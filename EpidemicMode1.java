import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * A class for epidemic mode 1.
 * <p>
 * I separate these two modes into different programs. The first mode is simply read file then change cells and print out.
 *
 * @author Xiaoqian Yan ID: 6018373
 */
public class EpidemicMode1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> list = new ArrayList<>();
        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            if (!str.isEmpty()) {
                list.add(str);
            } else {
                getFinal(list);
                list.clear();
            }
        }
        getFinal(list); // Print out the last list.
    }

    /**
     * Method for change '.' to 'S' and print out the final result.
     *
     * @param list one universe read from file.
     */
    private static void getFinal(List<String> list) {
        // Convert the list to char array.
        char[][] ch = new char[list.size()][list.get(0).length()];
        for (int i = 0; i < ch.length; i++) {
            ch[i] = list.get(i).toCharArray();
        }
        // Set the flag to check there is something has been changed in the array. If nothing has been changed, it means
        // that the array is done, then print out the array.
        boolean isChanged = true;
        while (isChanged) {
            isChanged = false;
            for (int i = 0; i < ch.length; i++) {
                for (int j = 0; j < ch[0].length; j++) {
                    if (ch[i][j] != '.') continue;
                    int count = 0;
                    if (i > 0 && ch[i - 1][j] == 'S') count++;
                    if (i < ch.length - 1 && ch[i + 1][j] == 'S') count++;
                    if (j > 0 && ch[i][j - 1] == 'S') count++;
                    if (j < ch[0].length - 1 && ch[i][j + 1] == 'S') count++;
                    if (count >= 2) {
                        ch[i][j] = 'S';
                        isChanged = true;
                    }
                }
            }
        }
        for (int i = 0; i < ch.length; i++) {
            for (int j = 0; j < ch[0].length; j++) {
                System.out.print(ch[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
