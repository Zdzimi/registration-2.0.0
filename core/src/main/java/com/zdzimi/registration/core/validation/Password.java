package com.zdzimi.registration.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {

    String message() default "Hasło musi zawierać dużą i małą angielską literę oraz cyfrę.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
