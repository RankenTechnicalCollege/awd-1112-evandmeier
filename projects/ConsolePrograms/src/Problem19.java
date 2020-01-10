import java.util.ArrayList;
import java.util.List;

public class Problem19 {

    // #19 : Write a function that concatenates two lists and returns the combined list. [a,b,c], [1,2,3] → [a,b,c,1,2,3]
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
            }
        };

        System.out.println(combine(integers, strings).toString());
    }

    /*
        By writing this generically, we allow for much more versatility in calling this function.
        Essentially what is going on here is that we are forcing the elements in 'listA' and 'listB'
        to be of the type 'T', or children of the type 'T'.

        See 'Bounded Type Parameters' in the GENERICS section of the Java Quick Syntax Reference.
     */
    public static <T, R extends T, S extends T> List<T> combine(List<R> listA, List<S> listB) {
        List<T> newList = new ArrayList<T>();

        for (R element : listA) {
            newList.add(element);
        }

        for (S element : listB) {
            newList.add(element);
        }

        return newList;
    }
}
