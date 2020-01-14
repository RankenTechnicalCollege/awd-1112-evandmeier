import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Problem12 {

    // #12 Write a function that reverses a list, preferably in place.
    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<Integer>() {
            {
                add(12);
                add(50);
                add(33);
                add(14);
                add(222);
            }
        };

        System.out.println("The original ArrayList: " + integers.toString());

        reverse(integers);
        System.out.println("Reversed ArrayList using create .reverseListInPlace(): " + integers.toString());

        integers = reverseList(integers);
        System.out.println("Reversing back to original ArrayList using created .reverseList(): " + integers.toString());
    }

    public static <T> void reverse(ArrayList<T> list) {
        for (int i = 0; i < (list.size() / 2); i++) {
            int endIndex = list.size() - 1 - i;
            T start = list.get(i);
            T end = list.get(endIndex);

            list.set(i, end);
            list.set(endIndex, start);
        }
    }

    public static <T> ArrayList<T> reverseList(ArrayList<T> list) {
        ArrayList<T> reverseList = new ArrayList<T>();

        for(int i = list.size() - 1; i >= 0; --i) {
            reverseList.add(list.get(i));
        }

        return reverseList;
    }
}
