package com.spring.integration.sample.model;

import java.io.Serializable;

public class HelloModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
    private String msg;

    public HelloModel() {
    }

    public HelloModel(String msg, int id) {
        this.msg = msg;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

	@Override
	public String toString() {
		return String.format("HelloModel [id=%s, msg=%s]", id, msg);
	}
}
