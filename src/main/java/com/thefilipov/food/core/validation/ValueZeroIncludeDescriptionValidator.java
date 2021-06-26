package com.thefilipov.food.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ValueZeroIncludeDescriptionValidator implements ConstraintValidator<ValueZeroIncludeDescription, Object> {

    private String valueField;
    private String descriptionField;
    private String descriptionMandatory;

    @Override
    public void initialize(ValueZeroIncludeDescription constraintAnnotation) {
        this.valueField = constraintAnnotation.valueField();
        this.descriptionField = constraintAnnotation.descriptionField();
        this.descriptionMandatory = constraintAnnotation.descriptionMandatory();
    }

    @Override
    public boolean isValid(Object objectValidation, ConstraintValidatorContext context) {
        boolean isValid = true;

        try {
            BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(objectValidation.getClass(), valueField)
                    .getReadMethod().invoke(objectValidation);
            String description = (String) BeanUtils.getPropertyDescriptor(objectValidation.getClass(), descriptionField)
                    .getReadMethod().invoke(objectValidation);

            if (value != null && BigDecimal.ZERO.compareTo(value) == 0 && description != null) {
                isValid = description.toLowerCase().contains(this.descriptionMandatory.toLowerCase());
            }
        } catch (Exception e) {
            throw new ValidationException(e);
        }

        return isValid;
    }
}
