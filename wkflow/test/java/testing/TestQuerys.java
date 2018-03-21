package testing;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import br.com.flow.model.entity.Usuario;
import br.com.flow.model.util.JPAUtil;

public class TestQuerys {

	
	public static void main(String[] args) {
		EntityManagerFactory emf = JPAUtil.getEntityManager().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		
//		print(listRoles(em));
//		print(listUsuario(em));
		listObject(em);
		em.close();

	}


	
/*
 *  
 */
	public static Collection<?> listRoles(EntityManager em){
		@SuppressWarnings("rawtypes")
		TypedQuery<Collection> query = em.createQuery(
				"select u.roles from Usuario u", Collection.class);
		
		Collection<?> results = query.getResultList();
		
		return results ;
	}
	
	public static Collection<Usuario> listUsuario(EntityManager em){
		TypedQuery<Usuario> query = em.createQuery(
				"select u from Usuario u", Usuario.class);
		
		Collection<Usuario> results = query.getResultList();
		return results;
	}
	public static void listObject(EntityManager em){
		TypedQuery<Object[]> query = em.createQuery(
				"select u.nome, u.login, u.email from Usuario u", Object[].class);
		
		Collection<Object[]> results = query.getResultList();
		for( Object[] obj : results ){
			System.out.println(" - - - - -");
			System.out.println("Nome:"+obj[0]+" Login:"+obj[1]+" Email:"+obj[2]);
		}
		
		return ;
	}
	
	public static void print( Collection< ? extends Object> lista ){
		System.out.println("---------------");
		for( Object o : lista){
			System.out.println(" - - - - -");
			System.out.println(o);
		}
		System.out.println("---------------");
	}
	
	
}


