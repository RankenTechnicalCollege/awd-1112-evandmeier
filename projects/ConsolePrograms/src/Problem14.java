import java.util.ArrayList;
import java.util.List;

public class Problem14 {

    // #14 : Write a function that returns a list of elements that are in odd positions in a list.
    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<Integer>() {
            {
                add(1);
                add(-4);
                add(15);
                add(3);
                add(12);
                add(93);
                add(74);
                add(2);
                add(11);
                add(833);
                add(20);
                add(41);
            }
        };

        List<Integer> oddIntegers = getOddElements(integers);
        for (int element : oddIntegers) {
            System.out.println(element);
        }
    }

    public static <T> List<T> getOddElements(List<T> items) {
        List<T> list = new ArrayList<T>();

        for (int i = 1; i < items.size(); i += 2) {
            list.add(items.get(i));
        }

        return list;
    }
}
