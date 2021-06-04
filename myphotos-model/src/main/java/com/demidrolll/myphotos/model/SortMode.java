package com.demidrolll.myphotos.model;

import com.demidrolll.myphotos.exception.ValidationException;

import java.util.Arrays;

public enum SortMode {

    POPULAR_PHOTO,
    POPULAR_AUTHOR,
    ;

    public static SortMode of(String name) {
        return Arrays.stream(SortMode.values())
                .filter(sortMode -> sortMode.name().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(() -> new ValidationException("Undefined sort mode" + String.valueOf(name).toUpperCase()));
    }
}
