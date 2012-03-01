/**
 * 
 */
package com.atex.jira.plugins.service;

import java.util.List;

import org.springframework.util.Assert;


import com.atex.jira.plugins.model.Project;
import com.atex.jira.plugins.service.ProjectKeysReader;
import com.atex.jira.plugins.service.ProjectReader;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;

/**
 * @author pau
 *
 */
public class DefaultPluginService implements PluginService {

    
    private final PluginSettingsFactory pluginSettingsFactory;
    private final TransactionTemplate transactionTemplate;
    
    
    public DefaultPluginService(final PluginSettingsFactory pluginSettingsFactory, final TransactionTemplate transactionTemplate) {
        Assert.notNull(pluginSettingsFactory, "parameter pluginSettingsFactory must not null");
        Assert.notNull(transactionTemplate, "parameter transactionTemplate must not null");
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.transactionTemplate = transactionTemplate;
    }
    
    @Override
    public List<String> getProjectKeys() {
        return transactionTemplate.execute(new ProjectKeysReader(pluginSettingsFactory));
    }

    @Override
    public Project getProject(String key) {
        return transactionTemplate.execute(new ProjectReader(pluginSettingsFactory, key));
    }

    @Override
    public void add(Project project) {
        transactionTemplate.execute(new ProjectWriter(pluginSettingsFactory, project));
    }

    @Override
    public void delete(String key) {
        throw new UnsupportedOperationException();
    }

}
