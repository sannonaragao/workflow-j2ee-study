package testing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.flow.model.entity.Project;
import br.com.flow.model.entity.Usuario;
import br.com.flow.model.util.JPAUtil;

public class TestQuerysCriteria {

	
	public static void main(String[] args) {
		EntityManagerFactory emf = JPAUtil.getEntityManager().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		
//		print(listRoles(em));
//		print(listUsuario(em));
//		print(listaUsuarios(em));
//		print(listaUsuarios("sannon", em));
		
		Calendar calendar = new GregorianCalendar(2016,3,28);
		
		calendar.set(Calendar.YEAR, 2016);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 12);

		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		
        Date date =  calendar.getTime();
        
		System.out.println(sf.format(date));        
        calendar.add(Calendar.MONTH, 10);
        
        Date dateFim = calendar.getTime();
        System.out.println(sf.format(dateFim));
        
        
		print(buscarProjetoPorPeriodo(em, date, dateFim));
		print(buscaProjName(em));
		
		em.close();

	}


//	public static Collection<Project>listaProjetos(EntityManager em, Date dataInicial, Date dataFinal ){
//
//		CriteriaBuilder builder = em.getCriteriaBuilder();
//		
//		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);
//		Root<Usuario> c = criteriaQuery.from(Usuario.class);
//		criteriaQuery.select(c);
//		
//		List<Predicate> predicates = new ArrayList();
//		
//		ParameterExpression<Long> expId = 
//		
////		predicates.add(builder.between(c.<long>get("id"), 1, 2));
//		
////		criteriaQuery.where(builder.equal(c.get("login"), id ));
//		
//		Collection<Usuario> usuarios = em.createQuery(criteriaQuery).getResultList();
//		return usuarios;
//	}
	
	public static Collection<String> buscaProjName(EntityManager em){

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
		
		Root<Project> a = criteriaQuery.from(Project.class);
		criteriaQuery.select(a.<String>get("name"));
		
		TypedQuery<String> query =  em.createQuery(criteriaQuery);
		
		Collection<String> nomes = query.getResultList();
		return nomes;
	}
	
	public static List<Project> buscarProjetoPorPeriodo(EntityManager manager, Date dataInicial,
			Date dataFinal) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Project> criteriaQuery = builder.createQuery(Project.class);
		Root<Project> a = criteriaQuery.from(Project.class);
		
		criteriaQuery.select(a);
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (dataInicial != null) {
			ParameterExpression<Date> dataProjetoInicial = builder.parameter(Date.class, "dataInicial");
			ParameterExpression<Date> dataProjetoFinal = builder.parameter(Date.class, "dataFinal");
			
			
			predicates.add(builder.between(a.<Date>get("dt_start"), dataProjetoInicial, dataProjetoFinal));
		}
		
	/*	if (modelo != null) {
			ParameterExpression<ModeloCarro> modeloExpression = builder.parameter(ModeloCarro.class, "modelo");
			predicates.add(builder.equal(a.get("carro").get("modelo"), modeloExpression));
		}*/
		
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Project> query = manager.createQuery(criteriaQuery);
		
//		if (dataInicial != null) {
//			Calendar dataInicialInicial = Calendar.getInstance();
//			dataInicialInicial.setTime(dataInicial);
//			dataInicialInicial.set(Calendar.HOUR_OF_DAY, 0);
//			dataInicialInicial.set(Calendar.MINUTE, 0);
//			dataInicialInicial.set(Calendar.SECOND, 0);
//			
//			Calendar dataInicialFinal = Calendar.getInstance();
//			dataInicialFinal.setTime(dataInicial);
//			dataInicialFinal.set(Calendar.HOUR_OF_DAY, 23);
//			dataInicialFinal.set(Calendar.MINUTE, 59);
//			dataInicialFinal.set(Calendar.SECOND, 59);
//			
//			query.setParameter("dataInicial", dataInicialInicial.getTime());
//			query.setParameter("dataFinal", dataInicialFinal.getTime());
//		}
		
		
		query.setParameter("dataInicial", new Date(dataInicial.getTime()));
		query.setParameter("dataFinal", new Date(dataFinal.getTime()));

/*		if (modelo != null) {
			query.setParameter("modelo", modelo);
		}*/
		
		return query.getResultList();
	}

	
	public static Collection<Usuario>listaUsuarios (String parametro, EntityManager em){

		CriteriaBuilder builder = em.getCriteriaBuilder();
		
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);
		Root<Usuario> c = criteriaQuery.from(Usuario.class);
		criteriaQuery.select(c);
		criteriaQuery.where(builder.equal(c.get("login"), parametro ));
		
		Collection<Usuario> usuarios = em.createQuery(criteriaQuery).getResultList();
		return usuarios;
	}


	public static Collection<Usuario>listaUsuarios (EntityManager em){

		CriteriaBuilder builder = em.getCriteriaBuilder();
		
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);
		criteriaQuery.from(Usuario.class);
		
		TypedQuery<Usuario> query =  em.createQuery(criteriaQuery);
		
		Collection<Usuario> usuarios = query.getResultList();
		return usuarios;
	}

	public static void print( List< ? extends Object> lista ){
		System.out.println("---------------");
		for( Object o : lista){

			System.out.println(o);
		}
		System.out.println("---------------");
	}
	
	public static void print( Collection< ? extends Object> lista ){
		System.out.println("---------------");
		for( Object o : lista){

			System.out.println(o);
		}
		System.out.println("---------------");
	}
	

	
}


