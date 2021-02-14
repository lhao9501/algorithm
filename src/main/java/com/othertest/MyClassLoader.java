package com.othertest;

public class MyClassLoader extends ClassLoader {

    private String name;

    public MyClassLoader(String name) {
        this.name = name;
    }

    /*@Override
    protected Class<?> findClass(String name) {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        byte[] bytes = null;

        try {
            in = new FileInputStream(new File("D:\\projects_2019\\sjcj\\test\\out\\production\\test", name));
            int c = 0;
            while((c = in.read()) != -1) {
                out.write(c);
            }
            bytes = out.toByteArray();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return this.defineClass(name, bytes, 0, bytes.length);

    }*/


    public static void main(String[] args) throws Exception {
        MyClassLoader myClassLoader = new MyClassLoader("testloader");  // 走super 设置getSystemClassLoader()
        MyClassLoader myClassLoader1 = new MyClassLoader("testloader");
        Class c = myClassLoader.loadClass("com.othertest.QuickSort");
        Class c1 = myClassLoader1.loadClass("com.othertest.QuickSort");
        QuickSort q = new QuickSort();
        System.out.println(c == q.getClass());
        System.out.println(c1 == q.getClass());
        System.out.println(c.getClassLoader().getClass().getName());
        System.out.println(c1.getClassLoader().getClass().getName());

//        Class clazz = myClassLoader.loadClass("com.othertest.QuickSort");
//        System.out.println(clazz.getClassLoader());
//        System.out.println(myClassLoader.getClass().getClassLoader().getParent());
//        System.out.println(com.othertest.MyClassLoader.class.getClassLoader());
        /*Object obj = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("main", String[].class);
        method.invoke(obj, (Object) new String[] {"1", "2"});*/
    }
}
