import java.util.ArrayList;
import java.util.List;

public class Problem11 {

    // #11 : Write a function that return the largest element in a list.
    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<Integer>() {
            {
                add(1);
                add(-4);
                add(15);
                add(3);
            }
        };

        ArrayList<Double> doubles = new ArrayList<Double>() {
            {
                add(12.0);
                add(-41.44);
                add(1.23452);
                add(0.02);
            }
        };

        ArrayList<String> strings = new ArrayList<String>() {
            {
                add("Hi");
                add("Hello");
                add("Wassup");
                add("Howdy fella");
            }
        };

        System.out.println("The ArrayList<Integer> is : " + integers.toString());
        System.out.println("Its largest element is " + getLargest(integers) + ".\n");

        System.out.println("The ArrayList<Double> is : " + doubles.toString());
        System.out.println("Its largest element is " + getLargest(doubles) + ".\n");

        System.out.println("The ArrayList<Double> is : " + strings.toString());
        System.out.println("Its largest element is " + getLargest(strings) + ".\n");
    }

    public static <T extends Comparable<T>> T getLargest(List<T> list) {
        T largestElement = null;

        for (T item : list) {
            if (largestElement == null || largestElement.compareTo(item) < 0) {
                largestElement = item;
            }
        }

        return largestElement;
    }
}
