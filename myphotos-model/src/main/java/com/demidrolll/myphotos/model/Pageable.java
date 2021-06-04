package com.demidrolll.myphotos.model;

import com.demidrolll.myphotos.exception.ValidationException;
import lombok.Getter;

@Getter
public class Pageable {

    private final int page;
    private final int limit;

    public Pageable(int limit) {
        this(1, limit);
    }

    public Pageable(int page, int limit) {
        if (page < 1) {
            throw new ValidationException("Invalid page value");
        }
        if (limit < 1) {
            throw new ValidationException("Invalid limit value");
        }
        this.page = page;
        this.limit = limit;
    }

    public int getOffset() {
        return (page - 1) * limit;
    }
}
