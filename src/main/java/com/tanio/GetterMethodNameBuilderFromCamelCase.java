package com.tanio;

class GetterMethodNameBuilderFromCamelCase implements GetterMethodNameBuilder {
    public String buildGetterName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
