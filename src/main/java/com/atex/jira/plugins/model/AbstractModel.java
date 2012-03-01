/**
 * 
 */
package com.atex.jira.plugins.model;

import java.util.Map;


/**
 * @author pau
 *
 */
public abstract class AbstractModel {
    
    
    
    protected abstract Map<String, String> getValues();
    
    protected String get(String key) {
        return get(key, "");
    }
    
    protected String get(String key, String fallback) {
        String value = getValues().get(key);
        return value == null?
                fallback:
                value;
    }
    
    protected boolean getAsBoolean(String key) {
        return Boolean.parseBoolean(get(key, "false"));
    }

}
