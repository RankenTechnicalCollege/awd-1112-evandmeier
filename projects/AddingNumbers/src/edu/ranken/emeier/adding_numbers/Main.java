package edu.ranken.emeier.adding_numbers;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    Scanner scanner = new Scanner(System.in);

	    System.out.println("Please enter your username:");
	    String username = scanner.nextLine();

	    System.out.println("What is your age?");
	    int age = scanner.nextInt();
//	    String line = scanner.nextLine();
//	    int age = Integer.parseInt(line);

	    System.out.println("Hello, " + username + "!");
	    System.out.println("Age: " + age);
    }
}
