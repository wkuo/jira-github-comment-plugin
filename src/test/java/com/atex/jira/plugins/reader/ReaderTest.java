/**
 * 
 */
package com.atex.jira.plugins.reader;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import com.atex.jira.plugins.model.Project;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

/**
 * @author pau
 *
 */
public class ReaderTest {
    
    
    @Mock PluginSettingsFactory pluginSettingsFactory;
    @Mock PluginSettings settings;
    
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(pluginSettingsFactory.createGlobalSettings()).thenReturn(settings);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIAEWhenNoSettingFound() {
        String key = "this.is.test.key";
        when(settings.get(key)).thenReturn(null);
        new ProjectReader(pluginSettingsFactory, key).doInTransaction();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIAEWhenSettingNotReturnMapInstance() {
        String key = "this.is.test.key3";
        when(settings.get(key)).thenReturn("failed");
        new ProjectReader(pluginSettingsFactory, key).doInTransaction();
    }
    
    @Test
    public void shouldReturnProjectObjectWithDefaultValuesWhenSettingReturnEmptyMap() {
        
        String key = "this.is.test.key1";
        when(settings.get(key)).thenReturn(getValues());
        Project project = new ProjectReader(pluginSettingsFactory, key).doInTransaction();
        assertEquals("", project.getKey());
        assertEquals("", project.getTopic());
        assertEquals("", project.getSecretKey());
        assertEquals("", project.getCommetUserId());
        assertEquals(false, project.isActive());
    }
    
    @Test
    public void shouldReturnProjectObjectWithCorrectProjectkeyWhenMapContainsProjectKey() {
        
        String key = "this.is.test.key2";
        Map<String, String> values = getValues();
        values.put(Project.KEY, "JRA");
        when(settings.get(key)).thenReturn(values);
        Project project = new ProjectReader(pluginSettingsFactory, key).doInTransaction();
        assertEquals("JRA", project.getKey());
        assertEquals("", project.getTopic());
        assertEquals("", project.getSecretKey());
        assertEquals("", project.getCommetUserId());
        assertEquals(false, project.isActive());
    }
    
    private Map<String, String> getValues() {
        return new HashMap<String, String>();
    }

}
