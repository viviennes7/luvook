package com.ms.luvook.common.error;

public class UnauthorizedException extends RuntimeException{
	private static final long serialVersionUID = -2238030302650813813L;
	
	public UnauthorizedException() {
		super("JWT is unauthorized");
	}
}
