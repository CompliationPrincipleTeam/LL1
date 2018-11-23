package solve;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.Test;

import dataStructure.First;
import dataStructure.Grammar;
import dataStructure.Production;
import javafx.util.Pair;

public class SolveFollowSet {
    public Grammar G = null;
    public First[] first = null;
    public HashMap<String,String> solve(Grammar g, First[] f)
    {
        this.G = g;
        this.first = f;
        HashMap<String,String> follow = new HashMap();
        for(int i=0;i<first.length;i++) {
            follow.put(first[i].vn, "");
        }
        follow.put(G.S, follow.get(G.S)+"#");
        ArrayList<Production> p = G.production2;

        boolean isAdd = false;
        int times=20;
        do
        {
            times--;
            isAdd = false;
            for(int i=0;i<p.size();i++)
            {
                String alpha = "";
                String B = "";
                String beta = "";
                String A = p.get(i).leftPart;
                String s = p.get(i).rightPart;
                //System.out.println(p.get(i).leftPart+"->"+s);
                int index = this.findNonterminal(s);	//alpha B beta中B的下标

                if(index == -1)
                {
                    continue;
                }

                int hasQuote = 0;
                if(index < s.length()-1)
                    hasQuote = s.charAt(index+1)=='\''? 1 : 0;
                if(index >= 1)
                    alpha = s.substring(0, index);
                if(index < s.length())
                    B = s.substring(index,index+1+hasQuote);
                if(index <= s.length()-2)
                    beta = s.substring(index+1+hasQuote);


                //规则2
                //System.out.println("B="+B);
                String followB = follow.get(B);
                //System.out.println(followB);
                if(!beta.equals(""))
                    followB += this.getFirstOfBeta(beta);
                if(!isAdd && !followB.equals(this.removeRepeatChar(followB)))
                    isAdd = true;
                followB = this.removeRepeatChar(followB);
                follow.put(B,followB);
                //System.out.println("follow"+B+":"+followB);

                String followA="";
                //规则3.2
                if(isBeta2$(beta))
                {
                    followA = follow.get(A);
                    followB = follow.get(B) + followA;
                    if(!isAdd && !followB.equals(this.removeRepeatChar(followB)))
                        isAdd = true;
                    followB = this.removeRepeatChar(followB);
                    follow.put(B,followB);
                    //System.out.println("follow"+B+":"+followB);
                }

                //规则3.1
                index = this.RefindNonterminal(s);
                if(index == -1)
                    continue;
                if(index < s.length()-1)
                    hasQuote = s.charAt(index+1)=='\''? 1 : 0;

                B = s.substring(index,index+1+hasQuote);
                //System.out.println("B="+B);
                followA = follow.get(A);
                followB = follow.get(B)+followA;
                if(!isAdd && !followB.equals(this.removeRepeatChar(followB)))
                    isAdd = true;
                followB = this.removeRepeatChar(followB);
                follow.put(B,followB);
                //System.out.println("follow"+B+":"+followB);
            }

        }
        while(times!=0);


        for(String key:follow.keySet()) {
            System.out.println("follow("+key +"):"+ follow.get(key));
        }
        return follow;


    }

    private boolean isBeta2$(String beta)
    {
        for(Production p: G.production2)
        {
            if(p.leftPart.equals(beta) && p.rightPart.equals("$"))
                return true;
        }
        return false;
    }
    private int findNonterminal(String rightPart)
    {
        int index = -1;
        for(int i=0;i<rightPart.length();i++) {
            if('A'<=rightPart.charAt(i) && rightPart.charAt(i)<='Z')
            {
                index = i;
                break;
            }
        }
        return index;
    }

    private int RefindNonterminal(String rightPart)
    {
        int len = rightPart.length();
        if('A'<=rightPart.charAt(len-1) && rightPart.charAt(len-1)<='Z')
            return len-1;
        if(rightPart.charAt(len-1)=='\'' && 'A'<=rightPart.charAt(len-2) && rightPart.charAt(len-2)<='Z')
            return len-2;
        return -1;
    }

    private String getFirstOfBeta(String beta)
    {
        String res = "";
        char ch = beta.charAt(0);
        if(G.vt.contains(String.valueOf(ch)))
        {
            return String.valueOf(ch);
        }

        int hasQuote = beta.charAt(1)=='\''?1:0;
        String firstNon = beta.substring(0,1+hasQuote);

        for(int i=0;i<first.length;i++)
        {
//			System.out.println(first[i].vn);
            if(first[i].vn.equals(firstNon))
            {
                for(Pair p: first[i].firstSet)
                    res+=p.getKey();
                break;
            }
        }

        for(Production p: G.production2)
        {
            //firstNon -> $时，往后扫描
            if((1+hasQuote)<beta.length() && p.leftPart.equals(firstNon) && p.rightPart.equals("$"))
            {
                char nextChar = beta.charAt(1+hasQuote);
                //如果是一个终结符
                if(G.vt.contains(String.valueOf(nextChar)) && res.indexOf(String.valueOf(nextChar))==-1)
                {
                    res+=String.valueOf(nextChar);
                }
                else
                {
                    String non = beta.substring(1+hasQuote, 2+hasQuote);
                    //如果是非终结符
                    if((2+hasQuote)<beta.length() && beta.charAt(2+hasQuote) == '\'')
                    {
                        non = beta.substring(1+hasQuote, 3+hasQuote);
                        for(int i=0;i<first.length;i++)
                        {
                            if(first[i].vn.equals(non))
                            {
                                for(Pair p1:first[i].firstSet)
                                {
                                    res+=p1.getKey();
                                }
                                break;
                            }
                        }
                    }
                }


            }

        }

        String[] helper = res.split("\\$");
        res="";
        for(int i=0;i<helper.length;i++)
            res+=helper[i];
        return res;
    }

    //构造First集合用于测试
    public First[] init() {
        this.G = new Grammar();     //需要薛总的grammer
        GetFirst getFirst = new GetFirst();

        //构造文法
//        this.G.S="E";
//        G.vn.add("E");
//        G.vn.add("E'");
//        G.vn.add("T");
//        G.vn.add("T'");
//        G.vn.add("F");
//
//        G.vt.add("+");
//        G.vt.add("*");
//        G.vt.add("(");
//        G.vt.add(")");
//        G.vt.add("i");
//        G.addProduction("E", "TE'");
//        G.addProduction("E'", "+TE'|$");
//        G.addProduction("T", "FT'");
//        G.addProduction("T'", "*FT'|$");
//        G.addProduction("F", "(E)|i");

        First[] first = getFirst.getFirst(this.G); //first[]数组 = 李珍岩的first数组
        Iterator<String> itor = G.vn.iterator();
        for(int i=0;i<first.length;i++) {
            //first[i] = new First(itor.next());
        }

        return first;
    }

    public String removeRepeatChar(String s) {
        if (s == null) {
            return "";
        }
        s = this.sort(s);
        StringBuffer sb = new StringBuffer();
        int i = 0;
        int len = s.length();
        while (i < len) {
            char c = s.charAt(i);
            sb.append(c);
            i++;
            while (i < len && s.charAt(i) == c) {//这个是如果这两个值相等，就让i+1取下一个元素
                i++;
            }
        }
        return sb.toString();
    }

    private String sort(String s)
    {
        char[] array = s.toCharArray();
        Arrays.sort(array);
        String res = "";
        for(int i=0;i<array.length;i++)
        {
            res+=String.valueOf(array[i]);
        }
        return res;
    }


//    G.addProduction("E", "TE'");
//    G.addProduction("E'", "+TE'|$");
//    G.addProduction("T", "FT'");
//    G.addProduction("T'", "*FT'|$");
//    G.addProduction("F", "(E)|i");

}
