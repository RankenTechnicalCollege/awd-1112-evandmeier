import java.util.ArrayList;

public class Problem15 {

    // #15 : Write a function that computes the total of a list of numbers. (Use ArrayList<Double>.)
    public static void main(String[] args) {
        ArrayList<Double> doubles = new ArrayList<Double>() {
            {
                add(12.0);
                add(-41.44);
                add(1.23452);
                add(0.02);
            }
        };

        System.out.printf("The sum of elements in the ArrayList is %f", computeTotal(doubles));
    }

    public static <T extends Number> double computeTotal(ArrayList<T> list) {
        double sum = 0;

        for (T element : list) {
            sum += element.doubleValue();
        }

        return sum;
    }
}
