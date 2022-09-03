package com.zdzimi.registration.core.model.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Violation {

    private final String fieldName;
    private final String message;
}
