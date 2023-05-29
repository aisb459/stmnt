package com.hrbl.gts.coreservices.statementpathapi.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DateConstraintValidator.class)
@Documented
public @interface DateConstraint
{
    String message() default "Invalid Start Date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    String first();
    String second();
}
