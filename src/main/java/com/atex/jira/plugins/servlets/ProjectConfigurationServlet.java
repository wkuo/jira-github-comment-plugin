package com.atex.jira.plugins.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atex.jira.plugins.configs.Configuration;
import com.atex.jira.plugins.configs.ConfigurationReader;
import com.atlassian.sal.api.auth.LoginUriProvider;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.templaterenderer.TemplateRenderer;

public class ProjectConfigurationServlet extends HttpServlet{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE = "text/html;charset=utf-8";
    private static final String VIEW = "projectconfigure.vm";
    
    private transient final UserManager userManager;
    private transient final LoginUriProvider loginUriProvider;
    private transient final TemplateRenderer renderer;
    private transient final PluginSettingsFactory pluginSettingsFactory;
    private transient final TransactionTemplate transactionTemplate;
    
    public ProjectConfigurationServlet(TemplateRenderer renderer, PluginSettingsFactory pluginSettingsFactory, TransactionTemplate transactionTemplate, UserManager userManager, LoginUriProvider loginUriProvider) {
        this.renderer = renderer;
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.transactionTemplate = transactionTemplate;
        this.userManager = userManager;
        this.loginUriProvider = loginUriProvider;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = userManager.getRemoteUsername(req);
//        if (username != null && !userManager.isSystemAdmin(username)) {
//            redirectToMain(req, resp);
//            return;
//        } else if (username==null) {
//            redirectToLogin(req, resp);
//            return;            
//        }
        String projectKey = req.getParameter("project");
        if (projectKey==null) {
            return;
        }
        
        Configuration configuration = transactionTemplate.execute(new ConfigurationReader(pluginSettingsFactory));
        Map<String, Object> models = new HashMap<String, Object>();
        models.put("configuration", configuration);
        resp.setContentType(CONTENT_TYPE);
        renderer.render(VIEW, models, resp.getWriter());        
//        resp.getOutputStream().write("Hello".getBytes());
        
    }
    

}
