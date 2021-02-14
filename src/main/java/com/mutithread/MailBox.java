package com.mutithread;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class MailBox {
    // 维护一个集合
    private static Map<Integer, GuardedObject> map = new Hashtable<>();

    private static int id = 1;

    private static synchronized int generateId() {
        return id++;
    }

    public static GuardedObject createGuardedObject() {
        GuardedObject go = new GuardedObject(generateId());
        map.put(go.getId(), go);
        return go;
    }

    public static GuardedObject getGuardedObject(int id) {
        return map.remove(id);
    }

    public static Set<Integer> getIds() {
        return map.keySet();
    }
}
