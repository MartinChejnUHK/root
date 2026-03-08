package pro1;

public class NumericUtils {

    public static long gcd(long x, long y) {
        while (y != 0) {
            long remainder = x % y;
            x = y;
            y = remainder;
        }
        return x;
    }
}
