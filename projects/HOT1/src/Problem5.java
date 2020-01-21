import java.util.Scanner;

public class Problem5 {

    public static final double TAX_RATE = 0.08;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double purchaseTotal = 0.0; // holds subtotal
        double purchasePrice = 0.0; // holds current purchase price
        int itemCount = 0; // holds number of items

        // original prompt
        System.out.println("This program will create a receipt from multiple purchase entries.");
        System.out.printf("Please enter the price of item #%d. (Enter negative number to exit)\n", itemCount + 1);

        do {
            try {
                purchasePrice = Double.parseDouble(scanner.nextLine());

                if (purchasePrice < 0) {
                    break;
                }

                // no exceptions, increment count and add purchasePrice
                purchaseTotal += purchasePrice;
                ++itemCount;
            } catch(Exception e) {
                System.out.println("Make sure you enter a valid positive number!\n");
            }

            System.out.printf("Please enter the price of item #%d. (Enter negative number to exit)\n", itemCount + 1);
        } while (purchasePrice >= 0);

        System.out.printf("\nYou entered %d purchases. \nSUBTOTAL: $%.2f \nTAX: $%.2f \nTOTAL: $%.2f",
                            itemCount,
                            purchaseTotal,
                            purchaseTotal * TAX_RATE,
                            purchaseTotal * (TAX_RATE + 1));
    }
}
