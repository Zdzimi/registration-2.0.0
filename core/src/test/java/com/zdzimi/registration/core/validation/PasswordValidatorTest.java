package com.zdzimi.registration.core.validation;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(Parameterized.class)
public class PasswordValidatorTest {

    private ConstraintValidatorContextImpl context;

    @BeforeEach
    void setUp() {
        context = mock(ConstraintValidatorContextImpl.class);
        initMocks(this);
    }

    @Parameterized.Parameter(0)
    public String password;
    @Parameterized.Parameter(1)
    public boolean exceptedResult;

    @Parameterized.Parameters(name = "{index} - {0} ->  {1}")
    public static List data() {
        return Arrays.asList(new Object[][]{
                {"aA1", true},
                {"aA:1", true},
                {"zZ0", true},
                {"aA0", true},
                {"aA9", true},
                {"a%A9", true},
                {"zZ9", true},
                {"yZ3", true},
                {"y Z 3", true},
                {"Zi3", true},
                {"aG1%", true},
                {"aG1@", true},
                {"a G&1@", true},
                {"aG1$", true},
                {"aa1$", false},
                {"AA1$", false},
                {"aG$", false},
                {"a2", false},
                {"A2", false},
                {"WI 2", false},
                {"wi 2", false},
                {"wi: 2", false},
                {"wi' 2", false},
        });
    }

    @Test
    public void test_parameterized() {
        PasswordValidator validator = new PasswordValidator();
        assertEquals(exceptedResult, validator.isValid(password, context));
    }
}