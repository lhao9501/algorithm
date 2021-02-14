package com.sorting;

public class CountSort {

    /**
     * 不是基于比较和交换   而是基于出现的次数
     * 空间换时间  稳定排序  空间/时间 O(n+k)  k为整数的范围
     * 只能是整数   数字之间比较集中
     * @param arraySort
     */
    public static void countSort(int[] arraySort) {
        // 找出min max   避免浪费空间
        int min, max;
        min = max = arraySort[0];
        for (int i = 0; i < arraySort.length; i++) {
            if (arraySort[i] < min) {
                min = arraySort[i];
            }
            if (arraySort[i] > max) {
                max = arraySort[i];
            }
        }

        // 创建 记录次数的数组     arraySort[i] - min 就是在countArr的索引
        int[] countArr = new int[max - min + 1];
        for (int i = 0; i < arraySort.length; i++) {
            countArr[arraySort[i] - min]++;
        }

        // 将当前元素和前面元素相加
        // 通过自己出现的次数和前面元素出现的次数 可以体现出该元素在数组的位置
        for (int i = 1; i < countArr.length; i++) {
            countArr[i] += countArr[i-1];
        }

        // 创建 新数组
        int[] newArr = new int[arraySort.length];
        // 倒序遍历 arraySort
        for (int i = arraySort.length - 1; i >= 0; i--) {
            countArr[arraySort[i] - min]--;
            newArr[countArr[arraySort[i] - min]] = arraySort[i];
        }

        // 搬移到原数组中
        for (int i = 0; i < newArr.length; i++) {
            arraySort[i] = newArr[i];
        }

    }
}
