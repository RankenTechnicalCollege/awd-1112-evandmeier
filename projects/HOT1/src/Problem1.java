import java.util.Scanner;

public class Problem1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //String input = "";

        System.out.println("This program will convert a distances in inches, and convert it to centimeters.");
        //System.out.println("Please enter a distance in INCHES (-999 to exit)");
        //input = scanner.nextLine();

        // accept numbers from the user, until they enter -999
        for (;;) {
            System.out.println("Please enter a distance in INCHES (-999 to exit)");
            String input = scanner.nextLine();
            if (input.equals("-999")) { break; }

            try {
                double inches = Double.parseDouble(input);

                if (inches <= 0) {
                    System.out.println("Please make sure you enter a positive number!\n");
                } else {
                    System.out.printf("%.2f inches is equal to %.2f centimeters!\n\n",
                                        inches,
                                        convertToCentimeters(inches));
                }
            } catch (NumberFormatException e) {
                System.out.println("Make sure you enter a valid positive number!\n");
            }

            //System.out.println("Please enter a distance in INCHES (-999 to exit)");
            //input = scanner.nextLine();
        }
    }

    // This function converts a value in inches into its cm counterpart
    public static final double convertToCentimeters(double inches) { return (inches * 2.54); }
}
