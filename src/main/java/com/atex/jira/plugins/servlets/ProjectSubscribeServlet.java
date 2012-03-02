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
    
    private final static String PARAM_TOPIC = "topic";
    private final static String PARAM_MODE = "mode";
    private final static String PARAM_SECRET_KEY = "secret";
    private final static String CALLBACK = "/plugins/servlet/githubcomment";
    private final static String PARAM_PROJECT = "project";
    
//    private final static String PARAM_SAVE = "save";
    private final static String MODE_SUBSCRIBE = "subscribe";
    private final static String MODE_UNSUBSCRIBE = "unsubscribe";
    
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
        String mode = req.getParameter(PARAM_MODE);
        String topic = req.getParameter(PARAM_TOPIC);
        String secret = req.getParameter(PARAM_SECRET_KEY);
        
        if (MODE_SUBSCRIBE.equalsIgnoreCase(mode)) {
            doSave();
        } else if (MODE_UNSUBSCRIBE.equalsIgnoreCase(mode)) {
            doRemove();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectKey = req.getParameter(PARAM_PROJECT);
        String cbDomain = getDomainUrl(req) ;
        String callBackUrl = cbDomain + CALLBACK;
        Map<String, Object> models = new HashMap<String, Object>();
//        Project currentProject = transactionTemplate.execute(new ProjectReader(pluginSettingsFactory, projectKey));
//        if (currentProject!=null) {
//            models.put("projectconfig", currentProject);
//        } else {
//        }
        models.put("projectconfig", "");
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
    
    private void doSave() {
        
    }
   
    private void doRemove(){
        
    }
}
