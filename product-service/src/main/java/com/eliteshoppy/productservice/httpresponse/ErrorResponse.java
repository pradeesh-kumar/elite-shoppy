package com.eliteshoppy.productservice.httpresponse;

public class ErrorResponse extends HttpResponse {
	
	public ErrorResponse() {
		super();
	}

	public ErrorResponse(String msg) {
		super(msg);
	}
}
