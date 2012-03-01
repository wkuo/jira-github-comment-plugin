/**
 * 
 */
package com.atex.jira.plugins;

/**
 * @author pau
 *
 */
public interface Constants {
    // name space for this plugin
    String NAME_SPACE = "com.atex.jira.plugins.githubcomment:";
    // key to get list of project keys
    String PROJECT_KEYS = NAME_SPACE + "project.keys";
    // template to generate key for each project's setting
    // e.g. com.atex.jira.plugins.githubcomment:project.key.JIRA, where the placeholder is project key in jira
    String PROJECT_KEY_TEMPLATE = NAME_SPACE + "project.key.%s";
}
