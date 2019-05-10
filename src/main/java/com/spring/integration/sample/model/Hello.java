package com.spring.integration.sample.model;

import java.io.Serializable;

public class Hello implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
    private String msg;

    public Hello() {
    }

    public Hello(String msg, int id) {
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
		return String.format("Hello [id=%s, msg=%s]", id, msg);
	}
}
