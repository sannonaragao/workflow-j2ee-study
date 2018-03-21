package br.com.flow.web.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.primefaces.model.DualListModel;

import br.com.flow.model.dto.ProjectDTO;
import br.com.flow.model.dto.UsuarioDTO;
import br.com.flow.model.repository.ProjectRepo;
import br.com.flow.model.util.CustomException;
import br.com.flow.model.util.Transactional;

@Named("projectMB")
@ViewScoped
public class ProjectMB extends AncestorMB implements Serializable {

	private static final long serialVersionUID = 6305318886167164606L;

	@Inject
	private ProjectRepo projectRepository;
	
	private DualListModel<UsuarioDTO> usersProject;
	
	private List<ProjectDTO> lista_de_projetos = null;

	@Inject
	private ProjectDTO projectdto;
	

	static final Logger logger = LogManager.getLogger(Logger.class.getName());

	@PostConstruct
	public void init() {

		usersProject = new DualListModel<UsuarioDTO>(new ArrayList<UsuarioDTO>(), new ArrayList<UsuarioDTO>());
		reLoad();
	}

	private void reLoad(){

		try {

			usersProject.getSource().clear();
			usersProject.getSource().addAll(projectRepository.usersNotInProject(projectdto));
			
			usersProject.getTarget().clear();
					
			if (projectdto.getId() != null)
				usersProject.getTarget().addAll(projectRepository.usersInProject(projectdto) );
			
		} catch (CustomException e) {
			this.addErrorMsg(e.getMessage(), e.getSeverity());
			e.printStackTrace();
		}
	}
	
	public List<ProjectDTO> getLista_de_projetos() {
		if (this.lista_de_projetos == null) {
			
			this.lista_de_projetos =	projectRepository.listAll();		 //projectRN.listAll();

		}
		return this.lista_de_projetos;
	}

	public void setLista_de_projetos(List<ProjectDTO> lista) {
		this.lista_de_projetos = lista;
	}

	public DualListModel<UsuarioDTO> getUsersProject() {
		return usersProject;
	}

	public void setUsersProject(DualListModel<UsuarioDTO> u) {
		this.usersProject = u;
	}

	public ProjectDTO getProject() {
		return projectdto;
	}

	public void setProject(ProjectDTO p) throws CustomException {
		this.setReadonly(true);
		
		if ( p == null ) return;
		
		ProjectDTO lproject = null;
		
		lproject = this.projectRepository.find(p);

		if ( lproject == null ) return;
		
		this.projectdto = lproject;
		
		//Retorna com os usuários para a lista de não associados ao projeto 
		usersProject.getSource().addAll(usersProject.getTarget());
		
		//Limpa a lista de associados
		usersProject.getTarget().clear();
		
		//Adiciona os associados à lista de associados
		usersProject.getTarget().addAll(this.projectdto.getUsuarios());
		
		//Remove os usuarios efetivamente associados ao projeto da lista de não associados.
		usersProject.getSource().removeAll(usersProject.getTarget());
		
	}


	public void insert() {
		this.projectdto = new ProjectDTO();
		this.reLoad();
		this.setReadonly(false);
	}
	
	public void edit() {
		
		if (this.getProject() == null || this.getProject().getId() == null
				|| this.getProject().getId().intValue() <= 0) {
			addErrorMsg("Selecione o Projeto que deseja editar.", FacesMessage.SEVERITY_WARN);
			return;
		}

		this.setReadonly(false);

		return;
	}

	@Transactional
	public void delete()  throws Exception {
		try {
			if (this.getProject() == null || this.getProject().getId() == null
					|| this.getProject().getId().intValue() <= 0) {
				addErrorMsg("Selecione o Projeto que deseja Excluir.", FacesMessage.SEVERITY_WARN);
				return;
			}

			projectRepository.delete(projectdto);
			
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

		this.lista_de_projetos = null;
		this.projectdto = new ProjectDTO();
		this.reLoad();
		this.addGrowlMsg("Sucesso!", "Dados Atualizados");

		return;
		
	}
	
	@Transactional
	public void update()  {

		try {
			projectdto.setUsuarios(new HashSet<UsuarioDTO>(usersProject.getTarget()));
			projectdto = projectRepository.update( projectdto );
		} catch (Exception e) {
			processErros( e );
			return;
		} 

		this.lista_de_projetos = null;
		this.addGrowlMsg("Sucesso!", "Dados Atualizados");
		return;
	}
	

//	public Boolean getCollapsedList() {
//		return collapsedList;
//	}

	public boolean isDeletedisabled(){
		if( this.projectdto == null || this.projectdto.getId() == null) return true;
		return false;
		 
	}
	
	
}
