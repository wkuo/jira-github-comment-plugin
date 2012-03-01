/**
 * 
 */
package com.atex.jira.plugins.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;

/**
 * @author pau
 *
 */
class DeleteProject extends AbstractPluginService implements TransactionCallback<Void> {

    private final PluginSettingsFactory pluginSettingsFactory;
    private String key;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteProject.class);
    
    public DeleteProject(final PluginSettingsFactory pluginSettingsFactory, String key) {
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.key = key;
    }
    
    @Override
    protected PluginSettingsFactory getPluginSettingsFactory() {
        return pluginSettingsFactory;
    }

    @Override
    public Void doInTransaction() {
        if(key == null) {
            LOGGER.warn("project key is null");
            return null;
        }
        List<String> keys = getProjectKeys();
        int index = keys.indexOf(key);
        if(index > -1) {
            keys.remove(index);
            PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
            settings.put(PROJECT_KEYS, keys);
            settings.put(String.format(PROJECT_KEY_TEMPLATE, key), null);
        } else {
            LOGGER.warn("projet key["+ key + "] not found in settings");
        }
        return null;
    }

}
