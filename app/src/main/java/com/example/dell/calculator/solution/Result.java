package com.example.dell.calculator.solution;


import android.media.MediaCodec;

import static java.lang.Math.E;
import static java.lang.Math.PI;

public class Result extends Equation {

    private Token tokens;


    Result() {
        tokens = new Token();

    }

    private String get(String equation) {
        MediaCodec.CryptoInfo.Pattern pattern;
        char[] ch = equation.toCharArray();
        StringBuilder stringBuilder;

        tokens.insert("(");

        for (int i = 0; i < ch.length; ) {
            stringBuilder = new StringBuilder();

            while (i < ch.length && ((ch[i] >= 'a' && ch[i] <= 'z')||ch[i]=='I')) {
                stringBuilder.append(ch[i]);
                i++;
            }

            if (stringBuilder.length() > 0) {
                switch (stringBuilder.toString()) {
                    case "pi":
                        tokens.insert(Double.toString(PI));
                        break;
                    case "e":
                        tokens.insert(Double.toString(E));
                        break;
                    default:
                        tokens.insert(stringBuilder.toString());
                }
                stringBuilder.delete(0, stringBuilder.length());
            }

            while (i < ch.length && (ch[i] >= '0' && ch[i] <= '9' || ch[i] == '.')) {
                stringBuilder.append(ch[i]);
                i++;
            }

            if (stringBuilder.length() > 0) {
                tokens.insert(stringBuilder.toString());
                stringBuilder.delete(0, stringBuilder.length());
            }

            if (i < ch.length && (isOperator(ch[i]) || ch[i] == '(' || ch[i] == ')' || ch[i] == '!' || ch[i] == '^' || ch[i] == '%')) {

                if (tokens.getLastElementOnly().equals("(") && ch[i] == '-') {
                    tokens.insert("@");
                } else {
                    tokens.insert(ch[i] + "");
                }
                i++;
            }
        }

        for (int i = 0; i <= new Equation().countOpenParenthesis(equation); i++) {
            tokens.insert(")");
        }

        return tokens.solve(tokens);

    }


    public String resultOfEquation(String string) {

        StringBuilder equation = new StringBuilder();
        equation.append(string);

        if (equation.length() == 0) {
            return "0.0";
        } else {
            return get(string);
        }
    }


}
