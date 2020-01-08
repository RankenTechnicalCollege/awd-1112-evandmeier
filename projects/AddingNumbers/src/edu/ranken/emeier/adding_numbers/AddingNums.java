package edu.ranken.emeier.adding_numbers;

import java.util.Scanner;

public class AddingNums {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double num;
        double sum = 0;

        do {
            System.out.println("Please enter a number:");

            num = scanner.nextDouble();
            sum += num;
        } while(num != 0);

        System.out.printf("The total is %.3f\n", sum);
        System.out.println(String.format("The total is %.3f\n", sum));
    }
}
