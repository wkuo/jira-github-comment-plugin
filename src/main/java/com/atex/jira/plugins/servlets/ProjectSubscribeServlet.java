package com.atex.jira.plugins.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import com.atex.jira.plugins.configs.Configuration;
import com.atex.jira.plugins.configs.ConfigurationReader;
import com.atex.jira.plugins.model.Project;
import com.atlassian.sal.api.auth.LoginUriProvider;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.templaterenderer.TemplateRenderer;

public class ProjectSubscribeServlet extends HttpServlet {
    
    private static final String CONTENT_TYPE = "text/html;charset=utf-8";
    private static final String VIEW = "projectsubscribe.vm";
    
    private transient final UserManager userManager;
    private transient final LoginUriProvider loginUriProvider;
    private transient final TemplateRenderer renderer;
    private transient final PluginSettingsFactory pluginSettingsFactory;
    private transient final TransactionTemplate transactionTemplate;
    
    private final static String PARAM_TOPIC = "hub.topic";
    private final static String PARAM_MODE = "hub.mode";
    private final static String PARAM_SECRET_KEY = "hub.secret";
    private final static String CALLBACK = "/plugins/servlet/githubcomment";
    private final static String PARAM_SAVE = "save";
    
    private final static String PARAM_SUBSCRIBE = "subscribe";
    private final static String PARAM_PROJECT = "project";
    
    public ProjectSubscribeServlet(TemplateRenderer renderer, PluginSettingsFactory pluginSettingsFactory, TransactionTemplate transactionTemplate, UserManager userManager, LoginUriProvider loginUriProvider) {
        this.renderer = renderer;
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.transactionTemplate = transactionTemplate;
        this.userManager = userManager;
        this.loginUriProvider = loginUriProvider;
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectKey = req.getParameter(PARAM_PROJECT);
        
        if (req.getParameter(PARAM_SAVE)!=null) {
            
        } else if (req.getParameter(PARAM_SUBSCRIBE)!=null) {
            doSubscribe(req, resp);
        } else {
            super.doPost(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectKey = req.getParameter(PARAM_PROJECT);
//        Configuration configuration = transactionTemplate.execute(new ConfigurationReader(pluginSettingsFactory));
        String cbDomain = req.getRemoteHost() ;
        String callBackUrl = cbDomain + CALLBACK;
        Map<String, Object> models = new HashMap<String, Object>();
        Project project = new Project(null);
        models.put("projectconfig", project);
        models.put("projectKey", projectKey);
        models.put("callback", callBackUrl);
        resp.setContentType(CONTENT_TYPE);
        renderer.render(VIEW, models, resp.getWriter());        
    }
    
    private void doSave(HttpServletRequest req) {
        
    }

    
    private void doSubscribe(HttpServletRequest req, HttpServletResponse resp) {
        String cbDomain = req.getRemoteHost() ;
        String topic = req.getParameter(PARAM_TOPIC);
        String secretKey = req.getParameter(PARAM_SECRET_KEY);
        String mode = req.getParameter(PARAM_MODE);
        String callBackUrl = cbDomain + CALLBACK;

        StringBuilder sb = new StringBuilder();
        sb.append("<html><body><form action=");
        sb.append(">");
        sb.append("<input type=hidden");
        sb.append("/>");
        
    }
    
}
