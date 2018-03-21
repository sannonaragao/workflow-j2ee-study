package br.com.flow.web.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.flow.model.dto.UsuarioDTO;
import br.com.flow.model.repository.UsuarioRepo;
import br.com.flow.model.util.CustomException;
import br.com.flow.model.util.Transactional;
import br.com.flow.util.MensagemUtil;

@Named("usuarioMB")
@ViewScoped
public class UsuarioMB extends AncestorMB implements Serializable {
	
	private static final long serialVersionUID = 5823390871524300151L;

	@Inject
	private UsuarioRepo usuarioRepo;
	
	private List<UsuarioDTO> lista_de_grupos = null;

	@Inject
	private UsuarioDTO usuariodto;
	private String password1;
	private String password2;
	
	public List<UsuarioDTO> getList(){
		if( this.lista_de_grupos == null ){
			this.lista_de_grupos = usuarioRepo.listAll();
		}
		
		return this.lista_de_grupos;
	}
	
	public UsuarioDTO getUsuario(){
		return this.usuariodto;
	}
	
	public void setUsuario(UsuarioDTO u) throws CustomException{
		this.setReadonly(true);
		
		if ( u == null ) return;
		
		UsuarioDTO lusuario = null;
		
		lusuario = this.usuarioRepo.find(u);

		if ( lusuario == null ) return;
		
		this.usuariodto = lusuario;
	}
	

//	@Override
	public void postvalidate(ComponentSystemEvent event) {


		UIComponent components = event.getComponent();

		// get password
		UIInput uiInputPassword = (UIInput) components.findComponent("password");
		String password = uiInputPassword.getLocalValue() == null ? "" : uiInputPassword.getLocalValue().toString();

		// get confirm password
		UIInput uiInputConfirmPassword = (UIInput) components.findComponent("pwd2");
		String confere = uiInputConfirmPassword.getLocalValue() == null ? "" : uiInputConfirmPassword.getLocalValue().toString();

		if ( ( password.isEmpty() || confere.isEmpty() ) && this.usuariodto.getSenha().isEmpty() ) {
			
			this.addErrorMsg(MensagemUtil.getMensagem("usuario_senha") + " : "
					+ MensagemUtil.getMensagem("standard.password"), FacesMessage.SEVERITY_INFO);
			
			uiInputPassword.setValid(false);
			uiInputConfirmPassword.setValid(false);

		}

//		super.postvalidate(event);

	};

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

	public void insert() {
		this.usuariodto = new UsuarioDTO();
		this.setReadonly(false);
	}

	public void edit() {
		
		if (this.getUsuario() == null || this.getUsuario().getId() == null
				|| this.getUsuario().getId().intValue() <= 0) {
			addErrorMsg("Selecione o Usuário que deseja editar.", FacesMessage.SEVERITY_WARN);
			return;
		}

		this.setReadonly(false);

		return;
	}

	@Transactional
	public void delete()  throws Exception {
		try {
			if (this.getUsuario() == null || this.getUsuario().getId() == null
					|| this.getUsuario().getId().intValue() <= 0) {
				addErrorMsg("Selecione o Projeto que deseja Excluir.", FacesMessage.SEVERITY_WARN);
				return;
			}

			usuarioRepo.delete(usuariodto);
			
		} catch (Exception e) {
	
			if (e instanceof CustomException) {
				addErrorMsg(((CustomException) e).getErros());
			} else {
				e.printStackTrace();
				StackTraceElement[] trace = e.getStackTrace();
				addErrorMsg("Erro interno: " + trace[0].toString(), FacesMessage.SEVERITY_ERROR);
				
			}

			return;
		} finally {

		}

		this.lista_de_grupos = null;
		this.usuariodto = new UsuarioDTO();
		this.addGrowlMsg("Sucesso!", "Dados Atualizados");

		return;
		
	}
	
	@Transactional
	public void update()  {

		try {
			
			if ( !this.password1.isEmpty() && ( this.password1.equals(this.password2) ) ){
				this.usuariodto.setSenha(new BCryptPasswordEncoder().encode(this.password1));
			}
			
			usuariodto = usuarioRepo.update( usuariodto );
		} catch (Exception e) {
			processErros( e );
			return;
		} 

		this.lista_de_grupos = null;
		this.addGrowlMsg("Sucesso!", "Dados Atualizados");
		return;
	}
	
	public boolean isDeletedisabled(){
		if( this.usuariodto == null || this.usuariodto.getId() == null  ||this.usuariodto.getId() == 0 ) return true;
		return false;
	}
}
