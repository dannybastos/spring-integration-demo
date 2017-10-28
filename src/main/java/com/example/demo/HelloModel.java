package com.example.demo;

import java.io.Serializable;

public class HelloModel implements Serializable {
	@Override
	public String toString() {
		return "HelloModel [id=" + id + ", msg=" + msg + "]";
	}

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
}
