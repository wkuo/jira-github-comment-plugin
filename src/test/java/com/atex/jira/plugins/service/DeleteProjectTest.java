/**
 * 
 */
package com.atex.jira.plugins.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import com.atex.jira.plugins.Constants;
import com.atlassian.crowd.acceptance.tests.applications.crowd.performance.VeryLargeCsvImporterTest;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

/**
 * @author pau
 *
 */
public class DeleteProjectTest implements Constants {
    
    @Mock PluginSettingsFactory pluginSettingsFactory;
    @Mock PluginSettings settings;
    
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        when(pluginSettingsFactory.createGlobalSettings()).thenReturn(settings);
    }
    
    
    @Test
    public void shouldNotThrowErrorWhenKeyIsNull() {
        new DeleteProject(pluginSettingsFactory, null).doInTransaction();
    }
    
    @Test
    public void shouldNotThrowErrorIfProjectKeyNotExist() {
        new DeleteProject(pluginSettingsFactory, "FAKEKEY").doInTransaction();
        verify(settings, times(0)).put(anyString(), any());
    }
    
    @Test
    public void shouldSucessfullyDeleteProject() {
        List<String> keys = new ArrayList<String>();
        keys.add("PLUGIN");
        when(settings.get(PROJECT_KEYS)).thenReturn(keys);
        new DeleteProject(pluginSettingsFactory, "PLUGIN").doInTransaction();
        verify(settings).put(PROJECT_KEYS, new ArrayList<String>());
        verify(settings, times(2)).put(anyString(), any());
    }

}
