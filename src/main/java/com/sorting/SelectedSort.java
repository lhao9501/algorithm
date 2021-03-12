package com.sorting;

public class SelectedSort {

    /**
     * 选择排序
     *      分为已排序区间和未排序区间
     *      默认所有元素在未排序区间 在未排序区间中选中第一个为最小元素
     *      与后续元素进行比较  找出最小元素  再进行交换  放到已排序中
     *
     *      最坏 O(n^2)   最好 O(n^2)   平均 O(n^2)
     *
     * @param arraySort
     */
    public static void selectedSort(int[] arraySort) {
        if (null == arraySort || arraySort.length <= 1) return;


        int min = 0;
        for (int i = 0; i < arraySort.length; i++) {
            // 每次选择第一个元素为最小元素
            min = i;

            // 在后续元素中找到比第一个元素小的元素   更新下标
            for (int j = i + 1; j < arraySort.length; j++) {
                if (arraySort[j] < arraySort[min]) {
                    min = j;
                }
            }

            // 元素交换
            if (min != i) {
                int temp = arraySort[i];
                arraySort[i] = arraySort[min];
                arraySort[min] = temp;
            }
        }
    }
}
