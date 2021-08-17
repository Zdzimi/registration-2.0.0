package com.zdzimi.registration.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OnlyLettersAndDigitsValidator.class)
@Documented
public @interface OnlyLettersAndDigits {

    String message() default "Pole musi zawierać conajmniej dwa znaki (tylko angielskie litery i cyfry).";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
