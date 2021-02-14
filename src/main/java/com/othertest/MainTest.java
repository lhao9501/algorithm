package com.othertest;

import java.io.*;

public class MainTest {

    private static int count = 0;

    public static void main(String[] args) {
        File file = new File("D:/test/java");

        readFolder(file);
    }


    private static void readFolder(File srcFolder) {
        BufferedReader in = null;
        try {
            // ��ȡ��Ŀ¼�����е��ļ������ļ��е�File����
            File[] fileArray = srcFolder.listFiles();

            // ������File���飬�õ�ÿһ��File����
            for (File file : fileArray) {
                // �жϸ�File�����Ƿ����ļ���
                if (file.isDirectory()) {
                    readFolder(file);
                } else {
                    // �����ж��Ƿ���.java��β
                    if (file.getName().endsWith(".java")) {
                        // ��������ļ��ľ���·��
                        System.out.println(file.getAbsolutePath());

                        in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));

                        int len;
                        //String str = "";
                        //int count = 0;
                        while ((len = in.read()) != -1) {
                            char ch = (char) len;
                            if (ch == ' ' || ch == '\n' || ch == '\r' || ch == '\t') {
                                continue;
                            }
                            count++;
                        }

                        /*while((str = in.readLine()) != null) {
                            String strLine = str;
                            strLine.replace(" ", "");
                            strLine.replace("\t", "");
                            strLine.replace("\n", "");

                            count += strLine.length();
                        }*/
                        System.out.println("====================����Ϊ�� " + count + " =======================");

                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (in != null) {
                // Ϊ�˱�֤close()һ����ִ�У��ͷŵ�finally�������
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
