package br.com.flow.model.entity;

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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import br.com.flow.model.util.BaseEntities;
import br.com.flow.model.util.RegExPatt;
import br.com.flow.model.validation.UniqueKey;

@UniqueKey(property = "name")
@Table(name="usergroup", indexes = { 
		@Index(name="pk_usergroup", columnList="usergroup_id", unique=true ) } , uniqueConstraints={
		@UniqueConstraint(columnNames="name", name="idx_usergroup_name")
		})
@Entity
public class UserGroup extends BaseEntities {

	/**
	 * Cadastro de Grupos de Usuários
	 * 
	 */
	private static final long serialVersionUID = 4799350798099637449L;
	@Id
	@Column(name = "usergroup_id")
	@GeneratedValue
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Size(min = 2, max = 30, message="[standard.size_min_max][{min}] [{max}]")
	@NotBlank(message="[standard.notnull]")
	@Column(nullable = false, length = 30, unique = true)
	private String name;
	
	public String getName() {
	 	 return name; 
	}	
	public void setName(String name) { 
		 this.name = name; 
	}
	

	@OrderBy("nome ASC")
	@ManyToMany(fetch = FetchType.LAZY , cascade ={ CascadeType.MERGE } )
	@JoinTable(name = "group_usuarios", joinColumns = @JoinColumn(name = "usergroup_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private Set<Usuario> usuarios = new HashSet<Usuario>(0);


	@NotNull(message="[standard.notnull]")
	@Pattern(regexp=RegExPatt.EMAIL,message="[usuario.email]")
	@Size(min = 5, max = 50, message="[standard.size_min_max] [{min}] [{max}]")
	@Column(nullable = false, length = 50, unique = true)
	private String email;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		UserGroup other = (UserGroup) obj;
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
		return true;
	}
	public Set<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}
