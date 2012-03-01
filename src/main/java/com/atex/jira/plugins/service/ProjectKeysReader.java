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
import com.atlassian.sal.api.transaction.TransactionCallback;

/**
 * @author pau
 *
 */
class ProjectKeysReader implements TransactionCallback<List<String>>, Constants {
    
    private final Logger LOGGER = LoggerFactory.getLogger(ProjectKeysReader.class);
    
    private final PluginSettingsFactory pluginSettingsFactory;
    
    
    
    public ProjectKeysReader(final PluginSettingsFactory pluginSettingsFactory) {
        this.pluginSettingsFactory = pluginSettingsFactory;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<String> doInTransaction() {
        List<String> keys = null;
        PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
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