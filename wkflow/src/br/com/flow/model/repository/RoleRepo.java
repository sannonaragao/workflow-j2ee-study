package br.com.flow.model.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.dozer.Mapper;
import org.springframework.stereotype.Repository;

import br.com.flow.model.dto.RoleDTO;
import br.com.flow.model.entity.Role;
import br.com.flow.model.util.BusinessRules;
import br.com.flow.model.util.CustomException;
@Repository
public class RoleRepo  extends BusinessRules  implements Serializable 	{

	
	private static final long serialVersionUID = 8203680982262679671L;
	
	@Inject
	private Mapper mapper;

	@Inject
	@RequestScoped
	private EntityManager manager;	

	public RoleDTO update(RoleDTO roledto ) throws Exception  {
	    
        Role role = mapper.map(roledto, Role.class);
        
        Map<String,String> map = new HashMap<String,String>();
			
		this.jsr303ModelValidation(role,  map);
		if (map.size() > 0 ) throw new CustomException("Informações inválidas",map,CustomException.SEVERITY_INFO_NAME);
		
		try {
			
            if ( roledto.getId() == null ){
        	
            	manager.persist(role);
        	roledto = mapper.map(role, RoleDTO.class);
        	
	        }else{
	        	Role mergeRole = manager.find(Role.class, roledto.getId());
	        	
				//Se não encontrou, é um registro que foi apagado
				if ( mergeRole == null ){
					throw new CustomException("O registro que está sendo modificado foi excluído.",CustomException.SEVERITY_WARN_NAME);
				}else{
					// Se encontrou, faz merge com o existente e atualiza.
					mergeRole = mapper.map(roledto, Role.class);
					manager.merge(mergeRole);
				}
	        }
            
        } catch (Throwable t) {
            throw(t);
        }
		return roledto;
	}

	public RoleDTO find(String s /*, EntityManager em*/) {
		
		TypedQuery<Role> query = this.manager.createQuery(
				"FROM Role role " +
				"WHERE role.name = :name)",
				Role.class);

		query.setParameter("name", s);

		Collection<Role> results = query.getResultList();

		for (Role p : results) {
			RoleDTO r = mapper.map(p, RoleDTO.class);
			return r;
		}
		
		return null;

	}

	public RoleDTO find(Long s, EntityManager em) {
		
		TypedQuery<Role> query = em.createQuery(
				"FROM Role role " +
				"WHERE role.id = :id)",
				Role.class);

		query.setParameter("id", s);

		Collection<Role> results = query.getResultList();

		for (Role p : results) {
			RoleDTO r = mapper.map(p, RoleDTO.class);
			return r;
		}
		
		return null;

	}
}
