package br.com.flow.model.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import br.com.flow.model.entity.Usuario;
import br.com.flow.model.entity.Work;

public class WorkTemplateDTO extends Work implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2105144944507946708L;
	private Long id;
	private WorkTemplateDTO	WorkTemplateSuper;
	private Set<Usuario> work_masters = new HashSet<Usuario>(0);

	public Long getId() {
		return id;
	}

	public Set<Usuario> getWork_masters() {
		return work_masters;
	}

	public WorkTemplateDTO getWorkTemplateSuper() {
		return WorkTemplateSuper;
	}
	public void setId(Long id) {
		this.id = id;
	}


	public void setWork_masters(Set<Usuario> work_masters) {
		this.work_masters = work_masters;
	}

	public void setWorkTemplateSuper(WorkTemplateDTO workTemplateSuper) {
		WorkTemplateSuper = workTemplateSuper;
	}
	

	private Set<WorkTemplateDTO>  workTemplates = new HashSet<WorkTemplateDTO>();
	
	public Set<WorkTemplateDTO> getWorkTemplates() {
		return workTemplates;
	}
	public void setWorkTemplates(Set<WorkTemplateDTO> workTemplates) {
		this.workTemplates = workTemplates;
	}


	private UserGroupDTO usergroup;

	@NotNull(message="Grupo é Obrigatório")
	public UserGroupDTO getUsergroup() {
		return usergroup;
	}

	public void setUsergroup(UserGroupDTO usergroup) {
		this.usergroup = usergroup;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		/*result = prime * result + ((WorkTemplateSuper == null) ? 0 : WorkTemplateSuper.hashCode());*/
		result = (int) (prime * result + ((WorkTemplateSuper == null) || WorkTemplateSuper.getId() == null ? 0 : WorkTemplateSuper.getId()));
		
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((workTemplates == null) ? 0 : workTemplates.hashCode());
		result = prime * result + ((work_masters == null) ? 0 : work_masters.hashCode());
		result = prime * result + ((usergroup == null) ? 0 : usergroup.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkTemplateDTO other = (WorkTemplateDTO) obj;
		if (WorkTemplateSuper == null) {
			if (other.WorkTemplateSuper != null)
				return false;
		} else if (!WorkTemplateSuper.getId().equals(other.WorkTemplateSuper.getId()))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (workTemplates == null) {
			if (other.workTemplates != null)
				return false;
		} /*else if (!workTemplates.equals(other.workTemplates))
			return false;*/
		if (work_masters == null) {
			if (other.work_masters != null)
				return false;
		} else if (!work_masters.equals(other.work_masters))
			return false;
		
		if (usergroup == null) {
			if (other.usergroup != null)
				return false;
		} else if (!usergroup.equals(other.usergroup))
			return false;
		
		return true;
	}
		
}
