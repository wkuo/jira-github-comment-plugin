/**
 * 
 */
package com.atex.jira.plugins.writer;

import static org.mockito.Mockito.*;

import java.util.Hashtable;
import java.util.Map;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.atex.jira.plugins.model.Project;
import com.atex.jira.plugins.reader.ProjectReader;
import com.atex.jira.plugins.service.ProjectWriter;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

/**
 * @author pau
 *
 */
public class ProjectWriterTest {
    
    @Mock PluginSettingsFactory pluginSettingsFactory;
    @Mock PluginSettings settings;
    
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(pluginSettingsFactory.createGlobalSettings()).thenReturn(settings);
    }
    
    
    @Test
    public void shouldNotAbleToWriteProjectWithEmptyKey() {
        Map<String, String> values = getValues();
        Project project = new Project(values);
        project.setKey("PLUGIN");
        new ProjectWriter(pluginSettingsFactory, project).doInTransaction();
        verify(settings, times(1)).put(eq(project.getNameSpaceKey()), any(Map.class));
        when(settings.get(project.getNameSpaceKey())).thenReturn(project.toMap());
        Project result = new ProjectReader(pluginSettingsFactory, project.getKey()).doInTransaction();
        assertEquals("PLUGIN", result.getKey());
    }
    
    private Map<String, String> getValues() {
        return new Hashtable<String, String>();
    }

}
