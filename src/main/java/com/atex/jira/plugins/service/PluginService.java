/**
 * 
 */
package com.atex.jira.plugins.service;

import java.util.List;

import com.atex.jira.plugins.model.Project;

/**
 * @author pau
 *
 */
public interface PluginService {
    
    
    /**
     * @return list of project keys, null safe
     */
    List<String> getProjectKeys();
    /**
     * @param key used to get project
     * @return project based on the <code>key</code>
     */
    Project getProject(String key);
    /**
     * Internally this method will add project key into existing list of project keys
     * @param project to be add
     */
    void add(Project project);
    /**
     * Delete existing project based on <code>key</code>, 
     * underlying also remove the key from existing list of project keys
     * @param key used to delete project
     */
    void delete(String key);

}
