import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class HelperFunctions {
    private HashMap < String, Integer > icp = new HashMap < > ();
    private HashMap < String, Integer > isp = new HashMap < > ();
    ArrayList < String > operators;
    static Scanner sc = new Scanner(System.in);
    public static final String RESET_TEXT = "\u001B[0m";
    //A public constant field to hold code to change the text font color to RED
    public static final String RED_TEXT = "\u001B[31m";
    //A public constant field to hold code to change the text font color to GREEN
    public static final String GREEN_TEXT = "\u001B[32m";
    static String smile = "\uD83D\uDE0A";
    static String love = "\uD83D\uDC9C";
    static String woman = "\uD83D\uDC67";
    static String man = "\uD83D\uDC66";
    double operand2;
    double operand1;
    String expr3 = "";
    String expr4 = "";
    double temp;

    public void showIntroduction() {
        System.out.println("\n");
        System.out.println("College of Information and Computing Sciences");
        System.out.println(" Saint Louis University");
        System.out.println(" Baguio City ");
        System.out.println("---------------------------------------------");
        System.out.println(GREEN_TEXT + "Implementation of Stack Data Structures");
        for (int index = 0; index < 10; index++) {
            System.out.print(smile + love + smile + "");
        }
        System.out.println("\n");
        System.out.println(" Group 5 " + smile);
        System.out.println(" Developers: \n Saleena Marie Dongga-as" + woman + "\n Rhyen Jan Natividad" + man +
                "\n Jericho Mijares" + man + "\n Roger Jr. H Chegyem" + man + "\n Ron Andrei Vanzuela" + man +
                "\n Jayvee Palangdan" + man);
        System.out.println("\n");
        System.out.print("Please press a key to start the program...");
        sc.nextLine();
    }
    static int value(char c) {
        return (int)(c - '0');

    }
    static int evaluate(String exp) {
        // Base Case: Given expression is empty
        if (exp.length() == 0) return -1;

        // The first character must be
        // an operand, find its value
        int res = value(exp.charAt(0));

        // Traverse the remaining characters in pairs
        for (int i = 1; i < exp.length(); i += 2) {
            // The next character must be an operator, and
            // next to next an operand
            char opr = exp.charAt(i), opd = exp.charAt(i + 1);

            // If next to next character is not an operand
            if (isOperand(opd) == false) return -1;

            // Update result according to the operator
            if (opr == '+') res += value(opd);
            else if (opr == '-') res -= value(opd);
            else if (opr == '*') res *= value(opd);
            else if (opr == '/') res /= value(opd);

                // If not a valid operator
            else return -1;
        }
        return res;
    }
    static boolean isOperand(char c) {
        return (c >= '0' && c <= '9');

    }
    public HelperFunctions() {
        operators = new ArrayList < > (Arrays.asList("+", "-", "*", "/", "^", "(", ")"));
        icp.put("+", 1);
        icp.put("-", 1);
        icp.put("*", 3);
        icp.put("/", 3);
        icp.put("^", 6);

        isp.put("+", 2);
        isp.put("-", 2);
        isp.put("*", 4);
        isp.put("/", 4);
        isp.put("^", 5);
    }
    // returns true if the top has a precedence over symbol, otherwise returns false
    private boolean precedence(String top, String symbol) {
        int a = isp.getOrDefault(top, 0);
        int b = icp.getOrDefault(symbol, 0);
        return a > b;
    }

    // Converts an infix to postfix expression
    public String infixToPostfix(String infixExpression) {
        String token;
        String symbol = null;
        int i;
        String expression = infixExpression;
        String postfixExpression = "";
        LinkedListStack < String > operatorStack = new LinkedListStack < > ();
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s%-40s%-60s\n", "token", "postfixExpression", "operatorStack");
        while (!expression.isBlank()) {
            i = 0;
            token = String.valueOf(expression.charAt(i));
            if (i + 1 < expression.length()) {
                symbol = String.valueOf(expression.charAt(i + 1));
            }
            if (!operators.contains(token)) {
                while (!operators.contains(symbol) && i + 1 < expression.length()) {
                    i++;
                    if (token.compareTo(" ") == 0) {
                        throw new StackException("The expression should have no spaces");
                    }
                    token += String.valueOf(expression.charAt(i));
                    if (i + 1 < expression.length()) {
                        symbol = String.valueOf(expression.charAt(i + 1));
                    }
                }
                postfixExpression += token + " ";
                System.out.printf("%-20s%-40s%-60s\n", token, postfixExpression, operatorStack);
            } else { // token is an operator
                while (!operatorStack.isEmpty() && precedence(operatorStack.top(), token) && token.compareTo("(") != 0) {
                    String topToken = operatorStack.pop();
                    postfixExpression += topToken + " ";
                    System.out.printf("%-20s%-40s%-60s\n", token, postfixExpression, operatorStack);
                } // end while
                if (operatorStack.isEmpty() || token.compareTo(")") != 0) {
                    operatorStack.push(token);
                } else { // pop the open parenthesis and discard it
                    operatorStack.pop();
                }
                System.out.printf("%-20s%-40s%-60s\n", token, postfixExpression, operatorStack);
            } // end else
            expression = expression.substring(i + 1);
        } // end while

        // get all remaining operators from the stack
        while (!operatorStack.isEmpty()) {
            String topToken = operatorStack.pop();
            postfixExpression += topToken + " ";
            System.out.printf("%-20s%-40s%-60s\n", " ", postfixExpression, operatorStack);
        } // end while

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        return "Result: " + postfixExpression;
    }
    public String postfixToInfix(String postfixExpression) {
        String expression = postfixExpression;
        String infixExpression = "";
        LinkedListStack < String > operandStack = new LinkedListStack < > ();
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s%-40s%-60s\n", "token", "postfixExpression", "operatorStack");
        while (!expression.isBlank()) {
            int i = 0;
            String symbol = String.valueOf(expression.charAt(0));
            if (symbol.compareTo(" ") == 0) {
                expression = expression.substring(1);
                continue;
            }
            if (!operators.contains(symbol)) {
                while (!String.valueOf(expression.charAt(i + 1)).equals(" ")) {
                    i++;
                    symbol += String.valueOf(expression.charAt(i));
                }
                operandStack.push(symbol);
                System.out.printf("%-20s%-40s%-60s\n", symbol, postfixExpression, operandStack);
            } else {
                String operand2 = operandStack.pop();
                String operand1 = operandStack.pop();
                infixExpression = "(" + operand1 + symbol + operand2 + ")";
                operandStack.push(infixExpression);
                System.out.printf("%-20s%-40s%-60s\n", symbol, postfixExpression, operandStack);
            }
            //expression = expression.substring(1);
            expression = expression.substring(i + 1);
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        return "Result: " + operandStack.pop();
    }

    // Converts a postfix to infix expression

    public double evaluatePostfixExpression(String e) {

        System.out.printf("%-10s%-20s%-30s%-40s%-50s\n", "Symbol", "Operand 1", "Operand 2", "Value", "operand Stack");
        String expression = e;
        int i;
        LinkedListStack < Double > operandStack = new LinkedListStack < > ();
        double token = 0;
        while (!expression.isBlank()) {
            i = 0;
            String symbol = String.valueOf(expression.charAt(0));
            //            System.out.printf("%-10s%-20s%-30s%-40s%-50s\n", symbol, token, "Operand 2", "Value", "operand Stack");
            if (symbol.compareTo(" ") == 0) {
                expression = expression.substring(1);
                continue;
            }
            if (!operators.contains(symbol)) {
                while (!String.valueOf(expression.charAt(i + 1)).equals(" ")) {
                    i++;
                    symbol += String.valueOf(expression.charAt(i));
                    //                    System.out.printf("%-10s%-20s%-30s%-40s%-50s\n", symbol, token, "Operand 2", "Value", "operand Stack");
                }
                double valueOfSymbol = Double.parseDouble(symbol);
                operandStack.push(valueOfSymbol);
                expr4 = operand1 + symbol + operand2;
                int res = evaluate(expr4);
                //                System.out.println(res);

                System.out.printf("%-10s%-20s%-30s%-40s%-50s\n", symbol, operand1, operand2, temp, operandStack);
            } else {
                operand2 = operandStack.pop();
                operand1 = operandStack.pop();

                switch (symbol) {
                    case "+":
                        token = operand1 + operand2;
                        break;
                    case "-":
                        token = operand1 - operand2;

                        //                        System.out.printf("%-10s%-20s%-30s%-40s%-50s\n", "Symbol", token, "Operand 2", "Value", "operand Stack");
                        break;
                    case "*":
                        token = operand1 * operand2;

                        //                        System.out.printf("%-10s%-20s%-30s%-40s%-50s\n", "Symbol", token, "Operand 2", "Value", "operand Stack");
                        break;
                    case "/":
                        token = operand1 / operand2;

                        //                        System.out.printf("%-10s%-20s%-30s%-40s%-50s\n", "Symbol", token, "Operand 2", "Value", "operand Stack");
                        break;
                    case "^":
                        token = Math.pow(operand1, operand2);

                        //                        System.out.printf("%-10s%-20s%-30s%-40s%-50s\n", "Symbol", token, "Operand 2", "Value", "operand Stack");
                        break;
                }

                expr3 = operand1 + symbol + operand2;
                int res = evaluate(expr3);
                //                System.out.println("Hoyyyyyy" + res);
                temp = eval(expr3);
                operandStack.push(token);
                System.out.printf("%-10s%-20s%-30s%-40s%-50s\n", symbol, operand1, operand2, eval(expr3), operandStack);
            }
            //expression = expression.substring(1);
            expression = expression.substring(i + 1);


        }
        return operandStack.pop();
    }
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)` | number
            //        | functionName `(` expression `)` | functionName factor
            //        | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return +parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Missing ')'");
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    if (eat('(')) {
                        x = parseExpression();
                        if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
                    } else {
                        x = parseFactor();
                    }
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    public static void main(String[] args) {
        //         HelperFunctions call = new HelperFunctions();
        //        String s =  "6 2 3 + - 3 8 2 / + * 2 ^ 3 +";
        ////        call.evaluatePostfixExpression( s);
        //        System.out.println("The result is: " + call.evaluatePostfixExpression(s));
        //        String expr3 = "1+1";
        //        int res = evaluate(expr3);
        //       System.out.println(eval("1+1"));
    }
}