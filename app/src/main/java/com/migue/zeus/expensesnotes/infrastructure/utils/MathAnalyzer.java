package com.migue.zeus.expensesnotes.infrastructure.utils;/* A Java program to evaluate a given expression where tokens are separated
by space.
Test Cases:
	"10 + 2 * 6"		 ---> 22
	"100 * 2 + 12"		 ---> 212
	"100 * ( 2 + 12 )"	 ---> 1400
	"100 * ( 2 + 12 ) / 14" ---> 100
*/

import java.util.Stack;

public class MathAnalyzer {
    public static double evaluate(String expression) {
        expression = expression.replace(",", "");
        char[] tokens = expression.toCharArray();

        // Stack for numbers: 'values'
        Stack<Double> values = new Stack<Double>();

        // Stack for Operators: 'ops'
        Stack<Character> ops = new Stack<Character>();

        for (int i = 0; i < tokens.length; i++) {
            char tChar = tokens[i];
            // Current token is a whitespace, skip it
            if (tokens[i] == ' ')
                continue;

            // Current token is a number, push it to stack for numbers
            if (isNumberCharacter(tokens[i])) {
                StringBuilder builder = new StringBuilder();
                builder.append(tokens[i]);
                // There may be more than one digits in number
                while ((i + 1) < tokens.length && isNumberCharacter(tokens[i + 1])) {
                    builder.append(tokens[++i]);
                }
                values.push(Double.parseDouble(builder.toString()));
            }

            // Current token is an opening brace, push it to 'ops'
            else if (tokens[i] == '(')
                ops.push(tokens[i]);

                // Closing brace encountered, solve entire brace
            else if (tokens[i] == ')') {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.pop();
            } else if (tokens[i] == '-' && values.empty()) {
                StringBuilder builder = new StringBuilder();
                i++;
                builder.append(tokens[i]);
                // There may be more than one digits in number
                while ((i + 1) < tokens.length && isNumberCharacter(tokens[i + 1])) {
                    builder.append(tokens[++i]);
                }
                values.push(Double.parseDouble(builder.toString()) * (-1));
            }
            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' ||
                    tokens[i] == '*' || tokens[i] == '/') {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }

        // Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        // Top of 'values' contains result, return it
        return values.pop();
    }

    private static boolean isNumberCharacter(char tChar) {
        return (tChar >= '0' && tChar <= '9') || tChar == '.';
    }

    // Returns true if 'op2' has higher or same precedence as 'op1',
    // otherwise returns false.
    public static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    // A utility method to apply an operator 'op' on operands 'a'
    // and 'b'. Return the result.
    public static double applyOp(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }

    // Driver method to test above methods
    public static void main(String[] args) {
        System.out.println(MathAnalyzer.evaluate("10 + 2 * 6"));
        System.out.println(MathAnalyzer.evaluate("100 * 2 + 12"));
        System.out.println(MathAnalyzer.evaluate("100 * ( 2 + 12 )"));
        System.out.println(MathAnalyzer.evaluate("100 * ( 2 + 12 ) / 14"));
    }
}