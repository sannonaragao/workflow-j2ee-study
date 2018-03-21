package br.com.flow.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;



public class UsuarioDTO implements Serializable {
	

	private static final long serialVersionUID = -6352677688258982478L;
	
	private Long id;
	private boolean ativo;
	private String celular;
	private String login;
	private Date nascimento;
	private String nome;
	private String ramal;
	private String residencial01;
	private Collection<RoleDTO> roles = new HashSet<RoleDTO>(0);
	private String senha;
	private String email;

	public UsuarioDTO() {
		
	}
	
	public String getCelular() {
		return celular;
	}

	public String getEmail() {
		return email;
	}

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public Date getNascimento() {
		return nascimento;
	}

	
	public String getNome() {
		return nome;
	}
	
	public String getRamal() {
		return ramal;
	}

	public String getResidencial01() {
		return residencial01;
	}

	public Collection<RoleDTO> getRoles() {
		return roles;
	}

	public String getSenha() {
		return senha;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setRamal(String ramal) {
		this.ramal = ramal;
	}

	public void setResidencial01(String residencial01) {
		this.residencial01 = residencial01;
	}

	public void setRoles(Collection<RoleDTO> roles) {
		this.roles = roles;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	/*
	 * Funções auxiliares
	 */
	public void addRole( RoleDTO role) {
		if ( this.getRoles() == null ){
			this.setRoles(getNewRoleCollection());
		}
		
		this.getRoles().add(role);
		
	}
	
	public ArrayList<RoleDTO> getNewRoleCollection() {
		ArrayList<RoleDTO> roleCollection = new ArrayList<RoleDTO>();
		return roleCollection; 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((celular == null) ? 0 : celular.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((nascimento == null) ? 0 : nascimento.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((ramal == null) ? 0 : ramal.hashCode());
		result = prime * result + ((residencial01 == null) ? 0 : residencial01.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
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
		UsuarioDTO other = (UsuarioDTO) obj;
		if (ativo != other.ativo)
			return false;
		if (celular == null) {
			if (other.celular != null)
				return false;
		} else if (!celular.equals(other.celular))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (nascimento == null) {
			if (other.nascimento != null)
				return false;
		} else if (!nascimento.equals(other.nascimento))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (ramal == null) {
			if (other.ramal != null)
				return false;
		} else if (!ramal.equals(other.ramal))
			return false;
		if (residencial01 == null) {
			if (other.residencial01 != null)
				return false;
		} else if (!residencial01.equals(other.residencial01))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UsuarioDTO [id=" + id + ", ativo=" + ativo + ", celular=" + celular + ", login=" + login
				+ ", nascimento=" + nascimento + ", nome=" + nome + ", ramal=" + ramal + ", residencial01="
				+ residencial01 + ", roles=" + roles + ", senha=" + senha + ", email=" + email + "]";
	}


	
}
