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
            }
        };

        System.out.println("The original ArrayList: " + integers.toString());

        reverseListInPlace(integers);
        System.out.println("Reversed ArrayList using create .reverseListInPlace(): " + integers.toString());

        integers = reverseList(integers);
        System.out.println("Reversing back to original ArrayList using created .reverseList(): " + integers.toString());
    }

    public static <T> void reverseListInPlace(ArrayList<T> list) {
        for (int i = 0, j = list.size() - 1; i < j; ++i) {
            list.add(i, list.remove(j));
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
