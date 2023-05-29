package com.hrbl.gts.coreservices.statementpathapi.constraints;

import com.hrbl.gts.coreservices.statementpathapi.constants.EnvironmentConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;

import java.sql.Date;

public class DateConstraintValidator implements ConstraintValidator<DateConstraint, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(DateConstraint constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);
            EnvironmentConstants.INPUT_DATE_FORMAT.setLenient(false);
            Date startDate = new Date(EnvironmentConstants.INPUT_DATE_FORMAT.parse((String) firstObj).getTime());
            Date endDate = new Date(EnvironmentConstants.INPUT_DATE_FORMAT.parse((String) secondObj).getTime());
            return startDate.before(endDate) || startDate.equals(endDate);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
