package br.com.flow.model.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanUtils;
import org.dozer.Mapper;
import org.springframework.stereotype.Repository;

import br.com.flow.model.dto.WorkTemplateDTO;
import br.com.flow.model.entity.WorkTemplate;
import br.com.flow.model.entity.WorkTemplate_;
import br.com.flow.model.util.BusinessRules;
import br.com.flow.model.util.CustomException;
import br.com.flow.model.util.Transactional;

@Repository
public class WorkTemplateRepo extends BusinessRules  implements Serializable {
	
	private static final long serialVersionUID = 8254436761885472320L;

	@Inject
	@RequestScoped
	private EntityManager manager;

	@Inject
	private Mapper mapper;
	
	public WorkTemplateDTO load(long id) throws Exception{
		WorkTemplate work = manager.find(WorkTemplate.class, id);
		if ( work == null ){
			throw new CustomException("Modelo não encontrado", CustomException.SEVERITY_WARN_NAME);
		}
		
		
		inicializar( work.getWorkTemplates());
		
		WorkTemplateDTO workDto = mapper.map(work, WorkTemplateDTO.class);
		return workDto;
	}
	
	private void inicializar(Set<WorkTemplate> lista) {
		
		if ( lista == null || lista.size() <= 0 ) return;
		
		for ( WorkTemplate var : lista ){
			inicializar(var.getWorkTemplates());
		}
		
	}

	public Collection<WorkTemplateDTO> list() {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<WorkTemplate> criteriaQuery = builder.createQuery(WorkTemplate.class);
		
		Root<WorkTemplate> root = criteriaQuery.from(WorkTemplate.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.isNull(root.get(WorkTemplate_.workTemplateSuper)));
		
		TypedQuery<WorkTemplate> query =  manager.createQuery(criteriaQuery);
		
		Collection<WorkTemplate> workTemplateList = query.getResultList();
		
		Collection<WorkTemplateDTO> collectionWkTempDTO = new HashSet<WorkTemplateDTO>(0);
		
		for (WorkTemplate p : workTemplateList) {
			collectionWkTempDTO.add(mapper.map(p, WorkTemplateDTO.class));
		}
		
		return collectionWkTempDTO;

	}

//	public Collection<WorkTemplateDTO> listChild(long id_super) {
//
//		CriteriaBuilder builder = manager.getCriteriaBuilder();
//		CriteriaQuery<WorkTemplate> criteriaQuery = builder.createQuery(WorkTemplate.class);
//		
//		Root<WorkTemplate> root = criteriaQuery.from(WorkTemplate.class);
//		criteriaQuery.select(root);
//		criteriaQuery.where(builder.equal(root.get(WorkTemplate_.WorkTemplateSuper), id_super));
//		
//		TypedQuery<WorkTemplate> query =  manager.createQuery(criteriaQuery);
//		
//		Collection<WorkTemplate> workTemplateList = query.getResultList();
//		
//		Collection<WorkTemplateDTO> collectionWkTempDTO = new HashSet<WorkTemplateDTO>(0);
//		
//		for (WorkTemplate p : workTemplateList) {
//			collectionWkTempDTO.add(mapper.map(p, WorkTemplateDTO.class));
//		}
//		
//		return collectionWkTempDTO;
//
//	}
	
	@Transactional
	public void delete(WorkTemplateDTO templateDTO) throws Exception {

		try {
			WorkTemplate template = manager.find(WorkTemplate.class, templateDTO.getId());
			
			if(template != null ) manager.remove(template); //: 

		} catch (Throwable t) {
			throw (t);
		}
	}


	@Transactional
	public WorkTemplateDTO update(WorkTemplateDTO templatedto) throws Exception {


		WorkTemplate template = mapper.map(templatedto, WorkTemplate.class);

		Map<String, String> map = new HashMap<String, String>();

		this.jsr303ModelValidation(template, map);
		if (map.size() > 0)
			throw new CustomException("Informações inválidas", map, CustomException.SEVERITY_INFO_NAME);

		try {

			if (templatedto.getId() == null) {

				manager.persist(template);
				templatedto = mapper.map(template, WorkTemplateDTO.class);

			} else {
				template = manager.find(WorkTemplate.class, templatedto.getId());

				// Se não encontrou, é um registro que foi apagado
				if (template == null) {
					throw new CustomException("O registro que está sendo modificado foi excluído.",
							CustomException.SEVERITY_WARN_NAME);
				} else {
					// Se encontrou, faz merge com o existente e atualiza.
					template = mapper.map(templatedto, WorkTemplate.class);
					manager.merge(template);
				}
			}

		} catch (Throwable t) {
			throw (t);
		}

		return templatedto;
	}

	public void reload(WorkTemplateDTO data) throws Exception  {
	
		WorkTemplateDTO work = this.load(data.getId());
		
		BeanUtils.copyProperties(data, work);

		
//		data = mapper.map(work, WorkTemplateDTO.class);
		
		
	}

}
