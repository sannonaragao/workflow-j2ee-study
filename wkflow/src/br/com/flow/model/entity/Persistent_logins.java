package br.com.flow.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import br.com.flow.model.util.BaseEntities;

@Entity
@Table (name="persistent_logins",uniqueConstraints= @UniqueConstraint( name="pk_persistent_logins", columnNames = { "series" }))
public class Persistent_logins extends BaseEntities  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2177211302611706023L;
	@Id
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Size(min=5, max= 100, message="[entity.Usuario.nome] : [standard.size_min_max][{min}] [{max}]")
	@NotNull(message="[entity.Usuario.nome] : [standard.notnull]")
	@Column(nullable = false)
	private String username;
	
	@Size(max= 65)
	@Column(nullable = false)
	@NaturalId
	private String series;
	
	@Column(nullable = false)
	private String token;

	@Column(nullable = false)
	private Date last_used;
	

	@PrePersist
	protected void onCreate() {
		last_used = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		last_used = new Date();
	}
	
	
}
