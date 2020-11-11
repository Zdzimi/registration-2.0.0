package com.zdzimi.registration.core.validation;

public class SignSolver {

    static boolean isSmallLetter(char sign) {
        return sign > 96 && sign < 123;
    }

    static boolean isBigLetter(char sign) {
        return sign > 64 && sign < 91;
    }

    static boolean isDigit(char sign) {
        return sign > 47 && sign < 58;
    }
}
