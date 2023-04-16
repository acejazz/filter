package com.tanio;

interface ArgumentChecks {
    static <T> void checkNotNull(T argument, String argumentName) {
        if (argument == null) {
            String message = "%s must not be null";
            throw new IllegalArgumentException(String.format(message, argumentName));
        }
    }
}