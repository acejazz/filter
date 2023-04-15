package com.tanio;

public interface ArgumentChecks {
    static <T> void checkNotNull(T t, String argumentName) {
        if (t == null) {
            String message = "%s must not be null";
            throw new IllegalArgumentException(String.format(message, argumentName));
        }
    }
}
