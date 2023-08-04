package usts.pycro.juc;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-16 3:43 PM
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(234 * 567);
        String str = "fibonacci";

    }

    public static int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return mid;
            if (nums[mid] > target) right = mid - 1;
            if (nums[mid] < target) left = mid + 1;
        }
        return left;
    }

    public static boolean isPalindrome(int x) {
        if (x < 0) return false;
        int[] numArr = new int[10];
        int idx = 0;
        while (x > 0) {
            numArr[idx++] = x % 10;
            x /= 10;
        }
        for (int i = 0; i < idx; ++i) {
            if (numArr[i] != numArr[idx - 1 - i]) return false;
        }
        return true;

    }


}

