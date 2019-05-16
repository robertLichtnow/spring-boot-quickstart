package com.quick.start.domain.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.quick.start.domain.util.TextUtils;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * AbstractService
 */
public abstract class AbstractService<T> {

    @Autowired
    private Validator validator;

    /**
     * Method for validating object based on its constraints
     * @param obj Object to be validated
     */
    protected void validate(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = this.validator.validate(obj);
        // If there isn't a constraint violation, does nothing
        if (constraintViolations.isEmpty())
            return;

        List<String> violationsText = new ArrayList<>();

        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            violationsText.add(String.format("'%s' %s",
                    constraintViolation.getPropertyPath(),
                    constraintViolation.getMessage()));
        }

        // throws exception
        throw new IllegalArgumentException("Invalid data: " + TextUtils.toSentence(violationsText));
    }
}