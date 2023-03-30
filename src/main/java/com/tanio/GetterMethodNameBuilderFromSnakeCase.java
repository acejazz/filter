package com.tanio;

class GetterMethodNameBuilderFromSnakeCase implements GetterMethodNameBuilder {
    public String buildGetterName(String fieldName) {
        while (fieldName.contains("_")) {
            fieldName = fieldName.replaceFirst(
                    "_[a-z]",
                    String.valueOf(
                            Character.toUpperCase(
                                    fieldName.charAt(
                                            fieldName.indexOf("_") + 1))));
        }
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
