package br.com.flow.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ProjectDTO implements Serializable {

	private static final long serialVersionUID = 6059180191917531418L;
	
	private Date dt_start;
	private boolean enabled = true;
	private Long id;
	private String name;
	private Set<UsuarioDTO> usuarios = new HashSet<UsuarioDTO>(0);
	
	public ProjectDTO(){
		
	}
	
	public Date getDt_start() {
	 	 return dt_start; 
	}
	
	public boolean getEnabled() {
	 	 return enabled; 
	}
	
	public Long getId() {
		return id;
	}
		
	public String getName() {
	 	 return name; 
	}
	
	public Set<UsuarioDTO> getUsuarios() {
		return usuarios;
	}	
	public void setDt_start(Date dt_start) { 
		 this.dt_start = dt_start; 
	}
	public void setEnabled(boolean enabled) { 
		 this.enabled = enabled; 
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) { 
		 this.name = name; 
	}
	public void setUsuarios(Set<UsuarioDTO> target) {
		/* Verifica quais objetos não existem mais e precisão ser deletados */
		Set<UsuarioDTO> listaUsuarios = new HashSet<UsuarioDTO>(this.usuarios);
		for( final UsuarioDTO p : listaUsuarios ){
			if ( !target.contains(p) ){
				this.usuarios.remove(p);
			}
		}
		
		/* Verifica quais objetos estão no target mas não no persistido e inclue */
		Set<UsuarioDTO> listaTargetUsuarios = new HashSet<UsuarioDTO>(target);
		for( final UsuarioDTO t : listaTargetUsuarios ){
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
		ProjectDTO other = (ProjectDTO) obj;
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
		return "ProjectDTO [dt_start=" + dt_start + ", enabled=" + enabled + ", id=" + id + ", name=" + name
				+ ", usuarios=" + usuarios + "]";
	}

	
	
}
