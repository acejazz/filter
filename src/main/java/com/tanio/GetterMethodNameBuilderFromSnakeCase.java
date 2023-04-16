package com.tanio;

import static com.tanio.ArgumentChecks.checkNotNull;

class GetterMethodNameBuilderFromSnakeCase implements GetterMethodNameBuilder {
    public String buildGetterName(String fieldName) {
        checkNotNull(fieldName, "target");

        while (fieldName.contains("_")) {
            fieldName = replaceFirstUnderscore(fieldName);
        }
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    private static String replaceFirstUnderscore(String text) {
        return text.replaceFirst(
                "_[a-z]",
                capitalize(firstCharacterAfterUnderscore(text)));
    }

    private static char firstCharacterAfterUnderscore(String text) {
        return text.charAt(text.indexOf("_") + 1);
    }

    private static String capitalize(char character) {
        return String.valueOf(Character.toUpperCase(character));
    }
}