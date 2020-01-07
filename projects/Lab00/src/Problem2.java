import java.util.Scanner;

public class Problem2 {

    // #2 : Write a program that asks the user for their name and greets them with their name.
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("What is your name?");
        String name = input.nextLine();

        System.out.println("Hello, " + name);
    }
}
