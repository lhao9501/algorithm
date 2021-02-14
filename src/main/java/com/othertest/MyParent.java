package com.othertest;

public class MyParent {
    public static final String name = "hello world";

    static {
        // name = "hello world";
        System.out.println("my parent");
    }
}


class MyChildren extends MyParent {
    static {
        System.out.println("my children");
    }
}

class MyTest {
    static {
        System.out.println("main function.....");
    }
    public static void main(String[] args) {
        // 直接定义静态变量的类初始化
        System.out.println(MyChildren.name);
    }
}
