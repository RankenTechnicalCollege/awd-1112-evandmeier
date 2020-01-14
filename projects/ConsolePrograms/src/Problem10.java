import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Problem10 {

    /*
        #10 : Write a guessing game where the user has to guess a secret number. After every guess the program
              tells the user whether their number was too large or too small. At the end, the number of tries
              should be printed. It counts only as one try if they input the same number multiple times consecutively.
     */

    public static void main(String[] args) {
        // Holds a list of already guessed inputs (prevents duplications on guessCount)
        ArrayList<Integer> guesses = new ArrayList<>();

        // Holds the number of guesses for each game
        int guessCount = 0;

        // Generate the random number that the user will guess against
        Random random = new Random();
        int randomNumber = random.nextInt(100) + 1;

        Scanner scanner = new Scanner(System.in);
        int userGuess = 0;

        System.out.println("We've made our selection! Our number is between 1 and 100");
        while (userGuess != randomNumber) {
            System.out.printf("Please make guess #%d\n", guessCount + 1);

            // make sure the user input is a valid number
            try {
                userGuess = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Make sure you enter a valid number!");
                continue;
            }

            // if the user has already guessed the entered number (don't count guess)
            if (guesses.contains(userGuess)) {
                System.out.printf("You've already guessed %d!\nTo remind you, your previous guess was %d.\n\n",
                        userGuess, guesses.get(guesses.size() - 1));
                continue;
            }

            // if the guess doesn't fit the default range (don't count guess)
            if (userGuess < 1 || userGuess > 100) {
                System.out.println("Make sure your guess must be between 1 and 100!");
                continue;
            }

            // give user feedback, increment guessCount, add guess to guesses
            guesses.add(userGuess);
            ++guessCount;

            if (userGuess > randomNumber)
                System.out.println("Your number is HIGHER than my number.");
            else if (userGuess < randomNumber)
                System.out.println("Your number is LOWER than my number.");
        }

        System.out.printf("Congratulations! You guessed my number in %d attempts!", guessCount);
    }
}
