public class Problem8 {

    // #8 : Write a program that prints all prime numbers less than 1,000,000.
    public static void main(String[] args) {
        // Prime numbers are positive numbers that only divisible by itself and 1

        // loop through all numbers from 1 - 1,000,000
        for (int i = 2; i < 1_000_000; i++) {
            // loop through all numbers from 2 (the first prime number) to i
            boolean isPrime = true;

            for (int j = 2, sqrt = (int) Math.sqrt(i); j < sqrt; j++) {
                // break the loop if the numbers are divisible
                if (i % j == 0) {
                    isPrime = false;
                }
            }

            // if the number is prime, print it
            if(isPrime) {
                System.out.println(i);
            }
        }
    }
}