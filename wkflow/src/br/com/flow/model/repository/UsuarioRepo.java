package br.com.flow.model.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.dozer.Mapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.flow.model.dto.RoleDTO;
import br.com.flow.model.dto.UsuarioDTO;
import br.com.flow.model.entity.Role;
import br.com.flow.model.entity.Usuario;
import br.com.flow.model.util.BusinessRules;
import br.com.flow.model.util.CustomException;
import br.com.flow.model.util.DozerProducer;
import br.com.flow.model.util.JPAUtil;
import br.com.flow.model.util.Transactional;

@Repository
public class UsuarioRepo extends BusinessRules implements Serializable {

	private static final long serialVersionUID = -8496730948418026888L;

	@Inject
	private Mapper mapper;

	@Inject
	private EntityManager manager;

	@Inject
	@RequestScoped
	private RoleRepo roleRep;
	
	
	public UsuarioDTO findByLoginorEmail(String email) throws Exception {

		// Não utilizamos o manager injected porque esta rotina é chamada de um
		// Spring bean
		// e não funciona.
		EntityManager entityManager = JPAUtil.getEntityManager();

		// QUERY QUE VAI SER EXECUTADA (UsuarioEntity.findUser)
		Query query = entityManager.createNamedQuery("Usuario.findByLoginorEmail");

		// PARÂMETROS DA QUERY
		query.setParameter("login", email);

		// RETORNA O USUÁRIO SE FOR LOCALIZADO

		Usuario user = (Usuario) query.getSingleResult();
		if (user == null) {
			entityManager.close();
			throw new UsernameNotFoundException("Usuário ou email não encontrado: " + email);
		}

		// Serve apenas para carregar as regras para conexão lazy.
		@SuppressWarnings("unused")
		Collection<Role> cr = user.getRoles();

		if (mapper == null) {
			mapper = new DozerProducer().getMapper();
		}
		UsuarioDTO usuariodto = mapper.map(user, UsuarioDTO.class);
		entityManager.close();

		return usuariodto;
	}

	@Transactional
	public UsuarioDTO update(UsuarioDTO usuariodto) throws Exception {

		/* Inicia com usuário ativo */
		usuariodto.setAtivo(true);

		try {
			Usuario usuario = null;
			Map<String, String> map = new HashMap<String, String>();

			if (usuariodto.getId() == null || usuariodto.getRoles().size() == 0) {

				RoleDTO roledto = roleRep.find("ROLE_USUARIO" /* , em */);
				if (roledto == null) {
					roledto = new RoleDTO();
					roledto.setName("ROLE_USUARIO");
					roledto = roleRep.update(roledto);
				}

				if (!usuariodto.getRoles().contains(roledto)) {
					usuariodto.addRole(roledto);
				}

				usuario = mapper.map(usuariodto, Usuario.class);

				this.jsr303ModelValidation(usuario, map);
				if (map.size() > 0)
					throw new CustomException("Informações inválidas", map, CustomException.SEVERITY_INFO_NAME);

				if (usuario.getId() == null) {
					manager.persist(usuario);
				} else {
					manager.merge(usuario);
				}

				usuariodto = mapper.map(usuario, UsuarioDTO.class);

			} else {

				Usuario mergeUsuario = this.manager.find(Usuario.class, usuariodto.getId());

				// Se não encontrou, é um registro que foi apagado
				if (mergeUsuario == null) {
					throw new CustomException("O registro que está sendo modificado foi excluído.",
							CustomException.SEVERITY_WARN_NAME);
				} else {
					// Se encontrou, faz merge com o existente e atualiza.
					mergeUsuario = mapper.map(usuariodto, Usuario.class);
					this.manager.merge(mergeUsuario);
				}
			}

			// tx.commit();
		} catch (Throwable t) {
			// tx.rollback();
			throw (t);
		} finally {
			// em.close();
		}

		return usuariodto;
	}

	public List<UsuarioDTO> listAll() {
		TypedQuery<Usuario> query = manager.createQuery("FROM Usuario usuario", Usuario.class);

		Collection<Usuario> results = query.getResultList();

		List<UsuarioDTO> usuarioDTO = new ArrayList<UsuarioDTO>(results.size());

		for (Usuario p : results) {
			usuarioDTO.add(mapper.map(p, UsuarioDTO.class));
		}

		return usuarioDTO;
	}

	public UsuarioDTO find(UsuarioDTO u) throws CustomException {

		if (u == null || u.getId() == null)
			return null;

		Usuario user = this.find(u.getId());

		UsuarioDTO dto = mapper.map(user, UsuarioDTO.class);

		return dto;
	}

	public void delete(UsuarioDTO usuariodto) throws Exception {

		try {
			Usuario user = manager.find(Usuario.class, usuariodto.getId());
			manager.remove(user);

		} catch (Throwable t) {
			throw (t);
		}
	}

	public Usuario find(Long user_id) throws CustomException {
		Usuario user = manager.find(Usuario.class, user_id);
		if (user == null) {
			throw new CustomException("Usuário não encontrado", CustomException.SEVERITY_WARN_NAME);
		}
		return user;
	}
}
