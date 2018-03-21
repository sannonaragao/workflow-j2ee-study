package br.com.flow.model.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import br.com.flow.model.entity.Usuario;

@Constraint(validatedBy = { EnabledUser.ValidSet.class, EnabledUser.Valid.class,  })
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnabledUser {
	
	String message() default "O usuário não está ativo.";

//	String property();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	class ValidSet implements ConstraintValidator<EnabledUser, Set<Usuario>>{
		 @Override
	        public void initialize(EnabledUser constraintAnnotation) {
	        }
	 
	        @Override
	        public boolean isValid(Set<Usuario> usuarios, ConstraintValidatorContext context) {
	        	System.err.println("public boolean isValid(Set<Usuario> usuarios, ConstraintValidatorContext context) {");
	        	System.err.println( context.getDefaultConstraintMessageTemplate());
	            return false;
	        }
	}

	class Valid implements ConstraintValidator<EnabledUser, Usuario>{
		 @Override
	        public void initialize(EnabledUser constraintAnnotation) {
	        }
	 
	        @Override
	        public boolean isValid(Usuario usuario, ConstraintValidatorContext context) {
	        	System.err.println( "public boolean isValid(Usuario usuario, ConstraintValidatorContext context) {");
	        	System.err.println( context.getDefaultConstraintMessageTemplate());
	            return false;
	        }
	}
	

}
