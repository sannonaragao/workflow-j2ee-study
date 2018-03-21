package br.com.flow.model.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	public static final String persistence_unit_name = "workflow";
	private static EntityManagerFactory emf;
	
	public static EntityManager getEntityManager() {

		if (emf == null){
			emf = Persistence.createEntityManagerFactory(persistence_unit_name);
		 }
		 return emf.createEntityManager();
		 
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		ExternalContext externalContext = facesContext.getExternalContext();
//		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
////		EntityManager em = (EntityManager) request.getAttribute("entityManager");
//		
//		return (EntityManager) request.getAttribute("entityManager");
		
	}
	
	public static EntityManagerFactory getEntityManagerFactory(){
		return emf;
	}
}
