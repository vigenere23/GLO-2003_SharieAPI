package com.github.glo2003.stubs;

public class NestedObject {
    private String string;
    private int integer;
    private double decimal;
    private SimpleObject simpleObject;

    public NestedObject() {
        setString("");
        setInteger(0);
        setDecimal(0.0);
    }

    public NestedObject(String string, int integer, double decimal, SimpleObject simpleObject) {
        setString(string);
        setInteger(integer);
        setDecimal(decimal);
        this.simpleObject = simpleObject;
    }

    public String getString() { return string; }

    public void setString(String string) { this.string = string; }

    public int getInteger() {
        return integer;
    }

    public void setInteger(int integer) { this.integer = integer; }

    public double getDecimal() {
        return decimal;
    }

    public void setDecimal(double decimal) { this.decimal = decimal; }

    public SimpleObject getSimpleObject() { return simpleObject; }
}
