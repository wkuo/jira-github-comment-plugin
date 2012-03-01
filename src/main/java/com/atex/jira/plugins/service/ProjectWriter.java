/**
 * 
 */
package com.atex.jira.plugins.service;

import com.atex.jira.plugins.model.Project;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;

/**
 * @author pau
 *
 */
public class ProjectWriter implements TransactionCallback<Void> {

    private final PluginSettingsFactory pluginSettingsFactory;
    private final Project project;
    
    public ProjectWriter(final PluginSettingsFactory pluginSettingsFactory, final Project project) {
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.project = project;
    }
    @Override
    public Void doInTransaction() {
        PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
        settings.put(project.getNameSpaceKey(), project.toMap());
        return null;
    }

}
