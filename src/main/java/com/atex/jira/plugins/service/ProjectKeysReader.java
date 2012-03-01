/**
 * 
 */
package com.atex.jira.plugins.service;

import java.util.List;

import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;

/**
 * @author pau
 *
 */
class ProjectKeysReader extends AbstractPluginService implements TransactionCallback<List<String>> {
    
    
    private final PluginSettingsFactory pluginSettingsFactory;
    
    
    
    public ProjectKeysReader(final PluginSettingsFactory pluginSettingsFactory) {
        this.pluginSettingsFactory = pluginSettingsFactory;
    }
    
    @Override
    protected PluginSettingsFactory getPluginSettingsFactory() {
        return pluginSettingsFactory;
    }

    @Override
    public List<String> doInTransaction() {
        return getProjectKeys();
    }

}
