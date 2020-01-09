import java.util.ArrayList;
import java.util.List;

public class Problem13 {

    // #13 : Write a function that checks whether an element occurs in a list.
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

        if (exists(integers, 15)) {
            System.out.println("The item you checked DOES exist in the ArrayList!\n");
        } else {
            System.out.println("The item you checked DOES NOT exist in the ArrayList!\n");
        }

        if (exists(doubles, 33.21)) {
            System.out.println("The item you checked DOES exist in the ArrayList!\n");
        } else {
            System.out.println("The item you checked DOES NOT exist in the ArrayList!\n");
        }

        if (exists(strings, "howdy")) {
            System.out.println("The item you checked DOES exist in the ArrayList!\n");
        } else {
            System.out.println("The item you checked DOES NOT exist in the ArrayList!\n");
        }
    }

    public static <T> boolean exists(List<T> list, T item) {
        for (T element : list) {
            if (item.equals(element)) {
                return true;
            }
        }

        return false;
    }
}
