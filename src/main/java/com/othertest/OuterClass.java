package com.othertest;

public class OuterClass {
    private int num = 10;
    private static int num2 = 100;


    public static class Inner {

        public static String inner_name = "zhangsan";

        public void show() {
            // System.out.println(num);//报错，只能访问静态
            System.out.println(num2);
        }

        // 静态内部类中可以包含静态的属性和方法
        public static void show2() {
            // System.out.println(num);//报错
            System.out.println(num2);
        }
    }

    // 一般在外围类中给出方法-----直接在外围类中调用静态内部类的数据
    public void display() {
        System.out.println(Inner.inner_name);
        new Inner().show();
        Inner.show2();
    }

    public static void main(String[] args) {
        Inner i = new Inner();
        Inner oi = new Inner();
        i.show();
        oi.show2();
        Inner.show2();
        Inner.show2();
        System.out.println(i == oi);   // false
    }
}