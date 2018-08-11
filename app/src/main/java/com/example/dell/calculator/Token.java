package com.example.dell.calculator;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.log;
import static java.lang.Math.log10;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.tan;

public class Token {
    int r;
    private String[] token;

    Token() {
        r = -1;
        token = new String[10];
    }

    void insert(String string) {
        if (r >= token.length - 1) {
            String[] temp = new String[token.length * 2];
            System.arraycopy(token, 0, temp, 0, r + 1);
            token = temp;
        }
        token[++r] = string;
    }

    private String get() {
        if (r == -1)
            return null;
        else {
            String temp = token[0];
            System.arraycopy(token, 1, token, 0, r);
            r--;
            return temp;
        }
    }

    String getLastElementOnly() {
        if (r == -1)
            return null;
        else
            return token[r];
    }

    private String getReverse() {

        if (r == -1)
            return null;
        else
            return token[r--];
    }


    private int getPriority(String opr) {
        switch (opr) {
            case ")":
                return 20;
            case "(":
                return 15;
            case "^":
            case "!":
            case "sin":
            case "cos":
            case "tan":
            case "In":
            case "log":
            case "%":
            case "sqrt":
                return 10;
            case "/":
            case "X":
                return 5;
            case "+":
            case "-":
            case "@":
                return 1;
            default:
                return 0;
        }
    }

    public String solve(Token tokens) {
        Token num = new Token();
        Token opr = new Token();
        double firstNum, secondNum;
        String result, op, s;
        try {
            while ((s = tokens.get()) != null) {
                if (!s.contains("+") && !s.contains("-") && !s.contains("X") && !s.contains("/")
                        && !s.contains("!") && !s.contains("(") && !s.contains(")") && !s.contains("sin") && !s.contains("@")
                        && !s.contains("cos") && !s.contains("tan") && !s.contains("sqrt") && !s.contains("In")
                        && !s.contains("log") && !s.contains("^") && !s.contains("%") && !s.contains("abs")) {
                    num.insert(s);
                } else if (s.equals(")")) {
                    while ((op = opr.getReverse()) != null && !op.equals("(")) {

                        if (isBinary(op.charAt(0))) {

                            if (num.r > 0) {
                                firstNum = Double.parseDouble(num.getReverse());
                                secondNum = Double.parseDouble(num.getReverse());
                                result = operation(op, firstNum, secondNum);
                                num.insert(result);

                            } else return "Invalid operation";
                        } else {
                            if (num.r > -1) {
                                firstNum = Double.parseDouble(num.getReverse());
                                result = operation(op, firstNum);
                                num.insert(result);
                            } else return "Invalid operation";
                        }
                    }
                } else {

                    if (opr.getLastElementOnly() == null || opr.getLastElementOnly().equals("(")
                            || getPriority(opr.getLastElementOnly()) < getPriority(s)) {
                        opr.insert(s);
                    } else {

                        while (getPriority(opr.getLastElementOnly()) >= getPriority(s) && !opr.getLastElementOnly().equals("(")) {
                            op = opr.getReverse();

                            if (op == null)
                                break;

                            if (isBinary(op.charAt(0))) {

                                if (num.r > 0) {
                                    firstNum = Double.parseDouble(num.getReverse());
                                    secondNum = Double.parseDouble(num.getReverse());
                                    result = operation(op, firstNum, secondNum);
                                    num.insert(result);
                                } else return "Invalid operation";
                            } else {

                                if (num.r > -1) {
                                    firstNum = Double.parseDouble(num.getReverse());
                                    result = operation(op, firstNum);
                                    num.insert(result);
                                } else return "Invalid operation";
                            }
                        }
                        opr.insert(s);
                    }
                }
            }
        }
        catch (Exception e){
            return "Invalid operation";
        }
        result = num.get();
        if(result==null)
            result="Wrong Format";
        return result;
    }

    private boolean isBinary(char op) {
        return (new Equation().isOperator(op) || op == '^');
    }

    private String operation(String op, double firstNum, double secondNum) {
        double result;
        switch (op) {
            case "+":
                result = secondNum + firstNum;
                break;
            case "-":
                result = secondNum - firstNum;
                break;
            case "/":
                if(firstNum==0) {
                    return "Invalid operation";
                }
                result = secondNum / firstNum;
                break;
            case "X":
                result = secondNum * firstNum;
                break;
            case "^":
                result = pow(secondNum, firstNum);
                break;
            default:
                return "Invalid binary operation";
        }
        if (result > pow(10, 308))
            return "+Infinity";
        else if (-result > pow(10, 308))
            return "-Infinity";
        return Double.toString(result);
    }

    private String operation(String op, double firstNum) {
        double result;
        switch (op) {
            case ")":
                result = firstNum;
                break;
            case "sin":
                result = sin(firstNum * PI / 180.0);
                break;
            case "cos":
                result = cos(firstNum * PI / 180.0);
                break;
            case "tan":
                result = tan(firstNum * PI / 180.0);
                break;
            case "In":
                if (firstNum > 0)
                    result = log(firstNum);
                else return "Invalid input";
                break;
            case "log":
                if (firstNum > 0)
                    result = log10(firstNum);
                else return "Invalid input";
                break;
            case "sqrt":
                if (firstNum >= 0)
                    result = sqrt(firstNum);
                else return "Invalid input";
                break;
            case "!":
                result = 1;
                if (firstNum != (int) firstNum || firstNum < 0.0) {
                    return "Invalid input";
                }
                for (int i = 2; i <= firstNum; i++) {
                    result *= i;
                }
                break;
            case "%":
                result = firstNum / 100.0;
                break;
            case "@":
                result = -firstNum;
                break;
            case "abs":
                result = abs(firstNum);
                break;
            default:
                return "Invalid unary operation";
        }
        if (result > pow(10, 16))
            return "+Infinity";
        else if (result < pow(10, -16) && result > -pow(10, -16))
            result = 0.0;
        else if (-result > pow(10, 16))
            return "-Infinity";
        return Double.toString(result);
    }
}
