package com.zdzimi.registration.service.exception;

import java.util.List;
import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {

    private final List<String> conflicts;

    public ConflictException(List<String> conflicts) {
        super("conflict");
        this.conflicts = conflicts;
    }
}
