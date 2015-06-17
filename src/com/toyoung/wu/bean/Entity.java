package com.toyoung.wu.bean;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public abstract class Entity implements Serializable{

	protected int id;

    protected String cacheKey;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getCacheKey() {
	return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
	this.cacheKey = cacheKey;
    }
}
