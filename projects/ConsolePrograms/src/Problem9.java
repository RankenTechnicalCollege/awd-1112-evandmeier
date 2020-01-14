public class Problem9 {

    /*
        #9 : Write a program that prints the next 20 leap years.
        Leap years must be divisible by 4 but not 100
        EXCEPTION ^ if the year is divisible by 400 (1600, 2000, 2400, etc)
     */
    public static void main(String[] args) {
        StringBuilder result = new StringBuilder("The next 20 leap years are: ");

        for (int currentYear = 2020, leapYearCount = 0; leapYearCount < 20; currentYear++) {
            boolean isLeapYear =
                    (currentYear % 400 == 0) ||
                    (currentYear % 100 != 0 && currentYear % 4 == 0);

            /*if (currentYear % 400 == 0) {
                isLeapYear = true;
            } else if (currentYear % 100 == 0) {
                isLeapYear = false;
            } else if (currentYear % 4 == 0) {
                isLeapYear = true;
            } else {
                isLeapYear = false;
            }*/

            if (isLeapYear) {
                ++leapYearCount;
                if (leapYearCount == 20) {
                    result.append("and ").append(currentYear).append(".");
                } else {
                    result.append(currentYear).append(", ");
                }
            }
        }

        System.out.println(result);
    }
}
