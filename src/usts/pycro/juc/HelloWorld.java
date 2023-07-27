package usts.pycro.juc;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-23 10:24 AM
 */
public class HelloWorld {
    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 4, 5};
        int[] arr2 = Arrays.copyOf(arr1, 10);
        int[] arr3 = arr1;
        MyClass c1 = new MyClass();
        Objects.toString(1);
        MyClass c2 = c1.clone();
        System.out.println(c1 == c2);
    }
}

class MyClass {

    @Override
    public MyClass clone() {
        try {
            return (MyClass) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}