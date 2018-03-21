package br.com.flow.model.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.dozer.Mapper;
import org.springframework.stereotype.Repository;

import br.com.flow.model.dto.UserGroupDTO;
import br.com.flow.model.dto.UsuarioDTO;
import br.com.flow.model.entity.UserGroup;
import br.com.flow.model.util.BusinessRules;
import br.com.flow.model.util.CustomException;
import br.com.flow.model.util.Transactional;

@Repository
public class UserGroupRepo extends BusinessRules implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3183996626873047238L;

	@Inject
	@RequestScoped
	private EntityManager manager;

	@Inject
	private Mapper mapper;

	@Inject
	private UsuarioRepo usuarioRepo;
	
	
	
	public List<UsuarioDTO> getUsersNotFromGroup(UserGroupDTO usergroupdto) throws CustomException{
		List<UsuarioDTO> listAllUsers = usuarioRepo.listAll();
		Collection<UsuarioDTO> usuariosGroup = new HashSet<UsuarioDTO>(0);		
		usuariosGroup = usergroupdto.getUsuarios();
		
		for(UsuarioDTO uDto: usuariosGroup ){
			listAllUsers.remove(uDto);
			
		}
		return listAllUsers;
	}

	
	public List<UserGroupDTO> listAll() {
		TypedQuery<UserGroup> query = manager.createQuery("FROM UserGroup usergroup ORDER BY usergroup.name asc ", UserGroup.class);

		Collection<UserGroup> results = query.getResultList();

		List<UserGroupDTO> groupDTO = new ArrayList<UserGroupDTO>(results.size());

		for (UserGroup p : results) {
			groupDTO.add(mapper.map(p, UserGroupDTO.class));
		}

		return groupDTO;

	}

	public UserGroupDTO find(UserGroupDTO u) throws CustomException {

		if (u == null || u.getId() == null)
			return null;

		UserGroup usergroup = this.find(u.getId());
		
		usergroup.getUsuarios().size();
		
		UserGroupDTO dto = mapper.map(usergroup, UserGroupDTO.class);

		return dto;
	}

	public UserGroup find(Long usergroup_id) throws CustomException {
		UserGroup usergroup = manager.find(UserGroup.class, usergroup_id);
		if (usergroup == null) {
			throw new CustomException("Grupo de Usuário não encontrado", CustomException.SEVERITY_WARN_NAME);
		}
		return usergroup;
	}

	@Transactional
	public void delete(UserGroupDTO usergroup) throws Exception {

		try {
			UserGroup project = manager.find(UserGroup.class, usergroup.getId());
			manager.remove(project);

		} catch (Throwable t) {
			throw (t);
		}
	}

	@Transactional
	public UserGroupDTO update(UserGroupDTO usergroup) throws Exception {

		UserGroup usergrp = mapper.map(usergroup, UserGroup.class);

		Map<String, String> map = new HashMap<String, String>();

		this.jsr303ModelValidation(usergrp, map);
		if (map.size() > 0)
			throw new CustomException("Informações inválidas", map, CustomException.SEVERITY_INFO_NAME);

		try {

			if (usergroup.getId() == null) {

				manager.persist(usergrp);
				usergroup = mapper.map(usergrp, UserGroupDTO.class);

			} else {
				usergrp = manager.find(UserGroup.class, usergroup.getId());

				// Se não encontrou, é um registro que foi apagado
				if (usergrp == null) {
					throw new CustomException("O registro que está sendo modificado foi excluído.",
							CustomException.SEVERITY_WARN_NAME);
				} else {
					// Se encontrou, faz merge com o existente e atualiza.
					usergrp = mapper.map(usergroup, UserGroup.class);
					manager.merge(usergrp);
				}
			}

		} catch (Throwable t) {
			throw (t);
		}

		return usergroup;
	}

}
