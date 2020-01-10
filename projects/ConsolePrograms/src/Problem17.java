import java.util.ArrayList;
import java.util.List;

public class Problem17 {

    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<Integer>() {
            {
                add(1);
                add(2);
                add(3);
                add(4);
                add(5);
            }
        };

        System.out.println("Sum Using For Loop: " + computeSumFor(integers));
        System.out.println("Sum Using While Loop: " + computeSumWhile(integers));
        System.out.println("Sum Using Recursion: " + computeSumRecursive(integers));
    }

    public static int computeSumFor(List<Integer> list) {
        int sum = 0;

        for (int i = 0; i < list.size(); ++i) {
            sum += list.get(i);
        }

        return sum;
    }

    public static int computeSumWhile(List<Integer> list) {
        int sum = 0;
        int index = 0;

        while (index < list.size()) {
            sum += list.get(index);

            index++;
        }

        return sum;
    }

    public static int computeSumRecursive(List<Integer> list) {
        return computeSumRecursive(list, list.size() - 1);
    }

    private static int computeSumRecursive(List<Integer> list, int lastIndex) {
        return lastIndex >= 0 ?
                list.get(lastIndex) + computeSumRecursive(list, lastIndex - 1) :
                0;
    }
}
