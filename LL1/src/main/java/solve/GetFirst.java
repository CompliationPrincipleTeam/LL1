package solve;

import dataStructure.First;
import dataStructure.Grammar;
import dataStructure.Production;
import javafx.util.Pair;
import org.junit.Test;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author FFXN
 * @date 2018-11-21
 * @node 获取每一个非终结符的FIRST集
 */
public class GetFirst {

    //判断一个string是否在HashSet<String>中
    public boolean HashContain(String s,HashSet<String> set){
        boolean flag=false;
        for (String string :set){
            if(s.equals(string)){
                flag=true;
                break;
            }
        }
        return flag;
    }

    //获取string中的第i个文法符号（主要为了识别A'）
    public String getSign(String productionRight,int i){
        if (i<productionRight.length()-1){
            if(productionRight.charAt(i+1)=='\''){
                return (productionRight.charAt(i)+"'");
            }
        }
        return (productionRight.charAt(i)+"");
    }

    //返回first集中A所在的那个first的位置
    public int getfirst_A(String A,ArrayList<First> firsts){
        for (int i=0;i<firsts.size();i++){
            if (firsts.get(i).vn.equals(A)){
                return i;
            }
        }
        return 0;
    }

    //判断终结符a是否已经在first中
    public boolean ifexist(String a,First first){
        for(Pair<String,Production> pair:first.firstSet){
            if (a.equals(pair.getKey().toString())){
                return true;
            }
        }
        return false;
    }

    //按照P78算法构造每一个非终结符的first集
    public First[] getFirst(Grammar grammar){

        String s1;
        //获取grammer的vt和vn集合
        for (Production production:grammar.production2){
            grammar.vn.add(production.leftPart);

            for (int j=0;j<production.rightPart.length();j++){
                s1=getSign(production.rightPart,j);
                if (s1.length()>1){
                    j++;
                    grammar.vn.add(s1);
                }
                else{
                    if (s1.charAt(0)>='A'&&s1.charAt(0)<='Z'){
                        grammar.vn.add(s1);
                    }
                    else {
                        grammar.vt.add(s1);
                    }
                }
            }
        }

        ArrayList<First> Firsts = new ArrayList<First>();
        First first;
        for (String vn:grammar.vn) {
            first=new First(vn);
            Firsts.add(first);
        }
        Pair<String,Production> pair;
        String ch;
        boolean finished = false;
        boolean end=false;
        First first1;
        String vn;
        //循环中若本次循环没有first发生变化，则finished=true，循环结束
        while (!finished){
            finished=true;
            for (Production p:grammar.production2){

                //判断产生式的第一个字符是否为非终结符

                if (HashContain(p.rightPart.charAt(0)+"",grammar.vt)){

                    //判断这个终结符是否已经在first中
                    s1=p.rightPart.charAt(0)+"";
                    if(!ifexist(s1,Firsts.get(getfirst_A(p.leftPart,Firsts)))){
                        pair=new Pair<String, Production>(s1,p);
                        Firsts.get(getfirst_A(p.leftPart,Firsts)).firstSet.add(pair);
                        finished=false;
                    }

                }
                else {
                    end=false;
                    for (int i=0;i<p.rightPart.length();i++){
                        //ch中放的是B
                        ch=getSign(p.rightPart,i);
                        if (ch.length()>1){
                            i++;
                        }
    //                    ch=p.rightPart.charAt(i);
                        //依次判断到达式是否为非终结符
                        if (HashContain(ch,grammar.vn)){
                            //B的first集
                            first1=Firsts.get(getfirst_A(ch,Firsts));
                            //依次判断将B的first元素加到A中
                            for (Pair<String, Production> pair1:first1.firstSet){
                                vn=pair1.getKey();
                                //判断A->BC的B的first元素是否已经在A中
                                if(!ifexist(vn,Firsts.get(getfirst_A(p.leftPart,Firsts)))){
                                    //把非空first元素加进去
                                    if (!vn.equals("$")){
                                        pair=new Pair<String, Production>(vn,p);
                                        Firsts.get(getfirst_A(p.leftPart,Firsts)).firstSet.add(pair);
                                        finished=false;
                                    }
                                }
                            }
                            //判断B的first中是否有$
                            if (ifexist("$",first1)){
                                continue;
                            }
                            else {
                                end=true;
                                break;
                            }
                        }
                        //B是终结符
                        else {
                            if (!ifexist(ch,Firsts.get(getfirst_A(p.leftPart,Firsts)))){
                                pair=new Pair<String, Production>(ch,p);
                                Firsts.get(getfirst_A(p.leftPart,Firsts)).firstSet.add(pair);
                                finished=false;
                            }
                            end=true;
                            break;
                        }
                    }
                    //如果BC都有$那么把$加到A中
                    if (!end){
                        if (!ifexist("$",Firsts.get(getfirst_A(p.leftPart,Firsts)))) {
                            pair = new Pair<String, Production>("$", p);
                            Firsts.get(getfirst_A(p.leftPart, Firsts)).firstSet.add(pair);
                            finished=false;
                        }
                    }
                }
            }
        }


        int length=Firsts.size();
        First[] firsts=new First[length];
        for (int i=0;i<length;i++){
            firsts[i]=Firsts.get(i);
        }
        return firsts;
    }


}
