package com.demidrolll.myphotos.model;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.util.Objects;

@Getter
public class OriginalImage {

    private final InputStream in;
    private final long size;
    private final String name;

    public OriginalImage(InputStream in, long size, String name) {
        this.in = Objects.requireNonNull(in);
        this.size = size;
        this.name = Objects.requireNonNull(name);
    }
}
