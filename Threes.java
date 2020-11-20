/**
 * A class for  Threes.
 *
 * @author Xiaoqian Yan ID: 6018373
 */
public class Threes {
    /**
     * Method for determine the greatest common divisor between number a and number b.
     *
     * @param a The first number.
     * @param b The second number.
     * @return The greatest common divisor of number a and number b.
     */
    /*private static int getGCD(int a, int b) {
        if (a % b == 0) return b;
        if (b > a) {
            int temp = b;
            b = a;
            a = temp;
        }
        return getGCD(a - b, b);
    }*/
    private static int getGCD(int a, int b) {
        if (a % b == 0) return b;
        else return getGCD(b, a % b);
    }

    /**
     * Method that print out first 70 sets ordered by increasing z.
     * Because of no two of x,y or z share a common factor other than 1. There should be one even number and two
     * odd numbers or three odd numbers.
     * If the even number is on the left hand side, one of x or y is even and another is odd.
     * The square numbers will be one even add one odd,and the sum will be odd.
     * On the right hand side if z is odd, z^3 + 1 will be even because z^3 is odd. Therefore left hand side is not
     * match right hand side. So the even number cannot at the left hand side.
     * If both x and y are odd, the sum of their square number will be even number on the left hand side.
     * Then z will be even and z^3 will be even too. The sum of Z^3 +1 then is odd,left hand side is not match right
     * hand side either. The even number cannot at the right hand side.
     * As a result, x,y,z should be all odd numbers. This is why x,y,z add 2 when increase.
     */
    public static void orderZ() {
        int z = 1, count = 1;
        while (count <= 70) {
            int maxXY = (int) Math.sqrt((long) z * z * z + 1);
            for (int x = z + 2; x < maxXY; x += 2) {
                if (getGCD(z, x) != 1) continue;
                long sqX = (long) x * x;
                long sqY = (long) z * z * z + 1 - sqX;
                int y = (int) Math.sqrt(sqY);
                if (y < x) break;
                // Check if y*y equals sqY. Just in case something happens for example sqY=9, y = 2.99 = (int)2.
                // Then y*y = 4, the new sqY is not equals right - x*x.
                if ((long) y * y == sqY && getGCD(z, y) == 1 && getGCD(y, x) == 1) {
                    System.out.println(count + " " + x + " " + y + " " + z);
                    if (count++ == 70) return;
                }
            }
            z += 2;
        }
    }

    /**
     * Method that print out first 70 sets ordered by increasing x.
     */
    private static void orderX() {
        int x = 3, count = 1;
        while (count <= 70) {
            long sqX = (long) x * x;
            for (int z = 1; z < x; z += 2) {
                if (getGCD(z, x) != 1) continue;
                long sqY = (long) z * z * z + 1 - sqX;
                int y = (int) Math.sqrt(sqY);
                if (y < x) continue;
                if ((long) y * y == sqY && getGCD(z, y) == 1 && getGCD(y, x) == 1) {
                    System.out.println(count + " " + x + " " + y + " " + z);
                    if (count++ == 70) return;
                }
            }
            x += 2;
        }
    }


    /**
     * The main method that print out the result.
     */
    public static void main(String[] args) {
        orderX();
        System.out.println();
        orderZ();
    }
}
