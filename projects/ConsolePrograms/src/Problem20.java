import java.util.ArrayList;
import java.util.List;

public class Problem20 {

    // 20 : Write a function that combines two lists by alternating taking elements from both lists, e.g. [a,b,c],
    //[1,2,3] → [a,1,b,2,c,3].
    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<Integer>() {
            {
                add(1);
                add(2);
                add(3);
            }
        };

        ArrayList<String> strings = new ArrayList<String>() {
            {
                add("a");
                add("b");
                add("c");
                add("d");
            }
        };

        System.out.println(combineAlternate(integers, strings).toString());
    }

    public static <T, R extends T, S extends T> List<T> combineAlternate(List<R> listA, List<S> listB) {
        List<T> newList = new ArrayList<T>();

        // return the higher of the two sizes
        int size = Math.max(listA.size(), listB.size());

        // iterate until we reach the 'size' value
        for (int i = 0; i < size; ++i) {
            if (i < listA.size()) {
                newList.add(listA.get(i));
            }

            if (i < listB.size()) {
                newList.add(listB.get(i));
            }
        }

        return newList;
    }
}
