/**
 * 
 */
package com.atex.jira.plugins.reader;

import static com.atex.jira.plugins.reader.ProjectKeysReader.KEY;
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

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

/**
 * @author pau
 *
 */
public class ProjectKeysReaderTest {
    
    @Mock PluginSettingsFactory pluginSettingsFactory;
    @Mock PluginSettings settings;
    
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(pluginSettingsFactory.createGlobalSettings()).thenReturn(settings);
    }
    
    @Test
    public void willNeverReturnNullSettingReturnNull() {
        when(settings.get(KEY)).thenReturn(null);
        List<String> result = new ProjectKeysReader(pluginSettingsFactory).doInTransaction();
        verify(settings, times(1)).get(KEY);
        assertNotNull(result);
        assertEquals(0, result.size());
    }
    
    @Test
    public void willNeverReturnNullWhenSettingReturnEmptyList() {
        when(settings.get(KEY)).thenReturn(new ArrayList<String>());
        List<String> result = new ProjectKeysReader(pluginSettingsFactory).doInTransaction();
        verify(settings, times(1)).get(KEY);
        assertNotNull(result);
        assertEquals(0, result.size());
    }
    
    @Test
    public void willReturnCorrectProjectKeysWhenThereAreSettingInPlugin() {
        List<String> test = new ArrayList<String>();
        test.add("PLUGIN");
        when(settings.get(KEY)).thenReturn(test);
        List<String> result = new ProjectKeysReader(pluginSettingsFactory).doInTransaction();
        verify(settings, times(1)).get(KEY);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("PLUGIN", result.get(0));
    }

}
