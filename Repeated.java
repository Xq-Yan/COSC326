import java.util.*;

/**
 * Implementation of Repeated digits
 *
 * @author Xiaoqian Yan ID: 6018373
 */
public class Repeated {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String temp = line;
            line = line.toUpperCase();
            String str1 = "";
            String str2 = "";
            if (line.charAt(0) != 'A' && line.charAt(0) != 'B') {   // If not start with "A" or "B" then print out
                System.out.println("Bad line: " + temp);
                continue;
            }
            String[] str = line.split(" ");
            if (str.length != 3) {                              // If the input is not a character with two integers
                System.out.println("Bad line: " + temp);
                continue;
            }
            if (str[1].matches(".*[A-Z]+.*") || str[2].matches(".*[A-Z]+.*")) { // If the integer with words
                System.out.println("Bad line: " + temp);
                continue;
            }
            if (str[0].length() > 1) {                         // If the first one is not a character
                System.out.println("Bad line: " + temp);
                continue;
            }
            if (str[2].length() > 10) {                       // If the last string is out of the bound of integer
                System.out.println("Bad line: " + temp);
                continue;
            }

            if (str[0].equals("A")) {
                int b = Integer.parseInt(str[1]); // Base b
                int n = Integer.parseInt(str[2]); // Integer n
                if (b <= 0 || n <= 0) {
                    System.out.println("Bad line: " + temp);
                    continue;
                }

                int[] result = findBlock(b, n);
                System.out.println("A " + result[0] + " " + result[1]);
            } else {
                int b = Integer.parseInt(str[1]); // Base b
                int c = Integer.parseInt(str[2]); // Base c
                int count = 2;
                int res = 0;
                while (true) {
                    str1 = convertToBase(b, count);
                    str2 = convertToBase(c, count);
                    if (isRepeated(str1) && isRepeated(str2)) {
                        res = count;
                        break;
                    } else count++;
                }
                System.out.println("B " + res);
            }

        }
    }

    /**
     * Method for convert the integer n from normal decimal to base b decimal.
     *
     * @param b The base read from scanner.
     * @param n The integer read from scanner.
     * @return the string after covert to base b decimal.
     */
    private static String convertToBase(int b, int n) {
        String res = "";
        int r = 0;  // reminder
        int num = n;
        while (num > 0) {
            r = num % b;
            num = num / b;
            //  10-15 means a-f
            if (r > 9) res = (char) ('a' + (r - 10)) + res;
            else res = r + res;
        }
        return res;
    }

    /**
     * Method for find out the longest block.
     *
     * @param b The base b.
     * @param n The integer n.
     * @return the block with the length.
     */
    private static int[] findBlock(int b, int n) {
        String[] convertNum = new String[n];
        int[] res = new int[2];
        int count = 0;   // count the length of the block
        HashMap<Integer, Integer> map = new HashMap<>();
        int start = 0;  // get the first repeated number
        for (int i = 1; i < n; i++) {
            convertNum[i] = convertToBase(b, i);
            if (isRepeated(convertNum[i])) {
                if (count == 0) {
                    start = i;
                }
                count++;
            } else {
                if (count != 0) {
                    map.put(start, count);  // set the start value as the key value.
                    count = 0;
                }
            }
        }
        map.put(start, count);
        int length = 0, value = 0, maxLength = 0;
        // find the longest block
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            length = entry.getValue();
            if (maxLength < length) {
                maxLength = length;
            }
        }
        // put the longest block and the value in the tempmap
        HashMap<Integer, Integer> tempmap = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            value = entry.getKey();
            length = entry.getValue();
            if (length == maxLength) {
                tempmap.put(value, length);
            }
        }
        // find the minimum start value
        int minValue = tempmap.keySet().iterator().next();
        for (Map.Entry<Integer, Integer> entry : tempmap.entrySet()) {
            value = entry.getKey();
            if (value <= minValue) {
                minValue = value;
            }

        }
        res[0] = minValue;
        res[1] = maxLength;
        return res;
    }

    /**
     * Method for convert the integer n from base b decimal to normal decimal.
     *
     * @param str The b base string.
     * @return true if the string is repeated, else return false.
     */
    private static boolean isRepeated(String str) {
        StringBuilder result = new StringBuilder();
        List list = new ArrayList();
        char[] temp = str.toCharArray();
        for (char c : temp) {
            if (!list.contains(c)) {
                result.append(c);
                list.add(c);
            }
        }
        return result.toString().length() != str.length();
    }

}
