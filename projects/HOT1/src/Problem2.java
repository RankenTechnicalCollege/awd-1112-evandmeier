public class Problem2 {

    public static void main(String[] args) {
        System.out.printf("The sum of the first 100 perfect squares is: %d", sumOfPerfectSquares(100));
    }

    /**
     * This function calculates and returns the sum of
     * the perfect squares of 1 .. n
     */
    public static int sumOfPerfectSquares(int n) {
        int sum = 0;

        for (int i = 1; i <= n; ++i) {
            sum += i * i; //Math.pow(i, 2);
        }

        return sum;
    }
}
