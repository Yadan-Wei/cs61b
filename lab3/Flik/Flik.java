/** An Integer tester created by Flik Enterprises. */
public class Flik {
    public static boolean isSameNumber(Integer a, Integer b) {
    /**
     * see stackoverflow for reasons.
     * When you compile a number literal in Java and assign it to a Integer (capital I) the compiler emits:
     *
     * Integer b2 =Integer.valueOf(127)
     * This line of code is also generated when you use autoboxing.
     *
     * valueOf is implemented such that certain numbers are "pooled", and it returns the same instance for values smaller than 128.
     *
     * From the java 1.6 source code, line 621:
     *
     * public static Integer valueOf(int i) {
     *     if(i >= -128 && i <= IntegerCache.high)
     *         return IntegerCache.cache[i + 128];
     *     else
     *         return new Integer(i);
     * }
     * The value of high can be configured to another value, with the system property.
     *
     * -Djava.lang.Integer.IntegerCache.high=999
     * If you run your program with that system property, it will output true!
     *
     * The obvious conclusion: never rely on two references being identical, always compare them with .equals() method.
     */
    return a.equals(b);
    }
}
