package com.mutithread.atomic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 通过反射获取Unsafe对象
 */
public class UnsafeAccessor {
    private static Unsafe UNSAFE;

    static {
        Field unsafe = null;
        try {
            unsafe = Unsafe.class.getDeclaredField("theUnsafe");
            unsafe.setAccessible(true);
            try {
                UNSAFE = (Unsafe) unsafe.get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static Unsafe getInstance() {
        return UNSAFE;
    }
}
