package com.eliteshoppy.eliteshoppycommons.httpresponse;

public abstract class HttpResponse {

	private String msg;

	public HttpResponse() {
		super();
	}

	public HttpResponse(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
