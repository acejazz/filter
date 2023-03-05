package com.tanio;

public class Entity {
    private Integer integerField;
    private Long longField;
    private Short shortField;
    private String stringField;
    private Boolean booleanField;
    private Float floatField;
    private Double doubleField;
    private Character charField;

    private int integerPrimitiveField;
    private long longPrimitiveField;
    private short shortPrimitiveField;
    private boolean booleanPrimitiveField;
    private float floatPrimitiveField;
    private double doublePrimitiveField;
    private char charPrimitiveField;

    private Object objectField;

    private NestedEntity nestedEntity;

    public Integer getIntegerField() {
        return integerField;
    }

    public void setIntegerField(Integer integerField) {
        this.integerField = integerField;
    }

    public Long getLongField() {
        return longField;
    }

    public void setLongField(Long longField) {
        this.longField = longField;
    }

    public Short getShortField() {
        return shortField;
    }

    public void setShortField(Short shortField) {
        this.shortField = shortField;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public Boolean getBooleanField() {
        return booleanField;
    }

    public void setBooleanField(Boolean booleanField) {
        this.booleanField = booleanField;
    }

    public Float getFloatField() {
        return floatField;
    }

    public void setFloatField(Float floatField) {
        this.floatField = floatField;
    }

    public Double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(Double doubleField) {
        this.doubleField = doubleField;
    }

    public Character getCharField() {
        return charField;
    }

    public void setCharField(Character charField) {
        this.charField = charField;
    }

    public int getIntegerPrimitiveField() {
        return integerPrimitiveField;
    }

    public void setIntegerPrimitiveField(int integerPrimitiveField) {
        this.integerPrimitiveField = integerPrimitiveField;
    }

    public long getLongPrimitiveField() {
        return longPrimitiveField;
    }

    public void setLongPrimitiveField(long longPrimitiveField) {
        this.longPrimitiveField = longPrimitiveField;
    }

    public short getShortPrimitiveField() {
        return shortPrimitiveField;
    }

    public void setShortPrimitiveField(short shortPrimitiveField) {
        this.shortPrimitiveField = shortPrimitiveField;
    }

    public boolean getBooleanPrimitiveField() {
        return booleanPrimitiveField;
    }

    public void setBooleanPrimitiveField(boolean booleanPrimitiveField) {
        this.booleanPrimitiveField = booleanPrimitiveField;
    }

    public float getFloatPrimitiveField() {
        return floatPrimitiveField;
    }

    public void setFloatPrimitiveField(float floatPrimitiveField) {
        this.floatPrimitiveField = floatPrimitiveField;
    }

    public double getDoublePrimitiveField() {
        return doublePrimitiveField;
    }

    public void setDoublePrimitiveField(double doublePrimitiveField) {
        this.doublePrimitiveField = doublePrimitiveField;
    }

    public char getCharPrimitiveField() {
        return charPrimitiveField;
    }

    public void setCharPrimitiveField(char charPrimitiveField) {
        this.charPrimitiveField = charPrimitiveField;
    }

    public Object getObjectField() {
        return objectField;
    }

    public void setObjectField(Object objectField) {
        this.objectField = objectField;
    }

    public NestedEntity getNestedEntity() {
        return nestedEntity;
    }

    public void setNestedEntity(NestedEntity nestedEntity) {
        this.nestedEntity = nestedEntity;
    }
}

class NestedEntity {
    private String stringField;
    private NestedNestedEntity nestedNestedEntity;

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public NestedNestedEntity getNestedNestedEntity() {
        return nestedNestedEntity;
    }

    public void setNestedNestedEntity(NestedNestedEntity nestedNestedEntity) {
        this.nestedNestedEntity = nestedNestedEntity;
    }
}

class NestedNestedEntity {
    private String stringField;

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }
}
