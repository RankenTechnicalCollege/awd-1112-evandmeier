import java.util.InputMismatchException;
import java.util.Scanner;

public class Problem4 {

    // #4. Write a program that asks the user for a number n and prints the sum of the numbers 1 to n
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int enteredNumber;

        //This loop will be able to handle invalid data
        do {
            try {
                System.out.println("Please enter a whole number.");
                enteredNumber = scanner.nextInt();

                break;
            } catch(Exception e) {
                System.out.println("[Error] Make sure you enter a WHOLE number.");
                scanner.next();
            }
        } while(true);

        //loop to enteredNumber and increment the accumulator
        int sum = 0;
        for(int i = 1; i <= enteredNumber; i++) {
            sum += i;
        }

        System.out.printf("The sum of 1 to %d is %d.", enteredNumber, sum);
    }
}
