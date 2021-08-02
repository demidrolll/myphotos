package com.demidrolll.myphotos.web.util;

import com.demidrolll.myphotos.exception.ValidationException;

public final class UrlExtractorUtil {

    private UrlExtractorUtil() {
    }

    public static String getPathVariableValue(String url, String prefix, String suffix) {
        if (url.startsWith(prefix) && url.endsWith(suffix)) {
            return url.substring(prefix.length(), url.length() - suffix.length());
        } else {
            throw new ValidationException(String.format("Can't extract path variable from url=%s with prefix=%s and suffix=%s", url, prefix, suffix));
        }
    }
}
