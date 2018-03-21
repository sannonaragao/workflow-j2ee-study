package testing;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.flow.model.entity.Role;
import br.com.flow.model.entity.Usuario;

public class ExJPA_grava_exclui {

	private static EntityManagerFactory factory;
	
	private EntityManager manager;
	
	@BeforeClass
	public static void init() {
		factory = Persistence.createEntityManagerFactory("workflow");
	}
	
	@Before
	public void setUp() {
		this.manager = factory.createEntityManager();
	}
	
	@After
	public void tearDown() {
		this.manager.close();
	}
	
	
	
	@Test
	public void updateCascade(){
		
		/*
		 * Essa função sem o cascade=CascadeType.PERSIST dá erro pois precisa primeiro gravar o "Role" depois o USUARIO.
		 * @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
		 * 
		 */
		Usuario u = new Usuario();
		u.setAtivo(true);
		u.setLogin("testeLog2");
		u.setNome("Testando 1234");
		u.setSenha("teste123");
		u.setEmail("asadfa@maisfais.com");
		
		
		Role role = new Role();
		role.setName("TESTE_ROLE");
		
		Collection<Role> roles = new ArrayList<Role>();
		roles.add(role);
		
		u.setRoles(roles);
		
		this.manager.getTransaction().begin();
		this.manager.persist(u);
		this.manager.getTransaction().commit();
		
	}
	
}
