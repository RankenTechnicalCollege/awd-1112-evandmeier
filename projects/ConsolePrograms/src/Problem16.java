import java.util.ArrayList;

public class Problem16 {

    // #16 : Write a function that tests whether a string is a palindrome.
    public static void main(String[] args) {
        String testString1 = "Able was I ere I saw Elba";
        String testString2 = "Hello bucko! I'm Evan!";

        System.out.printf("[True or False] : '%s' is a palindrome.\n", testString1);
        System.out.printf("Answer: %b\n\n", isPalindrome(testString1));

        System.out.printf("[True or False] : '%s' is a palindrome.\n", testString2);
        System.out.printf("Answer: %b", isPalindrome(testString2));
    }

    /*
        A palindrome is a sequence of characters that reads the same backwards as forwards
        EX: 1991
        EX: Able was I ere I saw Elba
        So essentially, we just have to check to see if the reverse of a string is the same as its original sequence.
     */
    public static boolean isPalindrome(String string) {
        if (string.equalsIgnoreCase(reverse(string))) {
            return true;
        }

        return false;
    }

    public static String reverse(String string) {
        char[] chars = string.toCharArray();

        for (int i = 0, endIndex = chars.length - 1; i < (chars.length / 2); i++, endIndex--) {
            char start = chars[i];
            char end = chars[endIndex];

            chars[i] = end;
            chars[endIndex] = start;
        }

        return new String(chars);
    }
}