package br.com.flow.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import br.com.flow.model.validation.EnabledUser;
import br.com.flow.model.validation.UniqueKey;

@UniqueKey(property = "name")
@Table(name="project", indexes = { 
		@Index(name="pk_project", columnList="project_id", unique=true ) } , uniqueConstraints={
		@UniqueConstraint(columnNames="name", name="idx_project_name")
		})
@Entity
@NamedQueries({
	@NamedQuery(name = "Project.listAll",	query= "SELECT p FROM Project p ORDER BY p.enabled desc, p.dt_start desc, p.name asc ")
 	
 })
//@AttributeOverride(name = "id", column = @Column(name = "project_id"))
public class Project implements Serializable  {

	private static final long serialVersionUID = 6059180191917531418L;
	
	@Id
	@Column(name = "project_id")
	@GeneratedValue	
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	private Date dt_start;
	
	private boolean enabled = true;
	
	@Size(min = 5, max = 80, message="[standard.size_min_max][{min}] [{max}]")
	@NotBlank(message="[standard.notnull]")
	@Column(nullable = false, length = 80, unique = true)
	private String name;
	
	@EnabledUser
	@OrderBy("nome ASC")
	@ManyToMany(fetch = FetchType.LAZY , cascade ={ CascadeType.MERGE } )
	@JoinTable(name = "project_usuarios", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private Set<Usuario> usuarios = new HashSet<Usuario>(0);

	public Date getDt_start() {
	 	 return dt_start; 
	}
		
	public boolean getEnabled() {
	 	 return enabled; 
	}
	
	public String getName() {
	 	 return name; 
	}	
	public Set<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setDt_start(Date dt_start) { 
		 this.dt_start = dt_start; 
	}
	public void setEnabled(boolean enabled) { 
		 this.enabled = enabled; 
	}
	public void setName(String name) { 
		 this.name = name; 
	}

	public void setUsuarios(Set<Usuario> target) {
		/* Verifica quais objetos não existem mais e precisão ser deletados */
		Set<Usuario> listaUsuarios = new HashSet<Usuario>(this.usuarios);
		for( final Usuario p : listaUsuarios ){
			if ( !target.contains(p) ){
				this.usuarios.remove(p);
			}
		}
		
		/* Verifica quais objetos estão no target mas não no persistido e inclue */
		Set<Usuario> listaTargetUsuarios = new HashSet<Usuario>(target);
		for( final Usuario t : listaTargetUsuarios ){
			if (  !this.usuarios.contains(t)){
				this.usuarios.add(t);
			}
		}

	}
	
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dt_start == null) ? 0 : dt_start.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((usuarios == null) ? 0 : usuarios.hashCode());
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (dt_start == null) {
			if (other.dt_start != null)
				return false;
		} else if (!dt_start.equals(other.dt_start))
			return false;
		if (enabled != other.enabled)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (usuarios == null) {
			if (other.usuarios != null)
				return false;
		} else if (!usuarios.equals(other.usuarios))
			return false;
		return true;
	}
	
	public String toString() {
		return "Project [id=" + id + ", dt_start=" + dt_start + ", enabled=" + enabled + ", name=" + name
				+ ", usuarios=" + usuarios + "]";
	}
}
