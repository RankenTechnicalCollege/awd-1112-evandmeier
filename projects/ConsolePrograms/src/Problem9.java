public class Problem9 {

    /*
        #9 : Write a program that prints the next 20 leap years.
        Leap years must be divisible by 4 but not 100
        EXCEPTION ^ if the year is divisible by 400 (1600, 2000, 2400, etc)
     */
    public static void main(String[] args) {
        String result = "The next 20 leap years are: ";
        int leapYearCount = 0;
        int currentYear = 2020;

        while(leapYearCount < 20) {
            boolean isLeapYear = false;

            if (currentYear % 400 == 0) {
                isLeapYear = true;
            } else if (currentYear % 100 == 0) {
                isLeapYear = false;
            } else if (currentYear % 4 == 0) {
                isLeapYear = true;
            } else {
                isLeapYear = false;
            }

            if (isLeapYear) {
                ++leapYearCount;
                if (leapYearCount == 20) {
                    result += "and " + currentYear + ".";
                } else {
                    result += currentYear + ", ";
                }
            }
            currentYear++;
        }

        System.out.println(result);
    }
}
