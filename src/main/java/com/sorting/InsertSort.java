package com.sorting;

/**
 * 插入排序
 *          分为已排序区间和未排序区间
 *          默认第一个元素为已排序区间 剩下的为未排序区间
 *          从未排序中第一个元素和右侧的已排序元素开始依次进行比较
 *          找到小的元素   插到已排序区间的合适位置上
 *
 *          最坏 O(n^2)   最好 O(n)    平均 O(n^2)
 */
public class InsertSort {

    public static void insertSort(int[] arraySort) {

        if (null == arraySort || arraySort.length <= 1) return;

        for (int i = 1; i < arraySort.length; i++) {
            // 先存放 默认已排序中的第一个元素   防止被后续的交换位置覆盖
            int value = arraySort[i];

            // 和已排序区间的右侧开始依次进行比较
            int j = i - 1;
            for (; j >= 0; j--) {
                if (arraySort[j] > value) {
                    // 如果已排序区间中有比较大的  向后移动一位
                    arraySort[j + 1] = arraySort[j];
                }else {
                    // 如果没有  直接跳出比较
                    break;
                }
            }

            // 注意 此时的 j 已经 减1了
            // 把值 插到已排序区间合适的位置上
            arraySort[j+1] = value;
        }
    }


}
