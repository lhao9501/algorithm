package com.sorting;

public class MergeSort {

    public static void mergeSort(int[] arraySort, int low, int high, int[] temp) {
        if (low < high) {
            int mid = low + ((high - low) >> 1);   // 注意运算符优先级
            mergeSort(arraySort, low, mid, temp);
            mergeSort(arraySort, mid+1, high, temp);

            merge(arraySort, low, high, mid, temp);
        }
    }

    public static void merge(int[] arraySort, int low, int high, int mid, int[] temp) {
        int i = low, j = mid + 1, k = 0;

        while (i <= mid && j <= high) {
            if (arraySort[i] <= arraySort[j]) {
                temp[k++] = arraySort[i++];
            }else {
                temp[k++] = arraySort[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arraySort[i++];
        }

        while (j <= high) {
            temp[k++] = arraySort[j++];
        }

        for (int m = 0; m < k; m++) {
            arraySort[low++] = temp[m];
        }

    }
}
