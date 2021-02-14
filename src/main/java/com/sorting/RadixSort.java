package com.sorting;

public class RadixSort {

    /**
     * 基数排序   先排个位、再排十位、百位、千位...  每个位上的排序又是一个计数排序
     * @param arraySort
     */
    public static void radixSort(int[] arraySort) {
        if (null == arraySort || arraySort.length <= 1) return;

        // 找到最大值
        int max = arraySort[0];
        for (int i = 0; i < arraySort.length; i++) {
            if (max < arraySort[i]) {
                max = arraySort[i];
            }
        }

        int[] countArr = new int[10];
        int[] newArr = new int[arraySort.length];

        for (int divider = 1; divider <= max; divider *= 10) {

            // 每一轮 要清空countArr数组的值
            for (int i = 0; i < countArr.length; i++) {
                countArr[i] = 0;
            }

            for (int i = 0; i < arraySort.length; i++) {
                countArr[arraySort[i] / divider % 10]++;
            }

            // 将当前元素和前面元素相加
            // 通过自己出现的次数和前面元素出现的次数 可以体现出该元素在数组的位置
            for (int i = 1; i < countArr.length; i++) {
                countArr[i] += countArr[i-1];
            }

            // 创建 新数组
//        int[] newArr = new int[arraySort.length];
            // 倒序遍历 arraySort
            for (int i = arraySort.length - 1; i >= 0; i--) {
//                countArr[arraySort[i] / divider % 10]--;
                newArr[--countArr[arraySort[i] / divider % 10]] = arraySort[i];
            }

            // 搬移到原数组中
            for (int i = 0; i < newArr.length; i++) {
                arraySort[i] = newArr[i];
            }
        }

    }

}
