public class Problem4 {

    public static void main(String[] args) {
        System.out.println(fib(1));
        System.out.println(fib(5));
        System.out.println(fib(10));
        System.out.println(fib(15));
    }


    public static int fib(int n) {
        if (n == 0) { return 0; }
        else if (n == 1) { return 1; } // the fib number is 1

        return fib(n - 1) + fib(n - 2);
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