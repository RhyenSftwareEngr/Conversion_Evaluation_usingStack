import java.util.Scanner;

public class testerClass {
    public static Scanner sc = new Scanner(System.in); // creating object of Scanner class
    static HelperFunctions convert = new HelperFunctions();

    public static void showMenu() {
        System.out.println();
        System.out.println("1: Convert Infix expression to Postfix expression");
        System.out.println("2: Convert Postfix expression");
        System.out.println("3: Evaluate Postfix expression");
        System.out.println("4: Quit");

        int ch = Integer.parseInt(sc.next()); // reading user's choice
        switch (ch) {
            case 1:
                System.out.println("\n\n");
                System.out.print("Your Results in Converting Infix to Postifix: ");

                //                poly1.addition(poly2);
                //                sc.nextLine();
                String expression = "";
                System.out.println("Enter an infix expression: ");
                expression = userInput(2);
                System.out.println(convert.infixToPostfix(expression));
                System.out.print("press enter to continue...");
                sc.nextLine();
                sc.nextLine();

                break;
            case 2:
                System.out.println("\n\n");
                System.out.print("Your Results in Converting Postfix to Infix: ");
                String expression2 = "";
                System.out.println("Enter a postfix expression: ");
                expression2 = userInput(2);
                System.out.println(convert.postfixToInfix(expression2));
                System.out.print("press enter to continue...");
                sc.nextLine();
                sc.nextLine();
                break;
            case 3:
                System.out.println("\n\n");
                HelperFunctions eval = new HelperFunctions();
                System.out.println("Input a Postfix expression");
                Scanner sc = new Scanner(System.in); //System.in is a standard input stream
                String str = sc.nextLine(); //reads string
                HelperFunctions call = new HelperFunctions();
                System.out.println("The result is: " + call.evaluatePostfixExpression(str));
                sc.nextLine();
                sc.nextLine();
                break;
            case 4:
                System.out.println("\nThank your for using our program! Feel free to come back.");
                for (int index = 0; index < 35; index++) {
                    System.out.print("");
                }
                System.exit(0);
            default:
                System.err.println("Invalid choice! Please make a valid choice.");
        }
    }
    public static String userInput(int task) {
        Scanner kbd = new Scanner(System.in);
        int temp = 0; //to let the block of the loop re-execute

        switch (task) {
            case 1:
                boolean proceed;
                do {
                    try {
                        proceed = false;
                        System.out.print("> ");
                        temp = Integer.parseInt(kbd.nextLine());
                    } catch (NumberFormatException exception) {
                        System.out.println("Invalid input, need to enter a number.\n");
                        proceed = true;
                    }
                } while (proceed);

            case 2:
                System.out.print("> ");
                return kbd.nextLine();

            default:
                System.out.println("There is an error.\n");
        }
        return Integer.toString(temp);
    }

    public static void main(String[] args) {
        HelperFunctions help = new HelperFunctions();
        help.showIntroduction();
        while (true) {
            showMenu(); //Displays the menu
            System.out.print("Make your choice: ");
        }
    }
}