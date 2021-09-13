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
public class OnlyLettersAndDigitsValidatorTest {

    private ConstraintValidatorContextImpl context;

    @BeforeEach
    void setUp() {
        context = mock(ConstraintValidatorContextImpl.class);
        initMocks(this);
    }

    @Parameterized.Parameter(0)
    public String word;
    @Parameterized.Parameter(1)
    public boolean expectedResult;

    @Parameterized.Parameters(name = "{index} - {0} - > {1}")
    public static List data() {
        return Arrays.asList(new Object[][]{
                {"1234567890", true},
                {"abcdefghijklmnopqrstuvwxyz", true},
                {"ABCDEFGHIJKLMNOPQRSTUVWXYZ", true},
                {"a", false},
                {"k", false},
                {"z", false},
                {"A", false},
                {"K", false},
                {"Z", false},
                {"r0", true},
                {"R1", true},
                {"w7", true},
                {"d9", true},
                {"fA", true},
                {"a1Q", true},
                {"*", false},
                {"8 6", false},
                {"#", false},
                {"ki.wadh", false},
                {"/", false},
                {":", false},
                {"@", false},
                {"[", false},
                {"`", false},
                {"{", false},
                {"ą", false},
                {"ć", false},
                {"ż", false},
                {"ź", false},
                {"ź", false},
                {"ó", false},
                {"uawhd*86", false},
                {"uawhd(86", false},
                {"uawhd<86", false},
                {"uawhd,86", false},
                {"uawhd?86", false}
        });
    }

    @Test
    public void test_parameterized() {
        OnlyLettersAndDigitsValidator validator = new OnlyLettersAndDigitsValidator();
        assertEquals(expectedResult, validator.isValid(word, context));
    }
}