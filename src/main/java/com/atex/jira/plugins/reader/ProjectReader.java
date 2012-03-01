/**
 * 
 */
package com.atex.jira.plugins.reader;

import com.atex.jira.plugins.Constants;
import com.atex.jira.plugins.model.Project;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;

/**
 * @author pau
 *
 */
public class ProjectReader implements TransactionCallback<Project>, Constants {

    private final PluginSettingsFactory pluginSettingsFactory;
    private final String key;

    
    public ProjectReader(PluginSettingsFactory pluginSettingsFactory, String key) {
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.key = key;
    }
    
    
    @Override
    public Project doInTransaction() {
        PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
        return new Project(settings.get(String.format(PROJECT_KEY_TEMPLATE, key)));
    }

}
