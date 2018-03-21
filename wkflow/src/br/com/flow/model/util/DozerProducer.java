package br.com.flow.model.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;

public class DozerProducer {
	
	@Produces
	@ApplicationScoped
	public Mapper getMapper() {
	    BeanMappingBuilder builder = new BeanMappingBuilder() {
	        protected void configure() {

	            mapping(br.com.flow.model.entity.Project.class,
	            		br.com.flow.model.dto.ProjectDTO.class);
	            
	            mapping(br.com.flow.model.entity.Role.class,
	            		br.com.flow.model.dto.RoleDTO.class);
	            
	            mapping(br.com.flow.model.entity.UserGroup.class,
	            		br.com.flow.model.dto.UserGroupDTO.class);
	            
	            mapping(br.com.flow.model.entity.Usuario.class,
	            		br.com.flow.model.dto.UsuarioDTO.class);

	            mapping(br.com.flow.model.entity.WorkTemplate.class,
	            		br.com.flow.model.dto.WorkTemplateDTO.class);
	            
	            
	        }
	    };        

	    // Create mapper
	    DozerBeanMapper mapper = new DozerBeanMapper();
	    
	    mapper.setCustomFieldMapper(new HibernateInitializedFieldMapper());
	    
	    // Add mappings
	    mapper.addMapping(builder);
	    
	    return mapper;
	}

}
