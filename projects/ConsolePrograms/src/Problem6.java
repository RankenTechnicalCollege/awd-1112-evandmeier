import java.util.Scanner;

public class Problem6 {

    /*
        #6: Write a program that asks the user for a number n and gives them the option
            to choose between computing the sum (+) and computing the product (*) of 1,...,n.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int enteredNumber = 0;
        boolean isNumberEntered = false;

        //This loop will be able to handle invalid data
        do {
            try {
                System.out.println("Please enter a whole number:");
                enteredNumber = scanner.nextInt();
                isNumberEntered = true;
            } catch(Exception e) {
                System.out.println("[Error] Make sure you enter a WHOLE number.");
                scanner.next();
            }
        } while(!isNumberEntered);

        //This loop handles entry errors for the second prompt
        boolean isDone = false;
        String input = null;
        do {
            if (input == null) {
                System.out.println("Would you like to add (+) or multiply (*) (-1 to exit)");
            }

            input = scanner.nextLine().trim();

            if ("".equals(input)) {
                // do nothing
            } else if ("-1".equals(input)) {
              isDone = true;
            } else if ("+".equals(input)) {
                int sum = 0;
                for (int i = 1; i <= enteredNumber; i++) {
                    sum += i;
                }

                // print out the results, and reset the input back to null
                System.out.printf("The sum of 1 to %d is %d", enteredNumber, sum);
                System.out.println();
                input = null;
//                isCalculationPerformed = true;
            } else if ("*".equals(input)) {
                int product = 1;
                for (int i = 1; i <= enteredNumber; i++) {
                    product *= i;
                }

                // print out the results, and reset the input back to null
                System.out.printf("The product of 1 to %d is %d", enteredNumber, product);
                System.out.println();
                input = null;
//                isCalculationPerformed = true;
            } else {
                System.out.println("Enter '+' for addition, or '*' for multiplication.");
                System.out.println();
            }
        } while(!isDone);
    }

}
