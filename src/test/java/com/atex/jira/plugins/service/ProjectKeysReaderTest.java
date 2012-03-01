/**
 * 
 */
package com.atex.jira.plugins.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import com.atex.jira.plugins.Constants;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

/**
 * @author pau
 *
 */
public class ProjectKeysReaderTest implements Constants {
    
    @Mock PluginSettingsFactory pluginSettingsFactory;
    @Mock PluginSettings settings;
    
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(pluginSettingsFactory.createGlobalSettings()).thenReturn(settings);
    }
    
    @Test
    public void willNeverReturnNullSettingReturnNull() {
        when(settings.get(PROJECT_KEYS)).thenReturn(null);
        List<String> result = new ProjectKeysReader(pluginSettingsFactory).doInTransaction();
        verify(settings, times(1)).get(PROJECT_KEYS);
        assertNotNull(result);
        assertEquals(0, result.size());
    }
    
    @Test
    public void willNeverReturnNullWhenSettingReturnEmptyList() {
        when(settings.get(PROJECT_KEYS)).thenReturn(new ArrayList<String>());
        List<String> result = new ProjectKeysReader(pluginSettingsFactory).doInTransaction();
        verify(settings, times(1)).get(PROJECT_KEYS);
        assertNotNull(result);
        assertEquals(0, result.size());
    }
    
    @Test
    public void willReturnCorrectProjectKeysWhenThereAreSettingInPlugin() {
        List<String> test = new ArrayList<String>();
        test.add("PLUGIN");
        when(settings.get(PROJECT_KEYS)).thenReturn(test);
        List<String> result = new ProjectKeysReader(pluginSettingsFactory).doInTransaction();
        verify(settings, times(1)).get(PROJECT_KEYS);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("PLUGIN", result.get(0));
    }

}
