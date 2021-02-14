package com.othertest;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] arr = {4, 8, 6, 5, 7, 3, 9, 2};
        int[] temp = new int[arr.length];

        // sort(arr, temp, 0, arr.length-1);
        quicksort(arr, 0, arr.length-1);

        System.out.println(Arrays.toString(arr));

    }

    public static void sort(int[] arr, int[] temp, int startIndex, int endIndex) {
        if(endIndex <= startIndex) {
            return;
        }

        int mid = startIndex + (endIndex - startIndex) / 2;

        sort(arr, temp, startIndex, mid);
        sort(arr, temp, mid+1, endIndex);

        merge(arr, temp, startIndex, endIndex, mid);
    }

    public static void merge(int[] arr, int[] temp, int startIndex, int endIndex, int mid) {
        int i = startIndex;
        int j = mid + 1;
        int k = 0;


        while(i <= mid && j <= endIndex) {
            if(arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            }else {
                temp[k++] = arr[j++];
            }
        }


        while(i <= mid) {
            temp[k++] = arr[i++];
        }
        while(j <= endIndex) {
            temp[k++] = arr[j++];
        }

        for(int m = 0; m < k; m++) {
            arr[startIndex++] = temp[m];
        }
    }

    public static void quicksort(int[] arr, int low, int high) {
        if(low < high) {
            int pivot = partition(arr, low, high);

            quicksort(arr, low, pivot-1);
            quicksort(arr, pivot+1, high);
        }
    }


    public static int partition(int[] arr, int low, int high) {

        int left = low;
        int temp = arr[left];

         while(low < high) {
              while(low < high && arr[high] >= temp) {
                  high--;
              }
              arr[low] = arr[high];

              while(low < high && arr[low] <= temp) {
                  low++;
              }
              arr[high] = arr[low];

             /*while(low < high) {
                 if(arr[high] >= temp) {
                     high--;
                 }else {
                     arr[low] = arr[high];
                     // low++;
                     break;
                 }
             }
             while(low < high) {
                 if(arr[low] <= temp) {
                     low++;
                 }else {
                     arr[high] = arr[low];
                     // high--;
                     break;
                 }
             }*/
         }

        /*while(low != high) {
            while(low < high && arr[high] > temp) {
                high--;
            }
            while(low < high && arr[low] <= temp) {
                low++;
            }

            if(low < high) {
                int p = arr[low];
                arr[low] = arr[high];
                arr[high] = p;
            }
        }*/

         arr[low] = temp;
        /*int p1 = arr[low];
        arr[low] = temp;
        arr[left] = p1;*/

        return low;
    }

}
