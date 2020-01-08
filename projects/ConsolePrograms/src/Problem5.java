import java.util.Scanner;

public class Problem5 {

    // #5: Modify the previous program such that only multiples of three or five are considered in the sum
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
            if (i % 3 == 0 || i % 5 == 0) {
                sum += i;
            }
        }

        System.out.printf("The sum of 1 to %d is %d. (multiples of 3 & 5)", enteredNumber, sum);
    }
}
