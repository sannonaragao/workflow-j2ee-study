package br.com.flow.web.mb;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
//@Named("contextoBean")
@SessionScoped
public class ContextoBean implements Serializable {

	private static final long serialVersionUID = -2071855184464371947L;
	private static final String system_name = "Workflow";

	
//	private static final String ICO_SAVE = "fa fa-floppy-o";
	
//	private static final Locale locale = new Locale("pt", "BR");

	public String getSystem_name() {
		
		return system_name;
	}

	public String getMaxwidth() {
		return "500";
	}

	/*
	public Usuario getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String login = external.getRemoteUser();

		if (login != null) {
			UsuarioRN usuarioRN = new UsuarioRN();
			Usuario usuario = usuarioRN.buscarPorLogin(login);
			return usuario;
		}
		return null;
	}

	public static String getIcoSave() {
		return ICO_SAVE;
	}
	*/	
}
