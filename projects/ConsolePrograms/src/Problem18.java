import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Problem18 {

//    public interface PerfectSquare {
//        boolean check(int x);
//    }

    /*
        Write a function 'apply' that applies a function to every element of a list. Use it to print the first twenty
        perfect squares. The perfect squares can be found by multiplying each natural number with itself. The
        first few perfect squares are 1*1= 1, 2*2=4, 3*3=9, 4*4=16. Twelve for example is not a perfect square
        because there is no natural number m so that m*m=12.
     */
    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<Integer>() {
            {
                add(10);
                add(88);
                add(292);
                add(177);
                add(544);
            }
        };

        Predicate<Integer> checkPerfectSquare = (Integer x) -> {
            for (int i = 1; i < x; ++i) {
                if (i * i == x) {
                    return true;
                }
            }

            return false;
        };

        for (int i = 0; i < apply(integers, checkPerfectSquare).size(); i++) {
            System.out.printf("The first 20 perfect squares preceding %d are: ", integers.get(i));
            for (Integer element : apply(integers, checkPerfectSquare).get(i)) {
                System.out.print(element);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static List<List<Integer>> apply(List<Integer> integers, Predicate<Integer> f) {
        List<List<Integer>> returnList = new ArrayList<>();

        for (int element : integers) {
            int count = 0;
            List<Integer> list = new ArrayList<>();

            for (int i = 1; i < element; ++i) {
                if (count < 20) {
                    if (f.test(i)) {
                        list.add(i);
                        count++;
                    }
                }
            }

            returnList.add(list);
        }

        return returnList;
    }
}
