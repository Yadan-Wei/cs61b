/** Test Planet */
public class TestPlanet{
	public static void main(String[] args){
		checkCalcForceExertedBy();
		
	}
	/**
     *  Checks whether or not two Doubles are equal and prints the result.
     *
     *  @param  expected    Expected double
     *  @param  actual      Double received
     *  @param  label       Label for the 'test' case
     *  @param  eps         Tolerance for the double comparison.
     */
    private static void checkEquals(double actual, double expected, String label, double eps) {
        if (Math.abs(expected - actual) <= eps * Math.max(expected, actual)) {
            System.out.println("PASS: " + label + ": Expected " + expected + " and you gave " + actual);
        } else {
            System.out.println("FAIL: " + label + ": Expected " + expected + " and you gave " + actual);
        }
    }

    /**
     *  Checks the Planet class to make sure calcDistance works.
     */
    private static void checkCalcForceExertedBy() {
        System.out.println("Checking calcDistance...");

        Planet a = new Planet(3, 4, 5, 6, 7, "img1");
		Planet b = new Planet(0, 0,-6, 5, 3, "img2");

        checkEquals(a.calcForceExertedBy(b), 21*6.67e-11/25, "calcDistance()", 0.01);
	}
	
}