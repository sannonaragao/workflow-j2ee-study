package br.com.flow.model.validation;

import javax.persistence.EntityManager;

public interface EntityManagerAwareValidator {  
    void setEntityManager(EntityManager entityManager); 
} 