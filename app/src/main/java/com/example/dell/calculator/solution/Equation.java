package com.example.dell.calculator.solution;


public class Equation {

    private int lastOprIndex(String string) {
        int opr[] = new int[17];
        int max;
        if (!string.contains("+") && !string.contains("-") && !string.contains("X") && !string.contains("/")
                && !string.contains("!") && !string.contains("(") && !string.contains(")") && !string.contains("sin")
                && !string.contains("cos") && !string.contains("tan") && !string.contains("sqrt") && !string.contains("In") && !string.contains("log")
                && !string.contains("^") && !string.contains("%") && !string.contains("e") && string.contains("pi"))
            return -1;
        opr[0] = string.lastIndexOf('+');
        opr[1] = string.lastIndexOf('-');
        opr[2] = string.lastIndexOf('/');
        opr[3] = string.lastIndexOf('X');
        opr[4] = string.lastIndexOf('!');
        opr[5] = string.lastIndexOf('(');
        opr[6] = string.lastIndexOf(')');
        opr[7] = string.lastIndexOf("sin");
        opr[8] = string.lastIndexOf("cos");
        opr[9] = string.lastIndexOf("tan");
        opr[10] = string.lastIndexOf("sqrt");
        opr[11] = string.lastIndexOf("In");
        opr[12] = string.lastIndexOf("log");
        opr[13] = string.lastIndexOf('^');
        opr[14] = string.lastIndexOf('%');
        opr[15] = string.lastIndexOf('e');
        opr[16] = string.lastIndexOf("pi");
        if (string.contains("sin"))
            opr[7] += 2;
        if (string.contains("cos"))
            opr[8] += 2;
        if (string.contains("tan"))
            opr[9] += 2;
        if (string.contains("sqrt"))
            opr[10] += 3;
        if (string.contains("In"))
            opr[11] += 1;
        if (string.contains("log"))
            opr[12] += 2;
        max = opr[0];
        for (int i = 1; i < opr.length; i++) {
            if (max < opr[i])
                max = opr[i];
        }
        return max;
    }

    String numberAppend(char number, String text) {
        StringBuilder stringBuilder = new StringBuilder();
        int index = -1;
        char lastChar = '@';
        stringBuilder.append(text);
        if (stringBuilder.length() > 0) {
            index = lastOprIndex(stringBuilder.toString());
            lastChar = stringBuilder.charAt(stringBuilder.length() - 1);
        }
        if (stringBuilder.substring(index + 1).length() < 15) {
            if (lastChar == ')' || lastChar == '!' || lastChar == 'i' || lastChar == 'e') {
                stringBuilder.append('X');
            }
            stringBuilder.append(number);
            return stringBuilder.toString();
        } else {
            return "error";
        }
    }

    String spacialOperatorAppend(String spacialOp, String text) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(text);
        int index = -1;
        char lastChar = '@';
        if (stringBuilder.length() > 0 && !spacialOp.equals("clear")) {
            index = lastOprIndex(stringBuilder.toString());
            lastChar = stringBuilder.charAt(stringBuilder.length() - 1);
        }
        switch (spacialOp) {
            case "backspace":
                if (isCharChar(lastChar)) {
                    if (lastChar == 'n' || lastChar == 's') {
                        if (stringBuilder.length()>2&&
                                (stringBuilder.charAt(stringBuilder.length() - 3) == 's' ||
                                stringBuilder.charAt(stringBuilder.length() - 3) == 'a' ||
                                stringBuilder.charAt(stringBuilder.length() - 3) == 't' ||
                                stringBuilder.charAt(stringBuilder.length() - 3) == 'c')) {
                            stringBuilder.delete(stringBuilder.length() - 3, stringBuilder.length());
                        } else {
                            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
                        }
                    } else if (lastChar == 't') {
                        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
                    } else if (lastChar == 'g') {
                        stringBuilder.delete(stringBuilder.length() - 3, stringBuilder.length());
                    } else if (lastChar == 'i') {
                        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
                    } else {
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    }
                } else if (stringBuilder.length() > 0) {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
                break;
            case "sign":
                if (stringBuilder.length() <= 0) {
                    stringBuilder.append("(-");
                } else if (index >0 && stringBuilder.substring(index - 1, index + 1).equals("(-")) {
                    stringBuilder.delete(index - 1, index + 1);
                } else {
                    StringBuilder temp = new StringBuilder();
                    temp.append(stringBuilder.substring(0, index + 1));
                    temp.append("(-");
                    temp.append(stringBuilder.substring(index + 1));
                    stringBuilder.delete(0, stringBuilder.length());
                    stringBuilder.append(temp);
                    temp.delete(0, temp.length());
                }
                break;
            case "decimal":
                StringBuilder temp = new StringBuilder();
                if (stringBuilder.length() > 0)
                    temp.append(stringBuilder.substring(index + 1));
                if (temp.toString().equals("")) {
                    if (lastChar == '!' || lastChar == ')') {
                        stringBuilder.append("X0.");
                    } else if (isCharChar(lastChar) || isOperator(lastChar)
                            || lastChar == '(' || stringBuilder.toString().equals("")) {
                        stringBuilder.append("0.");
                    }
                } else {
                    if (!temp.toString().contains("."))
                        stringBuilder.append(".");
                }
                break;
            case "equal":
                temp = new StringBuilder();
                temp.append(stringBuilder);
                stringBuilder.delete(0, stringBuilder.length());
                stringBuilder.append('=');
                stringBuilder.append(new Result().resultOfEquation(temp.toString()));
                break;
            case "parenthesis":
                int count = countOpenParenthesis(stringBuilder.toString());
                if (stringBuilder.length() == 0 || lastChar == '('
                        || isOperator(lastChar) || isCharChar(lastChar)) {
                    stringBuilder.append('(');
                } else if (count > 0 && (isNumber(lastChar) || lastChar == ')' || lastChar == '.' || lastChar == '!')) {
                    stringBuilder.append(')');
                } else {
                    stringBuilder.append("X(");
                }
                break;
            case "clear":
                if (stringBuilder.length() > 0)
                    stringBuilder.delete(0, stringBuilder.length());
                break;
        }
        return stringBuilder.toString();
    }


    String operationAppend(char op, String text) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(text);
        if (stringBuilder.length() == 0) {
            return "";
        }
        char lastChar = stringBuilder.charAt(stringBuilder.length() - 1);
        if (isOperator(lastChar)) {
            if (stringBuilder.charAt(stringBuilder.length() - 2) == '(') {
                if (lastChar != op && op == '-') {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    stringBuilder.append(op);
                }
            } else if (lastChar != op) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                stringBuilder.append(op);
            }
        } else if (lastChar == '(') {
            if (op == '-') {
                stringBuilder.append(op);
            }
        } else {
            stringBuilder.append(op);
        }
        return stringBuilder.toString();
    }


    private boolean isNumber(char c) {
        return (c >= '0' && c <= '9');
    }

    int countOpenParenthesis(String string) {
        return string.replace(")", "").length() - string.replace("(", "").length();
    }

    public boolean isCharChar(char c) {
        return (c >= 'a' && c <= 'z');
    }

    boolean isOperator(char c) {
        return (c == '+' || c == '-' || c == '/' || c == 'X');
    }

    public String scientificOperatorAppend(String scientificOperator, String text) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder temp;
        stringBuilder.append(text);
        int index = -1;
        char lastChar = '@';
        if (stringBuilder.length() > 0) {
            index = lastOprIndex(stringBuilder.toString());
            lastChar = stringBuilder.charAt(stringBuilder.length() - 1);
        }
        switch (scientificOperator) {
            case "fact":
                double f= 0.1;
                temp = new StringBuilder();
                temp.append(stringBuilder.substring(index + 1));
                if(!temp.toString().equals("")) {
                    f = Float.parseFloat(temp.toString());
                }
                if (stringBuilder.length() == 0 || isOperator(lastChar) || lastChar == '(') {
                    return "error : Please enter a number";
                }
                stringBuilder.substring(index + 1);
                if (lastChar == ')' || (!isCharChar(lastChar) && !temp.toString().equals("")
                        && f==(int)f)) {
                    stringBuilder.append('!');
                } else {
                    return "error : This is incorrect";
                }
                break;
            case "sqrt":
                if (stringBuilder.length() == 0 || isOperator(lastChar) || lastChar == '(') {
                    stringBuilder.append("sqrt(");
                } else if (isNumber(lastChar) || lastChar == '.' || lastChar == 'e' || lastChar == ')'
                        || lastChar == '!' || stringBuilder.toString().lastIndexOf("pi") == stringBuilder.length() - 2) {
                    stringBuilder.append("Xsqrt(");
                } else {
                    return "error : This is incorrect";
                }
                break;
            case "percent":
                if (isNumber(lastChar) || lastChar == '.' || lastChar == 'e' || lastChar == ')'
                        || stringBuilder.toString().lastIndexOf("pi") == stringBuilder.length() - 2) {
                    stringBuilder.append("%");
                } else {
                    return "error : This is incorrect";
                }
                break;
            case "sin":
                if (stringBuilder.length() == 0 || isOperator(lastChar) || lastChar == '(') {
                    stringBuilder.append("sin(");
                } else if (isNumber(lastChar) || lastChar == '.' || lastChar == 'e' || lastChar == ')'
                        || lastChar == '!' || stringBuilder.toString().lastIndexOf("pi") == stringBuilder.length() - 2) {
                    stringBuilder.append("Xsin(");
                } else {
                    return "error : This is incorrect";
                }
                break;
            case "cos":
                if (stringBuilder.length() == 0 || isOperator(lastChar) || lastChar == '(') {
                    stringBuilder.append("cos(");
                } else if (isNumber(lastChar) || lastChar == '.' || lastChar == 'e' || lastChar == ')'
                        || lastChar == '!' || stringBuilder.toString().lastIndexOf("pi") == stringBuilder.length() - 2) {
                    stringBuilder.append("Xcos(");
                } else {
                    return "error : This is incorrect";
                }
                break;
            case "tan":
                if (stringBuilder.length() == 0 || isOperator(lastChar) || lastChar == '(') {
                    stringBuilder.append("tan(");
                } else if (isNumber(lastChar) || lastChar == '.' || lastChar == 'e' || lastChar == ')'
                        || lastChar == '!' || stringBuilder.toString().lastIndexOf("pi") == stringBuilder.length() - 2) {
                    stringBuilder.append("Xtan(");
                } else {
                    return "error : This is incorrect";
                }
                break;
            case "in":
                if (stringBuilder.length() == 0 || isOperator(lastChar) || lastChar == '(') {
                    stringBuilder.append("In(");
                } else if (isNumber(lastChar) || lastChar == '.' || lastChar == 'e' || lastChar == ')'
                        || lastChar == '!' || stringBuilder.toString().lastIndexOf("pi") == stringBuilder.length() - 2) {
                    stringBuilder.append("XIn(");
                } else {
                    return "error : This is incorrect";
                }
                break;
            case "log":
                if (stringBuilder.length() == 0 || isOperator(lastChar) || lastChar == '(') {
                    stringBuilder.append("log(");
                } else if (isNumber(lastChar) || lastChar == '.' || lastChar == 'e' || lastChar == ')'
                        || lastChar == '!' || stringBuilder.toString().lastIndexOf("pi") == stringBuilder.length() - 2) {
                    stringBuilder.append("Xlog(");
                } else {
                    return "error : This is incorrect";
                }
                break;
            case "inverse":
                if (stringBuilder.length() == 0) {
                    stringBuilder.append("1/");
                } else if (lastChar != '(' && !isOperator(lastChar)) {
                    temp = new StringBuilder();
                    temp.append("1/(");
                    temp.append(stringBuilder);
                    temp.append(')');
                    stringBuilder.delete(0, stringBuilder.length());
                    stringBuilder.append(temp);
                } else
                    return "error : Incorrect input";
                break;
            case "ex":
                if (stringBuilder.length() == 0 || isOperator(lastChar) || lastChar == '(') {
                    stringBuilder.append("e^(");
                } else if (isNumber(lastChar) || lastChar == '.' || lastChar == 'e' || lastChar == ')'
                        || lastChar == '!' || stringBuilder.toString().lastIndexOf("pi") == stringBuilder.length() - 2) {
                    stringBuilder.append("Xe^(");
                } else {
                    return "error : This is incorrect";
                }
                break;
            case "square":
                if (isNumber(lastChar) || lastChar == '.' || lastChar == 'e' || lastChar == ')' ||
                        stringBuilder.toString().lastIndexOf("pi") == stringBuilder.length() - 2) {
                    stringBuilder.append("^(2)");
                } else {
                    return "error : This is incorrect";
                }
                break;
            case "power":
                if (isNumber(lastChar) || lastChar == '.' || lastChar == 'e' || lastChar == ')' ||
                        stringBuilder.toString().lastIndexOf("pi") == stringBuilder.length() - 2) {
                    stringBuilder.append("^(");
                } else {
                    return "error : This is incorrect";
                }
                break;
            case "abs":
                if (stringBuilder.length() == 0 || isOperator(lastChar) || lastChar == '(') {
                    stringBuilder.append("abs(");
                } else if (isNumber(lastChar) || lastChar == '.' || lastChar == 'e' || lastChar == ')'
                        || lastChar == '!' || stringBuilder.toString().lastIndexOf("pi") == stringBuilder.length() - 2) {
                    stringBuilder.append("Xabs(");
                } else {
                    return "error : This is incorrect";
                }
                break;
            case "pi":
                if (stringBuilder.length() == 0 || isOperator(lastChar)
                        || lastChar == '(') {
                    stringBuilder.append("pi");
                } else if (isNumber(lastChar) || lastChar == '.' || lastChar == 'e' || lastChar == ')'
                        || lastChar == '!' || stringBuilder.toString().lastIndexOf("pi") == stringBuilder.length() - 2) {
                    stringBuilder.append("Xpi");
                } else {
                    return "error : This is incorrect";
                }
                break;
            case "exp":
                if (stringBuilder.length() == 0 || isOperator(lastChar)
                        || lastChar == '(') {
                    stringBuilder.append("e");
                } else if (isNumber(lastChar) || lastChar == '.' || lastChar == 'e' || lastChar == ')'
                        || lastChar == '!' || stringBuilder.toString().lastIndexOf("pi") == stringBuilder.length() - 2) {
                    stringBuilder.append("Xe");
                } else {
                    return "error : This is incorrect";
                }
                break;
        }
        return stringBuilder.toString();
    }
}