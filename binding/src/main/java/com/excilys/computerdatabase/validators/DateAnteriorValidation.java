package com.excilys.computerdatabase.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = DateAnteriorValidator.class)
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateAnteriorValidation {

    String message() default "{Date}";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
