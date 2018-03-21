package br.com.flow.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class MySimpleUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	public MySimpleUrlAuthenticationFailureHandler() {

	}

	public MySimpleUrlAuthenticationFailureHandler(String defaultFailureUrl) {
		
		super(defaultFailureUrl);

	}
	
	@Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException { 
			
			if(  exception.toString().contains("Bad credentials") || ( exception.getCause() != null &&
					exception.getCause().getMessage().contains("UsernameNotFoundException") )){
						request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,  new MyAuthenticationException (  "Usuário ou Email inválidos"));
						this.getRedirectStrategy().sendRedirect(request, response, "/publico/login.jsf?login_error=2");
			}else
			{
				super.onAuthenticationFailure(request, response, exception);
			}
          
	}
}
