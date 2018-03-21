package wftest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.flow.model.dto.ProjectDTO;
import br.com.flow.model.entity.Project;

public class TestProjectQueries {

	private static EntityManagerFactory factory;
	
	private EntityManager manager;

	private DozerBeanMapper mapper;
	
	
	@BeforeClass
	public static void init() {
		factory = Persistence.createEntityManagerFactory("workflow");
	}
	
	@Before
	public void setUp() {
		this.manager = factory.createEntityManager();
		
		
	    BeanMappingBuilder builder = new BeanMappingBuilder() {
	        protected void configure() {

	            mapping(br.com.flow.model.entity.Usuario.class,
	            		br.com.flow.model.dto.UsuarioDTO.class);

	            mapping(br.com.flow.model.entity.Role.class,
	            		br.com.flow.model.dto.RoleDTO.class);
	            
	            mapping(br.com.flow.model.entity.UserGroup.class,
	            		br.com.flow.model.dto.UserGroupDTO.class);
	            
	            mapping(br.com.flow.model.entity.Project.class,
	            		br.com.flow.model.dto.ProjectDTO.class);
	        }
	    };
    
   
	    // Create mapper
	    this.mapper = new DozerBeanMapper();
	    
	    mapper.setCustomFieldMapper(new br.com.flow.model.util.HibernateInitializedFieldMapper());
	    
//		    List<CustomConverter> customConverters = new ArrayList<CustomConverter>();

	    /*
	     * 
	    @SuppressWarnings("rawtypes")
		List myMappingFiles = new ArrayList();
	    String file = "META-INF/dozerBeanMapping.xml";
	    
	    myMappingFiles.add(file);
	    */
	    // Add mappings
//	    mapper.setMappingFiles(myMappingFiles);
	    
	    mapper.addMapping(builder);
	    
	    
	}
	
	@After
	public void tearDown() {
		this.manager.close();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void ProjectRepo_listAll(){
		Query query = manager.createNamedQuery("Project.listAll", Project.class);

		Collection<Project> projectCollection = query.getResultList();

		List<ProjectDTO> projectsDTO = new ArrayList<ProjectDTO>(projectCollection.size());

		for (Project p : projectCollection) {
			projectsDTO.add(mapper.map(p, ProjectDTO.class));
		}
		print(projectsDTO);

	}
	

	public static void print( List< ? extends Object> lista ){
		System.out.println("---------------");
		for( Object o : lista){

			System.out.println(o);
		}
		System.out.println("---------------");
	}
}
