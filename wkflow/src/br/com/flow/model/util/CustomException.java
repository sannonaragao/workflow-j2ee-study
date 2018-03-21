package br.com.flow.model.util;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

public class CustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2640849236787324794L;
	public static final String SEVERITY_INFO_NAME = "INFO";
	public static final String SEVERITY_WARN_NAME = "WARN";
	public static final String SEVERITY_ERROR_NAME = "ERROR";
	public static final String SEVERITY_FATAL_NAME = "FATAL";
	private String SEVERITY_NAME;
//	private ArrayList<String> arrErros = new ArrayList<String>();
	
	public Severity getSeverity() {
		if( FacesMessage.SEVERITY_ERROR.toString().compareTo(SEVERITY_NAME) == 0 ){
			return FacesMessage.SEVERITY_ERROR;
		}else if( FacesMessage.SEVERITY_FATAL.toString().compareTo(SEVERITY_NAME) == 0 ){
			return FacesMessage.SEVERITY_FATAL;
		}else if( FacesMessage.SEVERITY_INFO.toString().compareTo(SEVERITY_NAME) == 0 ){
			return FacesMessage.SEVERITY_INFO;
		}else if( FacesMessage.SEVERITY_WARN.toString().compareTo(SEVERITY_NAME) == 0 ){
			return FacesMessage.SEVERITY_WARN;
		}
		return FacesMessage.SEVERITY_ERROR;
	}


	private Map<String, String> erros = new HashMap<String, String>();

    public Map<String, String> getErros() {
		return erros;
	}

	public CustomException() {

	}

	public CustomException(String message) {
		super(message);

	}

	public CustomException(String message, String severity) {
		super(message);
		this.SEVERITY_NAME = severity;
	}


	public CustomException(String message, Map<String,String>	map,  String severity) {
		super(message);
		this.SEVERITY_NAME = severity;
		erros = map;
//		arrErros.addAll(collection);
	}
	
	public CustomException(Throwable cause) {
		super(cause);

	}

	public CustomException(String message, Throwable cause) {
		super(message, cause);

	}

	public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}
	
	public FacesMessage getFacesMessage(){
		FacesMessage facesMessage = new FacesMessage(null,this.getMessage());
		
		if(this.SEVERITY_NAME == CustomException.SEVERITY_ERROR_NAME ){
			facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
		}else if(this.SEVERITY_NAME == CustomException.SEVERITY_FATAL_NAME ){
			facesMessage.setSeverity(FacesMessage.SEVERITY_FATAL);
		}else if(this.SEVERITY_NAME == CustomException.SEVERITY_INFO_NAME ){
			facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
		}else if(this.SEVERITY_NAME == CustomException.SEVERITY_WARN_NAME ){
			facesMessage.setSeverity(FacesMessage.SEVERITY_WARN);
		}else{
			facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		
		return facesMessage;
	}
}
