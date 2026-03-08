package pro1;

public class Fraction {
    private long numerator;
    private long denominator;

    public Fraction(long numerator, long denominator) {
        long divisor = NumericUtils.gcd(numerator, denominator);
        this.numerator = numerator / divisor;
        this.denominator = denominator / divisor;
    }

    public Fraction add(Fraction other) {
        long newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
        long newDenominator = this.denominator * other.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    @Override
    public String toString() {
        return numerator + " / " + denominator;
    }

    public static Fraction parse(String input) {
        String[] tokens = input.split("\\+");
        Fraction total = new Fraction(0, 1);

        for (String token : tokens) {
            String trimmed = token.trim();
            Fraction current;

            if (trimmed.contains("%")) {
                long value = Long.parseLong(trimmed.replace("%", "").trim());
                current = new Fraction(value, 100);
            } else {
                String[] components = trimmed.split("/");
                long num = Long.parseLong(components[0].trim());
                long den = Long.parseLong(components[1].trim());
                current = new Fraction(num, den);
            }

            total = total.add(current);
        }

        return total;
    }
}
