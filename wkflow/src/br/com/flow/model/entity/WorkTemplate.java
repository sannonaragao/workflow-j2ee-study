package br.com.flow.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class WorkTemplate extends Work implements Serializable{

	private static final long serialVersionUID = 4232977949778843410L;

	@Id
	@Column(name = "worktemplate_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToMany(fetch = FetchType.LAZY , cascade ={ CascadeType.MERGE } )
	@JoinTable(name = "WorkTemplate_MasterUsers", joinColumns = @JoinColumn(name = "work_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private Set<Usuario> work_masters = new HashSet<Usuario>(0);

	public Set<Usuario> getWork_masters() {
		return work_masters;
	}

	public void setWork_masters(Set<Usuario> work_masters) {
		this.work_masters = work_masters;
	}
	
	@ManyToOne
	@JoinColumn(name = "worktemplate_id_super", nullable = true, foreignKey = @ForeignKey(name = "fk_worktemplate_id_super")) 
	private WorkTemplate	workTemplateSuper;


	public WorkTemplate getWorkTemplateSuper() {
		return workTemplateSuper;
	}

	public void setWorkTemplateSuper(WorkTemplate wkSuper) {
		workTemplateSuper = wkSuper;
	}

	@OneToMany(mappedBy="workTemplateSuper")
	@NotNull(message="[standard.notnull]")
	private Set<WorkTemplate>  workTemplates = new HashSet<WorkTemplate>();


	public Set<WorkTemplate> getWorkTemplates() {
		return workTemplates;
	}

	public void setWorkTemplates(Set<WorkTemplate> workTemplates) {
		this.workTemplates = workTemplates;
	}

	@ManyToOne
	private UserGroup usergroup;

	public UserGroup getUsergroup() {
		return usergroup;
	}

	public void setUsergroup(UserGroup usergroup) {
		this.usergroup = usergroup;
	}
	
	/*result = (int) (prime * result + ((workTemplateSuper == null) || workTemplateSuper.getId() == null ? 0 : workTemplateSuper.getId()));*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = (int) (prime * result + ((workTemplateSuper == null) || workTemplateSuper.getId() == null ? 0 : workTemplateSuper.getId()));
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
		WorkTemplate other = (WorkTemplate) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (workTemplateSuper == null) {
			if (other.workTemplateSuper != null)
				return false;
		} else if (!workTemplateSuper.getId().equals(other.workTemplateSuper.getId()))
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
