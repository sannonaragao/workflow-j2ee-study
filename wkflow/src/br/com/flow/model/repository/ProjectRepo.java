package br.com.flow.model.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.dozer.Mapper;
import org.springframework.stereotype.Repository;

import br.com.flow.model.dto.ProjectDTO;
import br.com.flow.model.dto.UsuarioDTO;
import br.com.flow.model.entity.Project;
import br.com.flow.model.entity.Usuario;
import br.com.flow.model.util.BusinessRules;
import br.com.flow.model.util.CustomException;
import br.com.flow.model.util.Transactional;

@Repository
public class ProjectRepo extends BusinessRules  implements Serializable {

	private static final long serialVersionUID = -5161001073682660443L;

	@Inject
	@RequestScoped
	private EntityManager manager;

	@Inject
	private Mapper mapper;
	
	public ProjectRepo() {
	}	
	
	@SuppressWarnings("unchecked")
	public List<ProjectDTO> listAll() {

		Query query = manager.createNamedQuery("Project.listAll", Project.class);

		Collection<Project> projectCollection = query.getResultList();

		List<ProjectDTO> projectsDTO = new ArrayList<ProjectDTO>(projectCollection.size());

		for (Project p : projectCollection) {
			projectsDTO.add(mapper.map(p, ProjectDTO.class));
		}
		return projectsDTO;

	}

	public ProjectDTO find(ProjectDTO projectdto) throws CustomException {
		
		if ( projectdto == null || projectdto.getId() == null ) return null;
		
		Project project = this.find(projectdto.getId());
		
		//Inicializando o subset
		project.getUsuarios().size();
		
		ProjectDTO dto =  mapper.map(project, ProjectDTO.class);
		
		return dto;
	}
	
	public List<UsuarioDTO> usersNotInProject(ProjectDTO projectdto) {

		TypedQuery<Usuario> query = manager.createQuery(
		  "FROM Usuario usuario "
		  + "WHERE usuario.id "
		  + "NOT IN( SELECT usuario.id "
		  + "FROM Project project "
		  + "INNER JOIN project.usuarios usuarios "
		  + "WHERE project.id = :project_id)", Usuario.class);
		 
		query.setParameter("project_id", projectdto.getId());
		
		Collection<Usuario> results = query.getResultList();
 
		List<UsuarioDTO> lst_usuarioDTO = new ArrayList<UsuarioDTO>(results.size());

		for (Usuario p : results) {
			UsuarioDTO pdto = mapper.map(p, UsuarioDTO.class);
			lst_usuarioDTO.add(pdto);
		}

		return lst_usuarioDTO;
	}
	
	public List<UsuarioDTO> usersInProject(ProjectDTO projectdto) throws CustomException {

		
		Project project = this.find(projectdto.getId());
		
		Collection<Usuario> results = project.getUsuarios();
		
		List<UsuarioDTO> usuarioDTO = new ArrayList<UsuarioDTO>(results.size());
		
		for (Usuario p : results) {
			usuarioDTO.add(mapper.map(p, UsuarioDTO.class));
		}

		return usuarioDTO;
	}
	
	public Project find(Long projectdto_id ) throws CustomException{
		Project project = manager.find(Project.class, projectdto_id);
		if( project == null)  {
			throw new CustomException("Projeto não encontrado", CustomException.SEVERITY_WARN_NAME);
		}
		return project;
	}
	
	@Transactional
	public void delete(ProjectDTO projectdto) throws Exception  {

	    try {
        	Project project = manager.find(Project.class, projectdto.getId());
        	manager.remove(project);
            
        } catch (Throwable t) {
            throw(t);
        }
	}
	
	@Transactional
	public ProjectDTO update(ProjectDTO projectdto) throws Exception  {
			
        Project project = mapper.map(projectdto, Project.class);
        
        Map<String,String> map = new HashMap<String,String>();
			
		this.jsr303ModelValidation(project,  map);
		if (map.size() > 0 ) throw new CustomException("Informações inválidas",map,CustomException.SEVERITY_INFO_NAME);
		
		try {
			
            if ( projectdto.getId() == null ){
            	
            	manager.persist(project);
            	projectdto = mapper.map(project, ProjectDTO.class);
            	
            }else{
            	project = manager.find(Project.class, projectdto.getId());
            	
    			//Se não encontrou, é um registro que foi apagado
    			if ( project == null ){
    				throw new CustomException("O registro que está sendo modificado foi excluído.",CustomException.SEVERITY_WARN_NAME);
    			}else{
    				// Se encontrou, faz merge com o existente e atualiza.
    				project = mapper.map(projectdto, Project.class);
    				manager.merge(project);
    			}
            }
            
        } catch (Throwable t) {
            throw(t);
        } 
		
		return projectdto;
	}
	
	

}
