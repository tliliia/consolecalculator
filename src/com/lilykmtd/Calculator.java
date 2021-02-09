package com.lilykmtd;

import java.math.*;
import java.util.*;
import com.lilykmtd.*;

import static com.lilykmtd.CalculatorCache.DEFAULTCACHESIZE;

public class Calculator {
    private static CalculatorCache cache;

    public static void setCacheSize(int size) {
        Calculator.getCache().setCacheSize(size);
    }

    private static CalculatorCache getCache() {
        if (cache == null) {
            cache = new CalculatorCache(DEFAULTCACHESIZE);
        }
        return cache;
    }

    static public double calculateExpressionWithPrecision(String expression, int precision) throws Exception {
        Double res = Calculator.getCache().getResultForExpressionWithPrecision(expression, precision);
        if (res.equals(Double.NaN)) {//нет в кеше
            try {
                res = doExpressionWithPrecision(expression, precision);
                Calculator.getCache().saveCalculation(expression, precision, res);
            } catch (Exception e) {
                throw e;
            }
        }
        return res;
    }

    static private double doExpressionWithPrecision(String expression, int precision) throws Exception {
        String postNotationLine = infixNotationToPostfix(expression);
        double res = calculate(postNotationLine);
        BigDecimal bd = new BigDecimal(Double.toString(res));
        bd = bd.setScale(precision, RoundingMode.HALF_UP);

        return res;
    }

    static private double calculate(String postNotationString) throws Exception {
        Stack<Double> operStack = new Stack<>(); //Стек для хранения операторов
        double result = 0;
        if (!postNotationString.isEmpty())
        {
            for (int i=0; i < postNotationString.length(); i++) {
                char symbol = postNotationString.charAt(i);
                if (Character.isDigit(symbol) || symbol == '.') {
                    StringBuffer number = new StringBuffer();
                    number.append(symbol);
                    int next = i + 1;
                    while (next < postNotationString.length()) {
                        char nextSymbol = postNotationString.charAt(next);
                        if (Character.isDigit(nextSymbol) || nextSymbol == '.') {
                            number.append(postNotationString.charAt(next));
                            next++;
                        } else {
                            break;
                        }
                    }
                    i += number.length() - 1;
                    String strNumber = number.toString();
                    Double num = Double.parseDouble(strNumber);
                    operStack.push(num);
                } else if (isOperator(symbol)) {//бинарное действие
                    double b = operStack.pop();
                    double a = operStack.pop();

                    switch (symbol) {
                        case '+':
                            result = a + b;
                            break;
                        case '-':
                            result = a - b;
                            break;
                        case '*':
                            result = a * b;
                            break;
                        case '/':
                            result = a / b;
                            break;
                    }
                    operStack.push(result);
                }
            }
            result = operStack.peek();
        }
        return result;
    }

    static private String infixNotationToPostfix(String input) {
        StringBuffer postfix = new StringBuffer();
        Stack<Character> operStack = new Stack<>(); //Стек для хранения операторов
        char next;
        if (!input.isEmpty()) {
            for (int i = 0; i < input.length(); i++) {
                char symbol = input.charAt(i);
                switch (symbol) {
                    case ' ': break;
                    case ',':
                        postfix.append('.');
                        break;
                    case '(':
                        operStack.push(symbol);
                        break;
                    case ')': {
                        while ((next = operStack.pop()) != '(')
                            postfix.append(next);
                        break;
                    }
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        while (!operStack.isEmpty() && getPriority(operStack.peek()) >= getPriority(symbol))
                            postfix.append(operStack.pop());
                        operStack.push(symbol);
                        postfix.append(' ');
                        break;
                    default:
                        postfix.append(symbol);
                }
            }
            while (!operStack.isEmpty())
                postfix.append(operStack.pop());
        }
        return postfix.toString();
    }

    private static boolean isOperator(char с) {
        if (("+-/*^()".indexOf(с) != -1))
            return true;
        return false;
    }

    private static boolean isWhitespace(char с) {
        if ((" ".indexOf(с) != -1))
            return true;
        return false;
    }

    private static byte getPriority(char operation) {
        switch (operation) {
            case '(': return 0;
            case ')': return 1;
            case '+': return 2;
            case '-': return 3;
            case '*':
            case '/': return 4;
            default: return 5;
        }
    }
}
