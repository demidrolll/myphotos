package com.demidrolll.myphotos.common.config;

import java.util.concurrent.TimeUnit;

public final class Constants {

    public static final long MAX_UPLOADED_PHOTO_SIZE_IN_BYTES = 10L * 1024L * 1024L; // 10 MB

    public static final long DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS = TimeUnit.SECONDS.toMillis(60);

    private Constants() {
    }
}
