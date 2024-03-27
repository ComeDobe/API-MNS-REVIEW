package com.dobe.locmns.validators;

import com.dobe.locmns.exceptions.ObjectValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

import java.util.Set;
import java.util.stream.Collectors;

public class ObjectsValidator <T>{
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final jakarta.validation.Validator validator = factory.getValidator();

    public void validate(T objectToValidate) {
        Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate);
        if (!violations.isEmpty()) {
            Set<String> errorMessages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
            throw new ObjectValidationException(errorMessages, objectToValidate.getClass().getName());
        }
    }
}
