import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner lyx = new Scanner(System.in);  

        System.out.println("Welcome to my Calculator");

        while (true) {
            try {
                System.out.println("Enter a numbers:");
                System.out.println("+, -, *, /");
                String expression = lyx.nextLine();

                double result = lyxEvaluateExpression(expression);

                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error: Invalid expression. Please enter a valid mathematical expression.");
            }

            String choice = lyxAskToContinue(lyx);
            if (choice.equalsIgnoreCase("no")) {
                System.out.println("Thanks 4 using maem >.<");
                break;
            } else if (choice.equalsIgnoreCase("yes")) {
                lyxClearPreviousInput();
                continue;
            } else {
                System.out.println("Error: Please enter 'yes' or 'no' only.");
            }
        }
    }

    private static String lyxAskToContinue(Scanner lyx) {
        String choice = "";
        while (true) {
            System.out.print("Would you like to evaluate another expression? (yes/no): ");
            choice = lyx.nextLine().trim();
            if (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no")) {
                break;
            } else {
                System.out.println("Error: Invalid input! Please type 'yes' or 'no'.");
            }
        }
        return choice;
    }

    private static void lyxClearPreviousInput() {
        System.out.print("\033[H\033[2J");
        System.out.flush();  
    }

    private static double lyxEvaluateExpression(String expression) {
        expression = expression.replaceAll(" ", "");

        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        int i = 0;
        while (i < expression.length()) {
            char currentChar = expression.charAt(i);

            if (Character.isDigit(currentChar) || currentChar == '.') {
                StringBuilder num = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    num.append(expression.charAt(i));
                    i++;
                }
                numbers.push(Double.parseDouble(num.toString()));
            }
            else if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/') {
                while (!operators.isEmpty() && lyxHasPrecedence(currentChar, operators.peek())) {
                    numbers.push(lyxApplyOperator(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(currentChar);
                i++;
            } else {
                throw new IllegalArgumentException("Invalid character in expression");
            }
        }

        while (!operators.isEmpty()) {
            numbers.push(lyxApplyOperator(operators.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }

    private static double lyxApplyOperator(char operator, double b, double a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private static boolean lyxHasPrecedence(char current, char stackTop) {
        if (stackTop == '+' || stackTop == '-') {
            return current == '*' || current == '/';
        }
        return false;
    }
}
