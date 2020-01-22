public class Problem4 {

    public static void main(String[] args) {
        System.out.println(getFib(1));
        System.out.println(getFib(5));
        System.out.println(getFib(10));
        System.out.println(getFib(15));
    }


    public static int getFib(int n) {
        if (n == 0) { return 0; }
        else if (n == 1) { return 1; } // the fib number is 1

        return getFib(n - 1) + getFib(n - 2);
    }

    /*
        EXAMPLE SEQUENCE:
                    f(4)
                f(3)      f(2)
            f(2)  f(1)  f(1)  f(0)
          f(1)  f(0)

          f(4) = 1 + 1 + 1;

          f(5) =    f(3)    +    f(2)
                  f(2) + f(1)   f(1) + f(0)
                f(1) + f(0)
               = 1 + 1 + 1 + 1 + 1

          f(6) =                f(5)                    f(4)
                            f(4) + f(3)             f(3) + f(2)
                        f(3) + f(2) + f(2) + f(2) + f(2) + f(1) + f(1) + f(0)
                      f(2) + f(1) + f(1) + f(0) + f(1) + f(0) + f(1) + f(0) + f(1) + f(0)
                    f(1) + f(0)

              f(6) = 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1;
     */

}