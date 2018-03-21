package br.com.flow.web.mb;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.hibernate.exception.GenericJDBCException;
import org.primefaces.event.ToggleEvent;

import br.com.flow.model.util.CustomException;
import br.com.flow.util.MensagemUtil;

public class AncestorMB {

	@Inject
	private FacesContext context;

	@Inject
	private ExternalContext externalContext;

	private Boolean readonly = true;

	protected void addErrorMsg(Map<String, String> erros) {

		for (String key : erros.keySet()) {
			String value = erros.get(key);
			String msg = key;
			try {
				msg = MensagemUtil.getMensagem(key);
			} catch (MissingResourceException e) {
				System.err.println("addErrorMsg -> MissingResourceException: " + msg.toString());
			}

			this.addErrorMsg(msg + " : " + value, FacesMessage.SEVERITY_WARN);
		}

	}

	protected void addErrorMsg(String error, Severity severity) {
		FacesMessage facesMessage = new FacesMessage(error);

		if (severity == null) {
			severity = FacesMessage.SEVERITY_ERROR;
		}

		facesMessage.setSeverity(severity);
		context.addMessage("panelMessage", facesMessage);

	}

	protected void addErrorMsgBoth(Map<String, String> erros) {
		
		StringBuilder lsa_messageGrowl = new StringBuilder();

		for (String key : erros.keySet()) {
			String value = erros.get(key);
			String msg = key;
			try {
				msg = MensagemUtil.getMensagem(key);
			} catch (MissingResourceException e) {
				System.err.println("addErrorMsg -> MissingResourceException: " + msg.toString());
			}
			lsa_messageGrowl.append(msg + " : " + value+"\n");
			lsa_messageGrowl.append(System.getProperty("line.separator"));
			
			this.addErrorMsg(msg + " : " + value, FacesMessage.SEVERITY_WARN);
		}
		this.addGrowlMsg("Atenção!", lsa_messageGrowl.toString());
	}

	protected void addGrowlMsg(String title, String error) {

		addGrowlMsg(title, error, FacesMessage.SEVERITY_INFO);
	}

	protected void addGrowlMsg(String title, String error, Severity severity) {
		FacesMessage facesMessage = new FacesMessage(title, error);
		
		if (severity == null) {
			severity = FacesMessage.SEVERITY_INFO;
		}

		facesMessage.setSeverity(severity);
		context.addMessage("growlMessage", facesMessage);
	}

	/*
	 * evita que o fieldset se expanda ao clicar em outro item da listagem
	 * 
	 */
	public void onToggle(ToggleEvent event) {
		// Visibility visibility = event.getVisibility();
	}
	
	/*
	 * Varre o mapa de erros gerados pela
	 * BusinessRules.jsr303ModelValidation(..) e seta os componentes da tela com
	 * estado de erro e textos
	 */
	protected void processErros(Exception e) {
		Map<String, String> erros;
		
		if (e instanceof CustomException) {
			erros = ((CustomException) e).getErros() ;
		}else{
			erros = new HashMap<String, String>();
		}
		
		Map<String, Object> requestMap = externalContext.getRequestMap();

		@SuppressWarnings("unchecked")
		Map<String, Object> components = (Map<String, Object>) requestMap.get("components");

		for (Map.Entry<String, String> entry : erros.entrySet()) {

			HtmlInputText input = (HtmlInputText) components.get(entry.getKey());

			if (input == null) continue;

			input.setValid(false);
//			input.getAttributes().put("title", "\"#{not component.valid ? '" + entry.getValue() + "' : ''}\"");
			input.getAttributes().put("title", entry.getValue());

		}
		this.addErrorMsg(erros);
		
		if( erros.size() == 0 ){
			String ls_message = e.getMessage();
			if ( ls_message == null || ls_message.length() == 0 ){
				ls_message = "Erro interno do sistema: "+e.getClass().toString();
			}else{
				ls_message = "Erro interno do sistema: "+ls_message;
			}
			
			if (e instanceof PersistenceException && e.getCause() instanceof GenericJDBCException ) {
				GenericJDBCException ex = (GenericJDBCException) e.getCause();
				ls_message = ls_message +System.getProperty("line.separator")+ ex.getSQLException();
				
				/*
				((org.hibernate.exception.GenericJDBCException)e.getCause()).getSQLException()
				((org.hibernate.exception.GenericJDBCException)e.getCause()).getErrorCode()
				*/

			}
					
			FacesMessage facesMessage = new FacesMessage(ls_message);
			facesMessage.setSeverity(FacesMessage.SEVERITY_FATAL);
			context.addMessage("panelMessage", facesMessage);
			context.addMessage("growlMessage", facesMessage);
			
		}
		
	}

	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
	}

	public Boolean getReadonly() {
		return this.readonly;
	}

	private Boolean collapsedList = true;
	public Boolean getCollapsedList() {
		return collapsedList;
	}
}
