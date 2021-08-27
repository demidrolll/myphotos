package com.demidrolll.myphotos.web.component;

import com.demidrolll.myphotos.exception.ApplicationException;

public class HttpStatusException extends ApplicationException {

    private final int status;

    public HttpStatusException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
