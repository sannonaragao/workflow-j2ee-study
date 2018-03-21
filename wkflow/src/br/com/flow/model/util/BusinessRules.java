package br.com.flow.model.util;

import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import br.com.flow.model.validation.ConstraintValidatorFactoryImpl;
import br.com.flow.util.MensagemUtil;

public class BusinessRules {

	@Inject
	private EntityManager manager;
	
	
    public  <T> Set<ConstraintViolation<Object>> jsr303ModelValidation(Object u, Map<String,String>	 map ) {
    	
      EntityManagerFactory entityManagerFactory = manager.getEntityManagerFactory(); //JPAUtil.getEntityManagerFactory();

        ValidatorFactory validatorFactory = Validation.byDefaultProvider()
        		.configure()
        		.constraintValidatorFactory( new ConstraintValidatorFactoryImpl(entityManagerFactory) )
        		.buildValidatorFactory();
       
        Validator validator = validatorFactory.getValidator();
        
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(u);
        
        Pattern p = Pattern.compile("\\[(.*?)\\]");
        
        for (ConstraintViolation<Object> violation : constraintViolations) {
        	String atributo		=	(violation.getRootBean().getClass().toString()).substring(6)+"."+violation.getPropertyPath().toString();
        	String mensagem	= violation.getMessage();
//        	String label;
        	
        	try{
        		mensagem		=	MensagemUtil.getMensagem(mensagem.toLowerCase());
            }catch(MissingResourceException e){
//            	mensagem		=	violation.getPropertyPath().toString();
            	try{
            		mensagem		=	MensagemUtil.getMensagem(mensagem.toLowerCase().subSequence(1, mensagem.length()-1).toString());
            	}catch(MissingResourceException e2){
//            		System.err.println("MissingResourceException: "+mensagem.toLowerCase());
            	}
            	
            }	
        	
        	atributo = atributo.replace("br.com.flow.model.entity.", "").toLowerCase();
        	atributo = atributo.replace(".", "_");
        	
//        	System.out.println(mensagem);
        	
        	String[] args = new String[10];
        	
        	Matcher m = p.matcher(mensagem);
        	int x = 0;
        	String ls_path;
        	String ls_msg = null;
        	while(m.find()) {
        		ls_path = m.group(1);
        		try{
        			ls_msg = MensagemUtil.getMensagem(ls_path);
        			mensagem = mensagem.replace("["+ls_path+"]", ls_msg);
        		}catch(MissingResourceException e){
        			args[x] = ls_path;
        			mensagem = mensagem.replace("["+ls_path+"]", "");
        			x++;
        		}
    			
        	}
        	
        	if( x > 0 ) {
        		mensagem = String.format(mensagem, (Object[])args);
        	}
        	
        	/*
        	 * comentado por não ser mais necessário
        	 * 
        	try{
        		label		=	MensagemUtil.getMensagem(atributo.toLowerCase());
            }catch(MissingResourceException e){
            	label		=	violation.getPropertyPath().toString();
            }
            map.put(label, mensagem);
        	*/
        	
//        	atributo = atributo.replace("entity.", "");
//        	atributo = atributo.replace(".", "_");
						
        	map.put(atributo, mensagem);
	        
        }
        
//        externalContext.getSessionMap().put("validation_errors",map);
		
        return constraintViolations;
    }
    
    
}
