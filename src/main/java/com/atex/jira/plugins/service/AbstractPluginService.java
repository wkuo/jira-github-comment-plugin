/**
 * 
 */
package com.atex.jira.plugins.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atex.jira.plugins.Constants;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

/**
 * A base class for common methods for this plugin in interaction with <code>PluginSettings</code>
 * @author pau
 *
 */
abstract class AbstractPluginService implements Constants {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPluginService.class);
    
    
    protected abstract PluginSettingsFactory getPluginSettingsFactory();
    
    
    /**
     * Get list of project keys
     * @return project keys, null safe
     */
    @SuppressWarnings("unchecked")
    protected List<String> getProjectKeys() {
        List<String> keys = null;
        PluginSettings settings = getPluginSettingsFactory().createGlobalSettings();
        Object result = settings.get(PROJECT_KEYS);
        if(result instanceof List) {
            keys = (List<String>) result;
        } else {
            keys = new ArrayList<String>();
            if(result != null) {
                LOGGER.error("Failed to read list of project keys from settings, the data is not instance of List but actually is " + result.getClass().getName());                
            } else {
                LOGGER.warn("Failed to read list of project keys from settings, it is null");
            }
        }
        return keys;
    }

}
