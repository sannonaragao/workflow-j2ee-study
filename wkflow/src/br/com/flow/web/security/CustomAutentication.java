package br.com.flow.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.flow.model.dto.RoleDTO;
import br.com.flow.model.dto.UsuarioDTO;
import br.com.flow.model.repository.UsuarioRepo;

@Service("userDetailsService")
@Transactional
public class CustomAutentication implements UserDetailsService {

//	@Inject
//	private UsuarioRepo usuarioRepo;  
	
	public CustomAutentication() {
		super();
	}

	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

		
		try {

			UsuarioRepo usuarioRepo = new UsuarioRepo();
			UsuarioDTO user = usuarioRepo.findByLoginorEmail(email);
			
			UserDetails userdetail = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getSenha(),
					user.isAtivo(), true, true, true, getGrantedAuthorities(user.getRoles()));
			return userdetail;
		
			
		} catch (Exception e) {
 
			throw new UsernameNotFoundException(e.getMessage());
		}
		
	}

	private final List<GrantedAuthority> getGrantedAuthorities(final Collection<RoleDTO> role) {

		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (final RoleDTO r : role) {
			authorities.add(new SimpleGrantedAuthority(r.toString()));
		}

		return authorities;
	}

}
