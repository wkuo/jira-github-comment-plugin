/**
 * 
 */
package com.atex.jira.plugins.service;

import java.util.List;

import com.atex.jira.plugins.model.Project;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;

/**
 * @author pau
 *
 */
class ProjectWriter extends AbstractPluginService implements TransactionCallback<Void> {

    private final PluginSettingsFactory pluginSettingsFactory;
    private final Project project;
    
    public ProjectWriter(final PluginSettingsFactory pluginSettingsFactory, final Project project) {
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.project = project;
    }
    
    @Override
    public Void doInTransaction() {
        List<String> keys = getProjectKeys();
        keys.add(project.getKey());
        PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
        settings.put(PROJECT_KEYS, keys);
        settings.put(String.format(PROJECT_KEY_TEMPLATE, project.getKey()), project.toMap());
        return null;
    }
    
    @Override
    protected PluginSettingsFactory getPluginSettingsFactory() {
        return pluginSettingsFactory;
    }

}
