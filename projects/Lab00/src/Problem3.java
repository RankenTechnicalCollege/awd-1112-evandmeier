import java.util.Scanner;

public class Problem3 {

    // #3 : Modify the previous program such that only the users Alice and Bob are greeted with their names
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("What is your name?");
        String name = input.nextLine();

        if (name.equalsIgnoreCase("Alice") || name.equalsIgnoreCase("Bob")) {
            System.out.println("Hello, " + name);
        }
    }

}
