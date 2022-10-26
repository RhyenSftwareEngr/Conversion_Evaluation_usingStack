import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class ConvertExpression {
    private HashMap<String, Integer> icp = new HashMap<>();
    private HashMap<String, Integer> isp = new HashMap<>();
    ArrayList<String> operators;
    public ConvertExpression() {
        operators = new ArrayList<>(Arrays.asList("+", "-", "*","/","^", "(", ")"));
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
        int a = isp.getOrDefault(top,0);
        int b = icp.getOrDefault(symbol,0);
        return a > b;
    }

    // Converts an infix to postfix expression
    public String infixToPostfix(String infixExpression){
        String token;
        String symbol = null;
        int i;
        String expression = infixExpression;
        String postfixExpression = "";
        LinkedStack<String> operatorStack = new LinkedStack<>();
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s%-40s%-60s\n", "token", "postfixExpression", "operatorStack");
        while (!expression.isBlank()) {
            i = 0;
            token = String.valueOf(expression.charAt(i));
            if(i+1<expression.length()) {
                symbol = String.valueOf(expression.charAt(i + 1));
            }
            if (!operators.contains(token)) {
                while(!operators.contains(symbol) && i+1 < expression.length() ){
                    i ++;
                    if(token.compareTo(" ")== 0){
                        throw new StackException("The expression should have no spaces");
                    }
                    token += String.valueOf(expression.charAt(i));
                    if(i+1<expression.length()) {
                        symbol = String.valueOf(expression.charAt(i + 1));
                    }
                }
                postfixExpression += token + " ";
                System.out.printf("%-20s%-40s%-60s\n", token, postfixExpression, operatorStack);
            }else { // token is an operator
                while (!operatorStack.isEmpty() && precedence(operatorStack.top(),token) && token.compareTo("(") != 0){
                    String topToken = operatorStack.pop();
                    postfixExpression += topToken + " ";
                    System.out.printf("%-20s%-40s%-60s\n", token, postfixExpression, operatorStack);
                } // end while
                if (operatorStack.isEmpty() || token.compareTo(")") != 0){
                    operatorStack.push(token);
                } else {// pop the open parenthesis and discard it
                    operatorStack.pop();
                }
                System.out.printf("%-20s%-40s%-60s\n", token, postfixExpression, operatorStack);
            } // end else
            expression = expression.substring(i+1);
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

    // Converts a postfix to infix expression
    public String postfixToInfix(String postfixExpression){
        String expression = postfixExpression;
        String infixExpression = "";
        LinkedStack<String> operandStack = new LinkedStack<>();
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s%-20s%-30s%-40s%-50s\n", "Symbol", "Operand 1", "Operand 2", "Value", "operand Stack");
        while(!expression.isBlank()){
            int i = 0;
            String symbol = String.valueOf(expression.charAt(0));
            if(symbol.compareTo(" ")== 0){
                expression = expression.substring(1);
                continue;
            }
            if(!operators.contains(symbol)){
                while(!String.valueOf(expression.charAt(i+1)).equals(" ")) {
                    i++;
                    symbol += String.valueOf(expression.charAt(i));
                }
                operandStack.push(symbol);
                System.out.printf("%-10s%-20s%-30s%-40s%-50s\n", symbol, " ", " ", infixExpression, operandStack);
            } else{
                String operand2 = operandStack.pop();
                String operand1 = operandStack.pop();
                infixExpression = "(" + operand1 + symbol + operand2 + ")";
                operandStack.push(infixExpression);
                System.out.printf("%-10s%-20s%-30s%-40s%-50s\n", symbol, operand1, operand2, infixExpression, operandStack);
            }
            //expression = expression.substring(1);
            expression = expression.substring(i+1);
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        return "Result: " + operandStack.pop();
    }

    public double evaluatePostfixExpression(String e) {
        String expression = e;
        int i;
        LinkedStack<Double> operandStack = new LinkedStack<>();
        double token = 0;
        while (!expression.isBlank()) {
            i = 0;
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
                double valueOfSymbol = Double.parseDouble(symbol);
                operandStack.push(valueOfSymbol);
            } else {
                double operand2 = operandStack.pop();
                double operand1 = operandStack.pop();

                switch(symbol){
                    case "+":
                        token = operand1 + operand2;
                        break;
                    case "-":
                        token = operand1 - operand2;
                        break;
                    case "*":
                        token = operand1 * operand2;
                        break;
                    case "/":
                        token = operand1 / operand2;
                        break;
                    case "^":
                        token = Math.pow(operand1,operand2);
                        break;
                }
//                if(symbol.equals("+")){
//                    token = operand1 + operand2;
//                }
                operandStack.push(token);
            }
            //expression = expression.substring(1);
            expression = expression.substring(i + 1);

        }
        System.out.println(operandStack.pop());
        return operandStack.pop();
    }

    public static void main(String[] args) {
//        ConvertExpression call = new ConvertExpression();
//        String s =  "4 55 + 62 23 - *";
//        call.evaluatePostfixExpression( s);
    }
}
