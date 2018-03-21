package wftest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jintegrity.core.JIntegrity;

/*
 * Gera os dados de teste do aplicativo
 */
public class Workflow_testData {


	private static EntityManagerFactory factory;
	
	private EntityManager manager;

	private DozerBeanMapper mapper;
	
	private JIntegrity helper = new JIntegrity();
	
	
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
	            
	            mapping(br.com.flow.model.entity.WorkTemplate.class,
	            		br.com.flow.model.dto.WorkTemplateDTO.class);
	            
	        }
	    };

	    // Create mapper
	    this.mapper = new DozerBeanMapper();
	    
	    mapper.setCustomFieldMapper(new br.com.flow.model.util.HibernateInitializedFieldMapper());
	    mapper.addMapping(builder);
	    
		helper.cleanAndInsert();
		
		

	}
	

	@Test
	public void test() {
		Assert.assertTrue(true);
		
	}

}
