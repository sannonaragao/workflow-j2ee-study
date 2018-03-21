package br.com.flow.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class WorkTrail extends Work implements Serializable{

	private static final long serialVersionUID = 4232977949778843410L;

	@Id
	@Column(name = "worktrail_id")
	@GeneratedValue	(strategy=GenerationType.IDENTITY)
	private Long id;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToMany(fetch = FetchType.LAZY , cascade ={ CascadeType.MERGE } )
	@JoinTable(name = "WorkTrail_MasterUsers", joinColumns = @JoinColumn(name = "work_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private Set<Usuario> work_masters = new HashSet<Usuario>(0);

	public Set<Usuario> getWork_masters() {
		return work_masters;
	}

	public void setWork_masters(Set<Usuario> work_masters) {
		this.work_masters = work_masters;
	}
}
