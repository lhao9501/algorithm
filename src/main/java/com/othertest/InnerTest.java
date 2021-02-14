package com.othertest;

public class InnerTest {
    public static void main(String[] args) {

        //成员内部类被静态修饰后的访问方式是:
        //格式：外部类名.内部类名 对象名 = new 外部类名.内部类名();
        OuterClass.Inner oi = new OuterClass.Inner();
        oi.show();
        oi.show2();

        //show2()的另一种调用方式（通过类名调用静态）
        OuterClass.Inner.show2();
    }
}
