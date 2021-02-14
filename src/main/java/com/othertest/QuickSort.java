package com.othertest;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {

        int[] arr = {11, 3, 5, 7, 9, 4, 6};

        System.out.println(Arrays.toString(arr));

        QuickSort.quick_sort(arr);

        System.out.println(Arrays.toString(arr));

    }

    public static void quick_sort(int[] arr) {
        QuickSort.quickSort(arr, 0, arr.length-1);
    }

    public static void quickSort(int[] arr, int fromIndex, int toIndex) {
        if(fromIndex >= toIndex) return;
        int pivot = QuickSort.partition(arr, fromIndex, toIndex);

        QuickSort.quickSort(arr, fromIndex, pivot);
        QuickSort.quickSort(arr, pivot+1, toIndex);

    }

    public static int partition(int[] arr, int fromIndex, int toIndex) {
        int pivot = arr[toIndex];
        int i = fromIndex;

        for(int j = 0; j < toIndex; ++j) {
            if(arr[j] < pivot) {
                int temp;
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;

                i++;
            }
        }

        int temp;
        temp = arr[i];
        arr[i] = arr[toIndex];
        arr[toIndex] = temp;

        return i;
    }
}
