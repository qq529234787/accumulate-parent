package com.wme.learn.array;

/**
 * @Title: 多个二进制数进行异或支持交换律
 * @Auther: ming
 * @Date: 2018/2/5
 * @Version: 1.0
 */
public class FindOnlyOne {

    public static void main(String[] args){
        int[] arr = {1,2,3,4,10,20,20,19,31,19,10,4,3,2,1};
        int theOne = findTheOne(arr);
        System.out.println("--------------------");
        System.out.println(theOne);

        String swap = swap(2, 7);
        System.out.println(swap);
    }

    /**
     * 给一组数,只有一个数只出现了一次,其余所有数都是成对出现的,找出这个数
     * @param arr
     * @return
     */
    public static int findTheOne(int[] arr) {
        int theOne = 0;
        for(int i=0; i<arr.length; i++) {
            theOne ^= arr[i];
            System.out.println(theOne);
        }

        return theOne;
    }

    /**
     * 交换两个数字
     * @param a b
     * @return
     */
    public static String swap(int a, int b) {
        int temp = a^b;
        a = temp^a;
        b = temp^b;

        return a+":"+b;
    }

}
