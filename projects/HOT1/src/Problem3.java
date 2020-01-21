import java.util.Scanner;

public class Problem3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int input;

        System.out.println("Please enter a valid, whole number!");

        // infinite loop that breaks when the user enters a valid number
        for (; ;) {
            try {
                input = Integer.parseInt(scanner.nextLine());

                break;
            } catch (Exception e) {
                System.out.println("Make sure you enter a valid whole number!");
            }
        }

        // check to see if the current index is a multiple of 3 or 5
        for (int i = 1; i <= input; ++i) {
            if (isMultiple(i, 3) == true && isMultiple(i, 5) == true) {
                System.out.println("FizzBuzz");
            } else if (isMultiple(i, 3)) {
                System.out.println("Fizz");
            } else if (isMultiple(i, 5)) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
    }

    /*
        Function returns true if 'number' is a multiple of 'multiple'.
        Otherwise the function returns false.
     */
    public static boolean isMultiple(int number, int multiple) {
        if (number % multiple == 0) { return true; }
        else { return false; }
    }
}
