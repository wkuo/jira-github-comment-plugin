/**
 * 
 */
package com.atex.jira.plugins.servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import com.atex.jira.plugins.servlets.ProjectConfigurationServlet;
import com.atlassian.sal.api.auth.LoginUriProvider;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.templaterenderer.TemplateRenderer;

/**
 * @author pau
 *
 */
public class ProjectConfigurationServletTest {
    
    @Mock HttpServletRequest request;
    @Mock HttpServletResponse response;
    @Mock UserManager userManager;
    @Mock LoginUriProvider loginUriProvider;
    @Mock TemplateRenderer renderer;
    @Mock PluginSettingsFactory pluginSettingsFactory;
    @Mock TransactionTemplate transactionTemplate;
    
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        when(userManager.getRemoteUsername(any(HttpServletRequest.class))).thenReturn("admin");
        when(userManager.isSystemAdmin("admin")).thenReturn(true);
    }
    
    @Test
    public void withNullProjectParameterWillStillRenderCorrectly() throws ServletException, IOException {
        new ProjectConfigurationServlet(renderer, pluginSettingsFactory, transactionTemplate, userManager, loginUriProvider).doGet(request, response);
        verify(response).setContentType(ProjectConfigurationServlet.CONTENT_TYPE);
        verify(renderer).render(anyString(), anyMap(), any(Writer.class));
    }
    
//    public void

}
