import javax.swing.*;
import java.awt.*;

/**
 * Implementation of ToothpicksPanel
 * The basic idea is the final toothpicks length will be all the same and the sum should equals the default size(it is 800
 * in my situation). Try to find out the toothpick length at the final layer. Then calculate the default size of the
 * original one.
 *
 * @author Xiaoqian Yan ID: 6018373
 */
public class ToothpicksPanel extends JPanel {
    private static float r;
    private static float firstLength = 0;
    private static int n;
    private static final float defSize = 800;
    private static final int finalHeight = 804;
    private static final int finalWidth = 804;
    private static final int mid = (int) finalHeight / 2;

    /**
     * Constructor: set up the GUI when the input only represent  parameter n.
     *
     * @param n the nth generation.
     */
    public ToothpicksPanel(int n) {
        setPreferredSize(new Dimension(finalWidth, finalHeight));
        setBackground(Color.white);
        ToothpicksPanel.n = n;
        ToothpicksPanel.r = 1;
        // Calculate the final one toothpick length then find the first length of toothpick
        if (n == 0 || n == 1) firstLength = defSize;
        if (n % 2 == 0) firstLength = defSize / (((float) n + 2) / 2);
        else firstLength = defSize / (((float) n + 1) / 2);
    }

    /**
     * Constructor: set up the GUI when the input represents parameter n and parameter ratio.
     *
     * @param n the nth generation.
     * @param r the ratio.
     */
    public ToothpicksPanel(int n, float r) {
        setPreferredSize(new Dimension(finalWidth, finalHeight));
        setBackground(Color.white);
        ToothpicksPanel.n = n;
        ToothpicksPanel.r = r;
        // Calculate the final one toothpick length then find the length of first toothpick.
        // At this stage, the firstLength may bigger or smaller than default size, because now only can
        // guarantee the toothpicks at final layer is smaller than the default size.
        if (n == 0) firstLength = defSize;
        double scale = Math.pow(r, n);
        if (n % 2 == 0) {
            firstLength = (defSize / (((float) n + 2) / 2)) / (float) scale;
        } else {
            firstLength = (defSize / (((float) n + 1) / 2)) / (float) scale;
        }
        // When get the length of the first toothpick, calculate the total height and width of final layer.
        // Compare the maximum value of height and width,calculate with the default size,get the scale1.
        // Finally calculate the correct length of first toothpick
        double temp = firstLength;
        double width = firstLength;
        double height = 0;
        for (int i = 1; i <= n; i++) {
            temp *= r;
            if (i % 2 == 0) height += temp;
            else width += temp;
        }
        double scale1 = 0;
        if (height > width) scale1 = defSize / height;
        else scale1 = defSize / width;
       /* if (r < 1) {
            double scale2 = 0;
            if (r < 0.7) scale2 = 1.2;
            else if (r == 0.7 || r == 0.8) scale2 = 1.1;
            else scale2 = 1.09;
            firstLength = firstLength * (float) scale1 * (float) scale2;
        } else*/
        firstLength = firstLength * (float) scale1;
       // double scale2 = defSize / firstLength;
        //firstLength = firstLength * (float) (scale2 *0.8);
        //System.out.println(scale2);
        //System.out.println(firstLength);
    }

    /**
     * Method for draw lines.
     *
     * @param g the parameter of graphics.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        // Find out the length of the first basic line and the coordinates.
        int x1 = 2, y1 = mid, x2 = 2, y2 = mid;
        x1 = mid - Math.round(firstLength / 2);
        x2 = mid + Math.round(firstLength / 2);
        g.drawLine(x1, y1, x2, y2);
        paintLines(g, x1, y1, x2, y2, 1);
    }

    /**
     * Method for draw lines according to the preceding set.
     *
     * @param g    the parameter of graphics.
     * @param x1   The x coordinate of point one.
     * @param y1   The y coordinate of point one.
     * @param x2   The x coordinate of point two.
     * @param y2   The y coordinate of point two.
     * @param curr the current generation.
     */
    public void paintLines(Graphics g, int x1, int y1, int x2, int y2, int curr) {
        //int m1 = 0, m2 = 0, m3 = 0, m4 = 0, n1 = 0, n2 = 0, n3 = 0, n4 = 0;  // m as x, n as y
        if (curr == n + 1) {
            return;
        } else {
            double scale = Math.pow(r, curr);
            int halfSize = Math.round(firstLength * (float) scale / 2); // find the half line size of current generation
            // when current generation is odd
            if (curr % 2 == 1) {
                g.drawLine(x1, y1 - halfSize, x1, y1 + halfSize);
                g.drawLine(x2, y2 - halfSize, x2, y2 + halfSize);
                int m1 = x1;
                int n1 = y1 - halfSize;
                int m2 = x1;
                int n2 = y1 + halfSize;
                int m3 = x2;
                int n3 = y2 - halfSize;
                int m4 = x2;
                int n4 = y2 + halfSize;
                paintLines(g, m1, n1, m2, n2, curr + 1);
                paintLines(g, m3, n3, m4, n4, curr + 1);

                // when current generation is even
            } else if (curr % 2 == 0) {
                g.drawLine(x1 - halfSize, y1, x1 + halfSize, y1);
                g.drawLine(x2 - halfSize, y2, x2 + halfSize, y2);
                int m1 = x1 - halfSize;
                int n1 = y1;
                int m2 = x1 + halfSize;
                int n2 = y1;
                int m3 = x2 - halfSize;
                int n3 = y2;
                int m4 = x2 + halfSize;
                int n4 = y2;

                paintLines(g, m1, n1, m2, n2, curr + 1);
                paintLines(g, m3, n3, m4, n4, curr + 1);
            }
        }
    }
}
