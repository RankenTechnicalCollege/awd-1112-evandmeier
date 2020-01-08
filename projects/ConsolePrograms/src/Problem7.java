public class Problem7 {

    // #7 : Write a program that prints a multiplication table for numbers up to 12
    public static void main(String[] args) {
        for (int i = 1; i <= 12; i++) {
            for (int j = 1; j <= 12; j++) {
                // we use %4d because the max digit count in this case is 3
                System.out.printf("%4d", i *j);
            }

            System.out.println("test");
        }
    }
}
