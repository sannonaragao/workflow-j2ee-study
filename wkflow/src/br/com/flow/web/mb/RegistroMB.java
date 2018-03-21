package br.com.flow.web.mb;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sun.faces.context.flash.ELFlash;

import br.com.flow.model.dto.UsuarioDTO;
import br.com.flow.model.repository.UsuarioRepo;
import br.com.flow.util.MensagemUtil;


//@ManagedBean(name = "")
@Named("registroMB")
@ViewScoped
public class RegistroMB extends AncestorMB implements Serializable {

	private static final long serialVersionUID = -6456632915312399919L;
	private UsuarioDTO usuario = new UsuarioDTO();
	private String password1;
	private String password2;

	@Inject
	private UsuarioRepo usuarioRepository;

	public RegistroMB() {
		usuarioRepository = new UsuarioRepo();
	}

//	@Override
	public void postvalidate(ComponentSystemEvent event) {


		UIComponent components = event.getComponent();

		// get password
		UIInput uiInputPassword = (UIInput) components.findComponent("password");
		String password = uiInputPassword.getLocalValue() == null ? "" : uiInputPassword.getLocalValue().toString();
//		String passwordId = uiInputPassword.getClientId();


		// get confirm password
		UIInput uiInputConfirmPassword = (UIInput) components.findComponent("pwd2");
		String confere = uiInputConfirmPassword.getLocalValue() == null ? "" : uiInputConfirmPassword.getLocalValue().toString();

		if ( password.isEmpty() || confere.isEmpty()) {
			
			this.addErrorMsg(MensagemUtil.getMensagem("usuario_senha") + " : "
					+ MensagemUtil.getMensagem("standard.password"), FacesMessage.SEVERITY_INFO);
			
			uiInputPassword.setValid(false);
			uiInputConfirmPassword.setValid(false);

		}

//		super.postvalidate(event);

	};

	public String salvar() throws Exception {
		
		try {
			
			if( this.password1.equals(this.password2) ) {
				this.usuario.setSenha(new BCryptPasswordEncoder().encode(this.password1));
			}
			
			this.usuario = usuarioRepository.update(this.usuario);
			
		} catch (Exception e) {
		
			processErros(e);

			return null;
		}
		
		ELFlash.getFlash().put("usuario", this.usuario);
		return "welcome";

	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

}
