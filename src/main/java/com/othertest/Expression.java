package com.othertest;

import java.util.Stack;

public class Expression {

    // �����ʽת�����沨��ʽ
    public static String infixToPostfix(String infix) {
        // ������ջ-----'+ - * / ( )'
        Stack<Character> op = new Stack<>();
        StringBuilder postfixStr = new StringBuilder("");
        char[] prefixs = infix.trim().toCharArray();

        Character ch;

        for (int i = 0; i < prefixs.length; i++) {
            ch = prefixs[i];

            // ��������� 0~9
            if (ch >= '0' && ch <= '9') {
                // vlaues.push(Integer.valueOf(ch));
                postfixStr.append(ch);
                continue;
            }
            // "("---ֱ��ѹջ
            if ('(' == ch) {
                op.push(ch);
                continue;
            }
            /*
             * '+ - * /'----��ջֱ��ѹջ������ջ��Ԫ�رȽϣ� ���ȼ�����ջ��Ԫ���򵯳�ջֱ���������ȼ���Ȼ��ߵ͵ľ�ֹͣ��ջ
             * ��󽫸ò�����ѹջ
             */

            if ('+' == ch || '-' == ch) {
                // ֻҪջ��Ϊ�գ�ջ��Ԫ�ز���'(' �͵�ջ
                while (!op.empty() && (op.peek() != '(')) {
                    postfixStr.append(op.pop());
                }
                op.push(ch);
                continue;
            }
            if ('*' == ch || '/' == ch) {
                // ֻҪջ��Ϊ�գ�ջ��Ԫ����'* /' �͵�ջ
                while (!op.empty() && (op.peek() == '*' || op.peek() == '/')) {
                    postfixStr.append(op.pop());
                }
                op.push(ch);
                continue;
            }
            // ')'----��ʼ��ջֱ��������һ��'('
            if (')' == ch) {
                while (!op.empty() && op.peek() != '(') {
                    postfixStr.append(op.pop());
                }
                op.pop();// ----��'('Ԫ�ص���
                continue;
            }
        }
        // �����ַ�������ϲ�����ջ�������ݾ�ȫ����ջ
        while (!op.empty())
            postfixStr.append(op.pop());
        return postfixStr.toString();

    }

}
