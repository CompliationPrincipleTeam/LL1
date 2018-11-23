package StackPrint;
import java.util.*;
import dataStructure.Grammar;
import dataStructure.Production;
import solve.SolveTable;

public class Print {


    public static void print_stack(Grammar F ,SolveTable T,Production[] G) {
        //G是过程的数组（产生式），T是构成的那个可以调用分析表的东西，F是语法,都是已经存在的构建好的
        Stack<String> st = new Stack<String>();

        st.push(new String("#"));//入栈#
        st.push(new String(G[0].leftPart));//入栈E
        boolean flag=true;
        int k=0;//string数组的位置
        int ki=0;
        Scanner scan = new Scanner(System.in);
        String str2=null;
        //String[] in_sting=new String[100];//string数组以#结尾
        if (scan.hasNextLine()) {
            str2 = scan.nextLine();
        }
        scan.close();
        String[] in_sting = str2.split("");//分割字符串得到数组

        String a=new String(in_sting[k]);
        while(flag){
            System.out.print(st);//打印栈的内容
            ki=k;
            while(in_sting[ki]!="#")
            {
                System.out.printf("%s",in_sting[ki]);//打印剩下的输入数组
                ki++;
            }
            System.out.printf("#");
            System.out.println();
            String X = (String) st.pop();//出栈到X
            if(F.vt.contains(X))//查找是否存在
            {
                if(X==a){
                    k++;
                    a=new String(in_sting[k]);//转换输入的数组位串，且找到下一个
                }
                else {
                    System.out.println("文法有误");
                    System.exit(0);
                }
            }
            else if(X=="#")
            {
                if(X==a)flag=false;
                else{
                    System.out.println("文法有误");
                    System.exit(0);
                }
            }
            else if(!"".equals(T.table[T.mapVn.get(X)][T.mapVt.get(a)]))//不知道这样对不对，但是一个是找X，一个是找a存的String对应的表格的内容
            {
                while(!"".equals(G[ki].leftPart))
                {
                    if((G[ki].leftPart+"->"+G[ki].rightPart)==T.table[T.mapVn.get(X)][T.mapVt.get(a)])
                    {
                        break;
                    }
                    ki++;
                }//找到在产生式数组中的位置
                String reverse = new StringBuffer(G[ki].rightPart).reverse().toString();//倒置
                String[] ar = reverse.split("");//分割字符串得到数组
                ki=0;
                while(!"".equals(ar[ki]))
                {
                    st.push(new String(ar[k]));//入栈
                    ki++;
                }
            }
        }
    }
}

