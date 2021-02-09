package com.lilykmtd;

import java.util.*;

public class Main {
    public static final int DEFPRECISION = 10;

    public static void main(String[] args) {
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter expression");
            String exp = in.nextLine();
            exp = exp.replaceAll("\\s+", "");

//            System.out.println("Enter precision");
//            int p = Integer.parseInt(in.nextLine());
            try {
                double res = Calculator.calculateExpressionWithPrecision(exp, DEFPRECISION);
                System.out.println("result: " + res);
            } catch (Exception e) {
                System.out.println("Invalid expression");
            }
        }
    }
}