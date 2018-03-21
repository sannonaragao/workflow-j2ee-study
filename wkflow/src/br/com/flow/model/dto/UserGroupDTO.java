package br.com.flow.model.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class UserGroupDTO implements Serializable {

	/**
	 * Cadastro de Grupos de Usuários
	 * 
	 */
	private static final long serialVersionUID = -6799465439612787881L;
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	private String name;
	public String getName() {
	 	 return name; 
	}	
	public void setName(String name) { 
		 this.name = name; 
	}
	
	private Set<UsuarioDTO> usuarios = new HashSet<UsuarioDTO>(0);
	

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
	

	public Set<UsuarioDTO> getUsuarios() {
		return usuarios;
	}

	/*
	 * Funções auxiliares
	 */
	public void addUsuario( UsuarioDTO usuario) {
		
		this.getUsuarios().add(usuario);
		
	}
	
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
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserGroupDTO other = (UserGroupDTO) obj;
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

}
