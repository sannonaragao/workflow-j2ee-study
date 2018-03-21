package br.com.flow.web.security;

import org.springframework.security.core.AuthenticationException;

public class MyAuthenticationException  extends AuthenticationException{

	private static final long serialVersionUID = 4657169078043258194L;

	public MyAuthenticationException(String msg) {
		super(msg);

	}


}
