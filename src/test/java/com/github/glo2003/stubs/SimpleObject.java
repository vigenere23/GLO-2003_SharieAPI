package com.github.glo2003.stubs;

public class SimpleObject {

  public String text;
  public int number;
  public double decimal;

  public SimpleObject() {
    this.text = "A simple string";
    this.number = 123;
    this.decimal = 586.466;
  }

  public SimpleObject(String text, int number, double decimal) {
    this.text = text;
    this.number = number;
    this.decimal = decimal;
  }

}