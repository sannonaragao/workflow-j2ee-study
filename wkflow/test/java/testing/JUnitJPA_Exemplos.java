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
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.flow.model.entity.Project;
import br.com.flow.model.entity.Project_;
import br.com.flow.model.entity.Role;
import br.com.flow.model.entity.Usuario;

public class JUnitJPA_Exemplos {

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
	public void listaUsuarios1(){

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);
		criteriaQuery.from(Usuario.class);
		
		TypedQuery<Usuario> query =  manager.createQuery(criteriaQuery);
		
		Collection<Usuario> usuarios = query.getResultList();
		print( usuarios );
		return ;
	}

	@Test
	public void resultadoTupla() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = builder.createTupleQuery();
		
		Root<Project> carro = criteriaQuery.from(Project.class);
		criteriaQuery.multiselect(carro.get(Project_.name).alias("ProjectName")
						, carro.get("dt_start").alias("ProjectData"));
		
		TypedQuery<Tuple> query = manager.createQuery(criteriaQuery);
		List<Tuple> resultado = query.getResultList();
		
		for (Tuple tupla : resultado) {
			System.out.println(tupla.get("ProjectName") + " - " + tupla.get("ProjectData"));
		}
	}
	
	@Test
	public void buscaProjName(){

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
		
		Root<Project> a = criteriaQuery.from(Project.class);
		criteriaQuery.select(a.<String>get("name"));
		
		TypedQuery<String> query =  manager.createQuery(criteriaQuery);
		
		Collection<String> nomes = query.getResultList();
		print(nomes);
		return;
	}
	
	@Test
	public void buscarProjetoPorPeriodo() {
		

		Calendar calendar = new GregorianCalendar(2016,3,28);
		
		calendar.set(Calendar.YEAR, 2016);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 12);

		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		
        Date dataInicial =  calendar.getTime();
        
		System.out.println(sf.format(dataInicial));        
        calendar.add(Calendar.MONTH, 10);
        
        Date dataFinal = calendar.getTime();
        System.out.println(sf.format(dataFinal));
        
        
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
		print(query.getResultList());
		return ;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@Test
	public void listaUsuarios2(){

		String parametro= "sannon";
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);
		Root<Usuario> c = criteriaQuery.from(Usuario.class);
		/*
		 * Interessante.  Usando a anotação @Fetch(FetchMode.JOIN) sobre uma propriedade de classe que é FK de outra tabela,
		 * em conjunto com o comando FETCH da API critéria, faz um eager select evitando multiplas querys.
		 *
		 *@Fetch(FetchMode.JOIN)
		 *
		 * Join<Usuario, Role> role = (Join)c.fetch("roles");  //JOIN FETCH PARA EVITAR MULTIPLAS QUERYS
		 * 
		 */
		
		@SuppressWarnings("unchecked")
		Join<Usuario, Role> role = (Join)c.fetch("roles");  //JOIN FETCH PARA EVITAR MULTIPLAS QUERYS
		
//		criteriaQuery.where(role);
		
		criteriaQuery.select(c);
		criteriaQuery.where(builder.equal(c.get("login"), parametro ));
		
		Collection<Usuario> usuarios = manager.createQuery(criteriaQuery).getResultList();
		print(usuarios);
		return ;
	}

	@Test
	public void listaUsuarios(){

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);
		criteriaQuery.from(Usuario.class);
		
		TypedQuery<Usuario> query =  manager.createQuery(criteriaQuery);
		
		Collection<Usuario> usuarios = query.getResultList();
		print(usuarios);
		return ;
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
