package usts.pycro.juc;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-16 3:43 PM
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(1.0 / 0);
        try {
            System.out.println(1 / 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(1 + 1);
    }
}

