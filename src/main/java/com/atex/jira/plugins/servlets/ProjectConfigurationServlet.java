package com.atex.jira.plugins.servlets;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class ProjectConfigurationServlet extends HttpServlet{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final String CONTENT_TYPE = "text/html;charset=utf-8";
    private static final String VIEW = "projectconfigure.vm";
    
    private final static String PARAM_PROJECT = "project";
    private final static String PARAM_TOPIC = "hub.topic";
    private final static String PARAM_MODE = "hub.mode";
    private final static String PARAM_SECRET_KEY = "hub.secret";
    
    
    
    private transient final UserManager userManager;
    private transient final LoginUriProvider loginUriProvider;
    private transient final TemplateRenderer renderer;
    private transient final PluginSettingsFactory pluginSettingsFactory;
    private transient final TransactionTemplate transactionTemplate;
    private final PluginService pluginService;
    
    public ProjectConfigurationServlet(TemplateRenderer renderer, PluginSettingsFactory pluginSettingsFactory, TransactionTemplate transactionTemplate, UserManager userManager, LoginUriProvider loginUriProvider) {
        this.renderer = renderer;
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.transactionTemplate = transactionTemplate;
        this.userManager = userManager;
        this.loginUriProvider = loginUriProvider;
        pluginService = new DefaultPluginService(pluginSettingsFactory, transactionTemplate);
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = userManager.getRemoteUsername(req);
        
        if (username != null && !userManager.isSystemAdmin(username)) {
            redirectToMain(req, resp);
            return;
        } else if (username==null) {
            redirectToLogin(req, resp);
            return;
        }
        
        Map<String, Object> models = new HashMap<String, Object>();
        String projectKey = req.getParameter("project");
        if (projectKey != null) {
            models.put("project", pluginService.getProject(projectKey));
        }
        
        Configuration configuration = transactionTemplate.execute(new ConfigurationReader(pluginSettingsFactory));
        models.put("configuration", configuration);
        resp.setContentType(CONTENT_TYPE);
        renderer.render(VIEW, Collections.<String, Object>emptyMap(), resp.getWriter());        
        
    }
    
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project project = getProject(req);
        resp.setContentType(CONTENT_TYPE);
        renderer.render(VIEW, Collections.<String, Object>emptyMap(), resp.getWriter());
    }

    private void redirectToMain(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(loginUriProvider.getLoginUri(URI.create("")).toASCIIString());
    }
   
    private void redirectToLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(loginUriProvider.getLoginUri(getUri(req)).toASCIIString());
    }
    
    private URI getUri(HttpServletRequest req) {
        StringBuffer builder = req.getRequestURL();
        if (req.getQueryString() != null) {
            builder.append("?");
            builder.append(req.getQueryString());
        }
        return URI.create(builder.toString());
    }    
    
    private Project getProject(HttpServletRequest request) {
        Project project = new Project();
        project.setKey(request.getParameter(PARAM_PROJECT));
        project.setSecretKey(request.getParameter(PARAM_SECRET_KEY));
        project.setTopic(request.getParameter(PARAM_TOPIC));
        project.setActive(true);
        return project;
    }
    
}
