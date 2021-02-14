package com.sorting;

public class QuickSort {

    public static void quickSort(int[] arraySort, int low, int hight) {
        if (null == arraySort || hight - low <= 1) return;

        int pivot = partition(arraySort, low, hight);
        quickSort(arraySort, low, pivot);
        quickSort(arraySort, pivot+1, hight);
    }


    /**
     * 获取分区点index   左右指针交替填空  3while + 2break
     * @param arraySort
     * @param low
     * @param hight
     * @return
     */
    private static int partition(int[] arraySort, int low, int hight) {
        // 这个地方可以单独抽取出去  利用三数取中法
        int pivot = arraySort[low];

        while (low < hight) {
            while (low < hight) {
                // 先从右侧和pivot比较
                if (arraySort[hight] > pivot) {
                    hight--;
                } else {     // 相等的元素也要放到前边  体现了不稳定排序 也保证了分区点两边的元素数量平衡
                    arraySort[low] = arraySort[hight];
                    low++;
                    break;
                }
            }


            while (low < hight) {
                // 再从左侧和pivot比较
                if (arraySort[low] < pivot) {
                    low++;
                } else {
                    arraySort[hight] = arraySort[low];
                    hight--;
                    break;
                }
            }
        }

        arraySort[low] = pivot;

        return low;
    }

}
