import java.util.ArrayList;
import java.util.List;

public class Problem18 {

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

        for (int element : integers) {
            StringBuilder outputString =
                    new StringBuilder(String.format("The first 20 perfect squares preceding '%d' are: ",
                                                    element));

            for (int perfectSquare : apply(element)) {
                outputString.append(perfectSquare + " ");
            }

            System.out.println(outputString);
        }
    }

    public static ArrayList<Integer> apply(int number) {
        ArrayList<Integer> perfectSquares = new ArrayList<Integer>();

        outsideLoop : for (int i = 1; i < number; ++i) {
            for (int j = 1; j < i; ++j) {
                if (j * j == i) {
                    perfectSquares.add(i);

                    if (perfectSquares.size() < 19) {
                        continue outsideLoop;
                    } else {
                        break outsideLoop;
                    }
                }
            }
        }

        return perfectSquares;
    }
}
