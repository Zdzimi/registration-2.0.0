package com.zdzimi.registration.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.zdzimi.registration.core.validation.SignSolver.*;

public class OnlyLettersAndDigitsValidator implements ConstraintValidator<OnlyLettersAndDigits, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value.length() < 2) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            if (!isLicit(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isLicit(char charAt) {
        return isSmallLetter(charAt) || isBigLetter(charAt) || isDigit(charAt);
    }

}
