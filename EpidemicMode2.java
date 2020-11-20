import java.util.*;
import java.util.List;

/**
 * A class for epidemic mode 2.
 * The program use bfs as search strategy to find out the minimum amount of initial sick cells.
 * First calculate the minimum total amount should be in the grids. At each stage, if the amount of sick cells
 * is less than the the current cells amount and the cells amount also less than the minimum total amount
 * then get the normal cell to be sick.
 * There are many correct results can be print out, I just print out the first correct one.
 *
 * @author Xiaoqian Yan ID: 6018373
 */
public class EpidemicMode2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> list = new ArrayList<>();
        while (sc.hasNextLine()) {
            String str = sc.nextLine();

            if (!str.isEmpty()) {
                list.add(str);
            } else {
                bfs(list);
                list.clear();
            }
        }
        bfs(list);
    }

    /**
     * Method for find out the universe with minimum sick cells.
     *
     * @param list one  universe read from file.
     */
    private static void bfs(List<String> list) {


        // Convert list to char array.
        char[][] ch = new char[list.size()][list.get(0).length()];
        for (int i = 0; i < ch.length; i++) {
            ch[i] = list.get(i).toCharArray();
        }
        int rowLen = ch.length;
        int colLen = ch[0].length;

        int amountI = 0;
        double totalAmount = 0;
        for (int m = 0; m < rowLen; m++) {
            for (int n = 0; n < colLen; n++) {
                if (ch[m][n] == 'I') amountI++;
            }
        }
        totalAmount = Math.ceil(rowLen + colLen) * 2 / 4 + amountI; // Calculate the minimum total amount of sick cell should be initialized.
        //System.out.println("total " + totalAmount);

        Queue<int[]> pos = new LinkedList<>(); // pos for position
        Queue<char[][]> grid = new LinkedList<>();  // grid for array
        pos.add(new int[]{0, 0}); // Start form the position 0 0, put the position into the pos queue.

        grid.add(ch);
        int min = Integer.MAX_VALUE;
        int rescount = 0;  // The answer can be multiple, we just need the first answer as we want,
        while (!grid.isEmpty() && !pos.isEmpty()) {
            char[][] curr = grid.poll();
            int[] idx = pos.poll();
            for (int i = idx[0]; i < rowLen; i++) {
                for (int j = 0; j < colLen; j++) {

                    if (i == idx[0] && j < idx[1]) continue; // If the index we already visited then continue.
                    if (i < curr.length - 1 && curr[i + 1][j] == 'S' || j < curr[0].length - 1 && curr[i][j + 1] == 'S')
                        continue; // If the bottom one or the left one is 'S' then continue.
                    if (i != 0 && colLen == 1 && i != rowLen - 2 && curr[i - 1][j] == 'S') continue;
                        // If there is only one column and the up one is 'S' then continue.
                    else if (colLen > 1 && i > 0 && curr[i - 1][j] == 'S') continue;
                    // If the up one is 'S' then continue.

                    if (j != 0 && rowLen == 1 && j != colLen - 2 && curr[i][j - 1] == 'S') continue;
                        // If there is only one row and the right one is 'S' then continue.
                    else if (rowLen > 1 && j > 0 && curr[i][j - 1] == 'S') continue;
                    // If the right one is 'S' then continue.
                    if (rowLen - colLen > 1 && i == colLen + 1 && j == 0) {
                        i++;
                        continue;
                    }

                    if (curr[i][j] == '.') {
                        int countI = 0, countS = 0, cellAmount = 0;
                        for (int m = 0; m <= i; m++) {
                            for (int n = 0; n <= j; n++) {
                                if (curr[m][n] == 'I') countI++;
                                if (curr[m][n] == 'S') countS++;
                            }
                        }

                        cellAmount = ((i + 1 + j + 1) * 2) / 4 + countI;
                        if (countS > cellAmount) continue;

                        if (countS < cellAmount && cellAmount <= totalAmount) {
                            curr[i][j] = 'S';
                            char[][] temp = new char[rowLen][colLen];
                            // Copy the current array to temp array.
                            for (int k = 0; k < rowLen; k++) {
                                for (int l = 0; l < colLen; l++) {
                                    temp[k][l] = curr[k][l];
                                }
                            }
                            getSick(temp);

                            int[] count = count(temp);
                            if (count[0] < rowLen / 2 + 1)
                                continue; // The amount of 'S' always tries to be filled up the first line.
                            // If the amount is less than the half length at first line, it means the situation is
                            // not correct, then just continue.

                            if (count[1] == 0) { // When the amount of neither sick nor immune cells is 0, it means we find the answer.
                                if (count[0] > min) return; // If new amount is greater than the minimum, just return.
                                if (rescount == 1)
                                    return; // Only print out the first answer then return, others will ignore.
                                min = count[0];  // Record the minimum amount.
                                System.out.println(count[0]);
                                for (int k = 0; k < rowLen; k++) {
                                    for (int l = 0; l < colLen; l++) {
                                        if (temp[k][l] == 'A') temp[k][l] = '.';
                                        System.out.print(temp[k][l]);
                                    }
                                    System.out.println();
                                }
                                System.out.println();
                                rescount++;
                            }
                            grid.add(temp);
                            pos.add(new int[]{i, j + 2}); // Two neighbour cells can not be 'S' at the same time, so j + 2.
                            curr[i][j] = '.';
                        }
                    }
                }
            }
        }

    }

    /**
     * Method for change '.' cell to 'A', set a different letter with 'S' to separate the sick cell
     * which get sick by initial sick cells.
     *
     * @param temp one universe get from the bfs method.
     * @return temp which get sick by initial sick cells.
     */
    private static char[][] getSick(char[][] temp) {

        boolean isChanged = true;
        while (isChanged) {
            isChanged = false;
            for (int i = 0; i < temp.length; i++) {
                for (int j = 0; j < temp[0].length; j++) {
                    if (temp[i][j] != '.') continue;
                    int count = 0;
                    if (i > 0 && (temp[i - 1][j] == 'S' || temp[i - 1][j] == 'A')) count++;  // up
                    if (i < temp.length - 1 && (temp[i + 1][j] == 'S' || temp[i + 1][j] == 'A'))  // down
                        count++;
                    if (j > 0 && (temp[i][j - 1] == 'S' || temp[i][j - 1] == 'A')) count++; //left
                    if (j < temp[0].length - 1 && (temp[i][j + 1] == 'S' || temp[i][j + 1] == 'A')) //right
                        count++;
                    if (count >= 2) {
                        temp[i][j] = 'A';
                        isChanged = true;
                    }
                }
            }
        }
        return temp;
    }

    private static int perimeter(char[][] grid) {
        if (grid == null) return 0;
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'S' || grid[i][j] == 'A') {
                    res += 4;
                    if (i > 0 && (grid[i - 1][j] == 'S' || grid[i - 1][j] == 'A')) {
                        res -= 2;
                    }
                    if (j > 0 && (grid[i][j - 1] == 'S' || grid[i][j - 1] == 'A')) {
                        res -= 2;
                    }
                }
            }
        }
        return res;
    }

    /*private static int perimeter1(char[][] grid) {
        if (grid == null) return 0;
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'S') {
                    res += 4;
                    if (i > 0 && grid[i - 1][j] == 'S') {
                        res -= 2;
                    }
                    if (j > 0 && grid[i][j - 1] == 'S') {
                        res -= 2;
                    }
                }
            }
        }
        return res;
    }*/

    /**
     * Method for count the initial.
     *
     * @param array of initial sick cells amount and neither sick nor immune cells amount.
     */
    private static int[] count(char[][] array) {
        // First index is count the amount of initial sick cells, second index is to count the amount of neither sick nor immune.
        int[] count = {0, 0};
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] == 'S') {
                    count[0]++;
                }
                if (array[i][j] == '.') count[1]++;
            }
        }
        return count;
    }
}
