import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Problem18 {

    /*
        Write a function 'apply' that applies a function to every element of a list. Use it to print the first twenty
        perfect squares. The perfect squares can be found by multiplying each natural number with itself. The
        first few perfect squares are 1*1= 1, 2*2=4, 3*3=9, 4*4=16. Twelve for example is not a perfect square
        because there is no natural number m so that m*m=12.
     */
    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 1; i <= 1000; ++i) {
            integers.add(i);
        }

        Predicate<Integer> checkPerfectSquare = (Integer x) -> {
            for (int i = 1; i < x; ++i) {
                if (i * i == x) {
                    return true;
                }
            }

            return false;
        };

        for (Integer element : apply(integers, checkPerfectSquare)) {
            System.out.println(element);
        }
}

    public static List<Integer> apply(Iterable<Integer> integers, Predicate<Integer> func) {
        List<Integer> returnList = new ArrayList<>();

        for (int element : integers) {
            if (func.test(element)) {
                returnList.add(element);
                if (returnList.size() >= 20) { break; }
            }
        }

        return returnList;
    }
}
