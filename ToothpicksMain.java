import javax.swing.JFrame;
import java.util.Scanner;

public class ToothpicksMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] str = line.split(" ");
        int n = 0;
        float r = 0;
        if (line.length() == 0 || line.matches("[a-zA-Z\\p{Punct}]+"))
            System.out.println("Please enter n or n followed by r");
        if (str.length == 1) {
            n = Integer.parseInt(str[0]);
            if (n < 0) System.out.println("The generation should not be negative");
            new ToothpicksPanel(n);
        }

        if (str.length > 2) System.out.println("The generation should not be negative");
        if (str.length == 2) {
            n = Integer.parseInt(str[0]);
            r = Float.parseFloat(str[1]);
            if (n < 0) System.out.println("The generation should not be negative");
            if (r <= 0) System.out.println("The ratio should greater than zero");
            new ToothpicksPanel(n, r);
        }
        /*String[] str = new String[args.length-1];
        int n = 0;
        float r = 0;
        if (str.length == 0 || str.length > 2) {
            System.out.println("Please enter n or n followed by r");
        }
        if (str.length == 1) {
            n = Integer.parseInt(str[0]);
            if (n < 0) System.out.println("The generation should not be negative");
            new ToothpicksPanel(n);
        }
        if (str.length == 2) {
            n = Integer.parseInt(str[0]);
            r = Float.parseFloat(str[1]);
            if (n < 0) System.out.println("The generation should not be negative");
            if (r <= 0) System.out.println("The ratio should greater than zero");
            new ToothpicksPanel(n, r);
        }*/
        JFrame frame = new JFrame("Toothpicks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (str.length == 2) frame.getContentPane().add(new ToothpicksPanel(n, r));
        else frame.getContentPane().add(new ToothpicksPanel(n));
        //frame.getContentPane().add(new ToothpicksPanel());
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
}