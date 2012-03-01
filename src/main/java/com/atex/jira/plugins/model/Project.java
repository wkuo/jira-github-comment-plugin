/**
 * 
 */
package com.atex.jira.plugins.model;

import java.util.Hashtable;
import java.util.Map;

import org.springframework.util.Assert;

import com.atex.jira.plugins.Constants;

/**
 * @author pau
 *
 */
public class Project extends AbstractModel implements Constants {
    
    public static final String KEY = "key";
    public static final String TOPIC = "topic";
    public static final String SECERET_KEY = "secret.key";
    public static final String COMMENT_USER_ID = "comment.user.id";
    public static final String ACTIVE = "active";
    
    
    private String key;
    private String topic;
    private String secretKey;
    private String commetUserId;
    private Map<String, String> values;
    private boolean active;
    
    
    
    public Project(Map<String, String> values) {
        Assert.notNull(values, "parameter 'values' must not null");
        init(values);
    }
    
    @SuppressWarnings("unchecked")
    public Project(Object object) {
        Assert.notNull(object, "parameter 'object' must not null");
        Assert.isAssignable(Map.class, object.getClass(), "parameter object with type");
        init((Map<String, String>)object);
    }
    
    private void init(Map<String, String> values) {
        this.values = values;
        this.key = get(KEY);
        this.topic = get(TOPIC);
        this.secretKey = get(SECERET_KEY);
        this.commetUserId = get(COMMENT_USER_ID);
        this.active = getAsBoolean(ACTIVE);
    }
    
    public Map<String, String> toMap() {
        Map<String, String> map = new Hashtable<String, String>(5);
        map.put(KEY, getKey());
        map.put(TOPIC, getTopic());
        map.put(SECERET_KEY, getSecretKey());
        map.put(COMMENT_USER_ID, getCommetUserId());
        map.put(ACTIVE, Boolean.toString(isActive()));
        return map;
    }
    
    @Override
    protected Map<String, String> getValues() {
        return values;
    }


    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }
    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }
    /**
     * @return the topic
     */
    public String getTopic() {
        return topic;
    }
    /**
     * @param topic the topic to set
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }
    /**
     * @return the secretKey
     */
    public String getSecretKey() {
        return secretKey;
    }
    /**
     * @param secretKey the secretKey to set
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    /**
     * @return the commetUserId
     */
    public String getCommetUserId() {
        return commetUserId;
    }
    /**
     * @param commetUserId the commetUserId to set
     */
    public void setCommetUserId(String commetUserId) {
        this.commetUserId = commetUserId;
    }
    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }
    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    public String getNameSpaceKey() {
        return String.format(PROJECT_KEY_TEMPLATE, getKey());
    }
    
    
}
