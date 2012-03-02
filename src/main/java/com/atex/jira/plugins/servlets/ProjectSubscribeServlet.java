package com.atex.jira.plugins.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atex.jira.plugins.configs.Configuration;
import com.atex.jira.plugins.configs.ConfigurationReader;
import com.atex.jira.plugins.model.Project;
import com.atex.jira.plugins.service.DefaultPluginService;
import com.atex.jira.plugins.service.PluginService;

import com.atlassian.sal.api.auth.LoginUriProvider;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.google.common.collect.Maps;

public class ProjectSubscribeServlet extends HttpServlet {
    
    private static final String CONTENT_TYPE = "text/html;charset=utf-8";
    private static final String VIEW = "projectsubscribe.vm";
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectSubscribeServlet.class);
    
    private transient final UserManager userManager;
    private transient final LoginUriProvider loginUriProvider;
    private transient final TemplateRenderer renderer;
    private transient final PluginSettingsFactory pluginSettingsFactory;
    private transient final TransactionTemplate transactionTemplate;
    
    private final static String PARAM_PROJECT = "project";
    private final static String PARAM_TOPIC = "topic";
    private final static String PARAM_MODE = "mode";
    private final static String PARAM_SECRET_KEY = "secret";
    private static final String PARAM_COMMENT_USER_ID = "commentUserId";
    private final static String CALLBACK = "/plugins/servlet/githubcomment";
    
//    private final static String PARAM_SAVE = "save";
    private final static String MODE_SUBSCRIBE = "subscribe";
    private final static String MODE_UNSUBSCRIBE = "unsubscribe";
    
    private final PluginService pluginService;
    
    
    public ProjectSubscribeServlet(TemplateRenderer renderer, PluginSettingsFactory pluginSettingsFactory, TransactionTemplate transactionTemplate, UserManager userManager, LoginUriProvider loginUriProvider) {
        this.renderer = renderer;
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.transactionTemplate = transactionTemplate;
        this.userManager = userManager;
        this.loginUriProvider = loginUriProvider;
        pluginService = new DefaultPluginService(pluginSettingsFactory, transactionTemplate);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        
        String mode = req.getParameter(PARAM_MODE);
        Map<String, Object> models = Maps.newHashMap();
        if (MODE_SUBSCRIBE.equalsIgnoreCase(mode)) {
            Project project = getProject(req);
            pluginService.add(project);
            models.put("success", "success");
            
        } else if (MODE_UNSUBSCRIBE.equalsIgnoreCase(mode)) {
            models.put("removed", Boolean.TRUE);
            doRemove();
        }
        
        resp.setContentType(CONTENT_TYPE);
        renderer.render(VIEW, models, resp.getWriter()); 
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectKey = req.getParameter(PARAM_PROJECT);
        String cbDomain = getDomainUrl(req) ;
        String callBackUrl = cbDomain + CALLBACK;
        Map<String, Object> models = new HashMap<String, Object>();

        try {
            Project project = pluginService.getProject(projectKey);
            System.out.println(projectKey);
            models.put("projectconfig", project);
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage(), e);
        }
        models.put("projectKey", projectKey);
        models.put("callback", callBackUrl);
        resp.setContentType(CONTENT_TYPE);
        renderer.render(VIEW, models, resp.getWriter());        
    }
    
    private String getDomainUrl(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        sb.append(req.getScheme());
        sb.append("://");
        sb.append(req.getServerName());
        if (req.getServerPort()!=80) {
            sb.append(":");
            sb.append(req.getServerPort());
        }
        return sb.toString();
    }
    
   
    private void doRemove(){
        
    }
    
    private Project getProject(HttpServletRequest request) {
        Project project = new Project();
        project.setKey(request.getParameter(PARAM_PROJECT));
        project.setSecretKey(request.getParameter(PARAM_SECRET_KEY));
        project.setTopic(request.getParameter(PARAM_TOPIC));
        project.setCommetUserId(request.getParameter(PARAM_COMMENT_USER_ID));
        project.setActive(true);
        return project;
    }
}
