package com.sorting;

public class BubbleSort {


    public static void bubbleSort(int[] arraySort) {
        if (null == arraySort || arraySort.length <= 1) return;

        for (int j = 0; j < arraySort.length - 1; j++) {
            boolean flag = false;

            for (int i = 0; i < arraySort.length - 1 - j; i++) {
                if (arraySort[i] > arraySort[i + 1]) {
                    int temp = arraySort[i];
                    arraySort[i] = arraySort[i + 1];
                    arraySort[i + 1] = temp;

                    flag = true;
                }

            }
            if (!flag) {
                break;
            }
        }
    }

    /**
     * 有可能序列后面的几个数字有顺序
     * @param arraySort
     */
    public static void bubbleSort3(int[] arraySort) {
        if (null == arraySort || arraySort.length <= 1) return;

        for (int end = arraySort.length - 1; end > 0; end--) {
            int sortedIndex = 1;
            for (int begin = 0; begin < end; begin++) {
                if (arraySort[begin] > arraySort[begin + 1]) {
                    int temp = arraySort[begin];
                    arraySort[begin] = arraySort[begin + 1];
                    arraySort[begin + 1] = temp;

                    sortedIndex = begin + 1;  // end--  这里要begin+1
                }
            }

            end = sortedIndex;
        }
    }
}
