package com.lilykmtd;
import java.io.*;
import java.util.Objects;

public class CacheItem implements Serializable {
    private static final long serialVersionUID = -2338626292552177485L;

    public String expression;
    public int precision;
    public double result;

    CacheItem(String expression, int precision, double result) {
        this.expression = expression;
        this.precision = precision;
        this.result = result;
    }

    public String getExpression() {
        return expression;
    }

    public int getPrecision() {
        return precision;
    }

    public double getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheItem that = (CacheItem) o;
        return precision == that.precision && expression.equals(that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, precision);
    }
}