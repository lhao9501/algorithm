package com.othertest;

public class RunExpressionTest {
    public static void main(String args[])
    {
        try
        {
            /*BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println
                    ("Please enter an expression with +,*,() and 1-digit integer:");
            String expr = br.readLine();
            com.othertest.Expression exprcal = new com.othertest.Expression();*/
            // int ans = exprcal.calculateExpr(expr);
            // System.out.println("The answer is: "+ans);

            System.out.println(Thread.currentThread().getContextClassLoader().getClass().getName());
            System.out.println(RunExpressionTest.class.getClassLoader().getClass().getName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
