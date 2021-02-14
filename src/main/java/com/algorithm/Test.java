package com.algorithm;

import com.sorting.*;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) throws Exception {
//        test16();
//        String str = new String("铜鼓");
        String str = "测铜鼓"; // 1222071
        String str2 = "测铁路";  // 1216846

        System.out.println(str2.hashCode());  // 1222071
        System.out.println(str.hashCode());  // 1222071


        String string = "1-2--4-";
        System.out.println(Arrays.toString(string.split("-")));
        System.out.println(string.split("-").length);

    }



    public static void test16() {
        Integer[] arr = {1, 8, 4, 5, 3, 9, 6};

        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>(arr/*, (e1, e2) -> e2 - e1*/);

        binaryHeap.add(20);

        binaryHeap.remove();

        System.out.println(binaryHeap.toString());
    }


    public static void test15() {
        AVLTree<Integer> avlTree = new AVLTree<>();
        avlTree.add(22);
        avlTree.add(12);
        avlTree.add(2);
        avlTree.add(56);
        avlTree.add(88);

        avlTree.remove(2);

        avlTree.inorderTraversal();
    }

    public static void test14() {
        int[] arr = {5, 8, 4, 5, 1, 3, 9, 6, 10, 11, 12};
        RadixSort.radixSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void test13() {
        int[] arr = {5, 8, 4, 5, 1, 3, 9, 6, 10, 11, 12};
        CountSort.countSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void test12() {
        int[] arr = {5, 8, 4, 5, 1, 3, 9, 6};
        int[] temp = new int[arr.length];
        MergeSort.mergeSort(arr, 0, arr.length-1, temp);
        System.out.println(Arrays.toString(arr));
    }

    public static void test11() {
        int[] arr = {5, 8, 4, 5, 1, 3, 9, 6};
        QuickSort.quickSort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    public static void test10() {
        int[] arr = {1, 8, 4, 5, 5, 3, 9, 6};
        InsertSort.insertSort(arr);

        System.out.println(Arrays.toString(arr));
    }


    public static void test9() {
        int[] arr = {1, 8, 4, 5, 5, 3, 9, 6};
        SelectedSort.selectedSort(arr);

        System.out.println(Arrays.toString(arr));
    }

    public static void test8() {
        int[] arr = {1, 8, 4, 5, 5, 3, 9, 6, 10, 11, 12};
        // 1 4 5 5 3 8 6 9 10 11 12
//        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8};
        BubbleSort.bubbleSort3(arr);

        System.out.println(Arrays.toString(arr));
    }


    public static void test7() {
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        /*binarySearchTree.add(22);
        binarySearchTree.add(12);
        binarySearchTree.add(2);
        binarySearchTree.add(56);
        binarySearchTree.add(8);
        binarySearchTree.add(1);
        binarySearchTree.add(28);
        binarySearchTree.add(38);
        binarySearchTree.add(40);*/
        binarySearchTree.add(4);
        binarySearchTree.add(2);
        binarySearchTree.add(1);
        binarySearchTree.add(3);
        binarySearchTree.add(6);
        binarySearchTree.add(5);
        binarySearchTree.add(7);


//        binarySearchTree.levelTraversal();

//        binarySearchTree.inorderTraversal();
        System.out.println(binarySearchTree.heigth2());
        System.out.println(binarySearchTree.isCompleted2());
    }

    public static void test6() {
        CircleQueue<Integer> circleQueue = new CircleQueue<>();
        for (int i = 0; i < 10; i++) {
            circleQueue.enQueue(i);
        }

        circleQueue.deQueue();
        circleQueue.deQueue();
        circleQueue.deQueue();
        circleQueue.deQueue();

        for (int i = 0; i < 10; i++) {
            circleQueue.enQueue(i * 10);
        }

        System.out.println(circleQueue);
    }


    public static void test5() {
        Queue<Integer> queue = new Queue<>();
        for (int i = 0; i < 10; i++) {
            queue.enQueue(i);
        }

        queue.enQueue(22);
        queue.deQueue();
        queue.deQueue();

        System.out.println(queue);
    }

    public static void test4() {
        SingleLinkedList<Integer> linkedList = new SingleLinkedList<>();
        for (int i = 0; i < 10; i++) {
            linkedList.add_circle(i);
        }

        System.out.println(linkedList);
    }


    public static void test3() {
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            linkedList.add(i);
        }

        linkedList.remove(0);
        linkedList.remove(3);

        linkedList.add(5, 12);
        linkedList.add(0, 33);

        System.out.println(linkedList.indexOf(12));

        System.out.println(linkedList);
    }


    public static void test2() {
        SingleLinkedList<Integer> linkedList = new SingleLinkedList<>();
        for (int i = 0; i < 10; i++) {
            linkedList.add(i);
        }

        linkedList.remove(0);
        linkedList.remove(3);

        linkedList.add(5, 12);
        linkedList.add(0, 33);

        System.out.println(linkedList);
    }

    public static void test() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        list.remove(3);
        list.remove(3);
        list.remove(3);
        list.remove(3);

        System.out.println(list);

        list.add(2, null);
        System.out.println(list);
        System.out.println(list.indexOf(null));
    }
}
