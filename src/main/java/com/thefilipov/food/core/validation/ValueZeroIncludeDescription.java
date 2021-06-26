package com.thefilipov.food.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { ValueZeroIncludeDescriptionValidator.class })
public @interface ValueZeroIncludeDescription {

    String message() default "descrição obrigatória inválida";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String valueField();
    String descriptionField();
    String descriptionMandatory();

}
