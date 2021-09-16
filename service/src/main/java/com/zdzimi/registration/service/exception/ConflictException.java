package com.zdzimi.registration.service.exception;

import java.util.List;

public class ConflictException extends RuntimeException {

    private List<String> conflicts;

    public ConflictException(List<String> conflicts) {
        super("conflict");
        this.conflicts = conflicts;
    }

    public List<String> getConflicts() {
        return conflicts;
    }
}
