package br.com.flow.web.mb;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.flow.model.dto.UserGroupDTO;
import br.com.flow.model.dto.UsuarioDTO;
import br.com.flow.model.repository.UserGroupRepo;
import br.com.flow.model.repository.UsuarioRepo;
import br.com.flow.model.util.CustomException;
import br.com.flow.model.util.Transactional;

@Named("gruposMB")
@ViewScoped
public class GruposMB extends AncestorMB implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2083106675187757674L;

	@Inject
	private UserGroupRepo groupRepo;
	
	private List<UserGroupDTO> lista_de_grupos = null;

	@Inject
	private UserGroupDTO usergroupdto;
	
	@Inject
	private UsuarioRepo usuarioRepo;
	
//	@Inject
	
	public List<UserGroupDTO> getList(){
		if( this.lista_de_grupos == null ){
			this.lista_de_grupos = groupRepo.listAll();
		}
		
		return this.lista_de_grupos;
	}
	
	public UserGroupDTO getUsergroup(){
		return this.usergroupdto;
	}
	
	public void setSelectedUsers(Set<UsuarioDTO> users){
		this.usergroupdto.setUsuarios(users);
	}
	
	public Set<UsuarioDTO> getSelectedUsers(){
			
		return this.usergroupdto.getUsuarios();
	}
	
	public void setUsersFromGroup(Set<UsuarioDTO> users) throws CustomException{
		this.usergroupdto.setUsuarios(users);
	}
	
	public List<UsuarioDTO> getlistAllUsers() throws CustomException{
		
		List<UsuarioDTO> listAllUsers = usuarioRepo.listAll();
		
//		Set<UsuarioDTO> listSelectedUsers = usergroupdto.getUsuarios();
		listAllUsers.removeAll(usergroupdto.getUsuarios());
		listAllUsers.addAll(0, usergroupdto.getUsuarios());
		
		return listAllUsers;
	}
	
	public void setUsergroup(UserGroupDTO u) throws CustomException{
		this.setReadonly(true);
		
		if ( u == null ) return;
		
		UserGroupDTO lusergroup = null;
		
		lusergroup = this.groupRepo.find(u);

		if ( lusergroup == null ) return;
		
		this.usergroupdto = lusergroup;
	}
	
	public void insert() {
		this.usergroupdto = new UserGroupDTO();
		this.setReadonly(false);
	}

	public void edit() {
		
		if (this.getUsergroup() == null || this.getUsergroup().getId() == null
				|| this.getUsergroup().getId().intValue() <= 0) {
			addErrorMsg("Selecione o Grupo de Usuários que deseja editar.", FacesMessage.SEVERITY_WARN);
			return;
		}

		this.setReadonly(false);

		return;
	}

	public void teste1() {
		System.err.println("Teste 1");
	}
	public void teste2() {
		System.err.println("Teste 2");
	}

	
	@Transactional
	public void delete()  throws Exception {
		try {
			if (this.getUsergroup() == null || this.getUsergroup().getId() == null
					|| this.getUsergroup().getId().intValue() <= 0) {
				addErrorMsg("Selecione o Projeto que deseja Excluir.", FacesMessage.SEVERITY_WARN);
				return;
			}

			groupRepo.delete(usergroupdto);
			
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
		this.usergroupdto = new UserGroupDTO();
		this.addGrowlMsg("Sucesso!", "Dados Atualizados");

		return;
		
	}
	
	@Transactional
	public void update()  {

		try {
			usergroupdto = groupRepo.update( usergroupdto );
			this.lista_de_grupos = null;
			this.getList();
			this.setUsergroup(usergroupdto);
		} catch (Exception e) {
			processErros( e );
			return;
		} 

		this.addGrowlMsg("Sucesso!", "Dados Atualizados");
		
		return;
	}
	
	public boolean isDeletedisabled(){
		if( this.usergroupdto == null || this.usergroupdto.getId() == null  ||this.usergroupdto.getId() == 0 ) return true;
		return false;
	}
}
