package com.tanio;

import static com.tanio.ArgumentChecks.checkNotNull;

class GetterMethodNameBuilderFromCamelCase implements GetterMethodNameBuilder {
    public String buildGetterName(String fieldName) {
        checkNotNull(fieldName, "fieldName");

        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
