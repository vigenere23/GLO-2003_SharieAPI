package com.github.glo2003;

public class SimpleObject {

    private String string;
    private int integer;
    private double decimal;

    public SimpleObject() {
        setString("");
        setDecimal(0.0);
        setInteger(0);
    }

    public SimpleObject(String string, int integer, double decimal) {
        setString(string);
        setInteger(integer);
        setDecimal(decimal);
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
}
