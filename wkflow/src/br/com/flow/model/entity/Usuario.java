package br.com.flow.model.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.flow.model.util.RegExPatt;
import br.com.flow.model.validation.UniqueKey;




//@UniqueKey(property = "login")
@UniqueKey.List(value={ @UniqueKey(property = "login"), @UniqueKey(property = "email")})



@Table(name="usuario", indexes = { 
		@Index(name="idx_login", columnList="login", unique=true ),
		@Index(name="idx_email", columnList="email", unique=true  ) } )
@Entity

@NamedQueries({
	@NamedQuery(name = "Usuario.findByLoginorEmail",	query= "SELECT u FROM Usuario u where u.login = :login or u.email = :login  ")
 	
 })



//@AttributeOverride(name = "id", column = @Column(name = "usuario_id"))
public class Usuario  implements Serializable  {
	
	private static final long serialVersionUID = -6352677688258982478L;
	
	@Id
	@Column(name = "usuario_id")
	@GeneratedValue
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	private boolean ativo=true;
	
	private String celular;

	@NotNull(message="[standard.notnull]")
	@Pattern(regexp=RegExPatt.EMAIL,message="[usuario.email]")
	@Size(min = 5, max = 50, message="[standard.size_min_max] [{min}] [{max}]")
	@Column(nullable = false, length = 50, unique = true)
	private String email;
	
	@Size(min = 5, max = 15, message="[standard.size_min_max][{min}] [{max}]")
	@NotNull(message="[standard.notnull]")
	@Column(nullable = false, length = 15, unique = true)
	private String login;
	private Date nascimento;
	
	@Size(min=5, max= 100, message="[standard.size_min_max][{min}] [{max}]")
	@NotNull(message="[standard.notnull]")
	@Column(nullable = false)
	private String nome;

	private String ramal;
	
	private String residencial01;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;

	@NotNull(message="[standard.notnull]")
	@Column(nullable = false, length = 80 )
	private String senha;

	public Usuario() {
	}

	public String getCelular() {
		return celular;
	}

	public String getEmail() {
		return email;
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

	public Collection<Role> getRoles() {
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

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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
		Usuario other = (Usuario) obj;
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
		return "Usuario [id=" + id + ", ativo=" + ativo + ", celular=" + celular + ", email=" + email + ", login="
				+ login + ", nascimento=" + nascimento + ", nome=" + nome + ", ramal=" + ramal + ", residencial01="
				+ residencial01 + ", roles=" + roles + ", senha=" + senha + "]";
	}
	
}
