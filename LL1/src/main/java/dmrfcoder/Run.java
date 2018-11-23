package dmrfcoder;
import dataStructure.First;
import dataStructure.Grammar;
import dataStructure.Production;
import javafx.util.Pair;
import solve.GetFirst;
import solve.SolveFollowSet;
import solve.SolveTable;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author dmrfcoder
 * @date 2018/11/20
 */
public class Run {
    public static void run(ArrayList<Production> pro, ArrayList<String> vn, ArrayList<String> vt) throws IOException {
        //初始化G的vt集合与vn集合
    	Grammar G = new Grammar();
    	for(String terminal :vt)
    		G.vt.add(terminal);
    	for(String nonterminal : vn)
    		G.vn.add(nonterminal);
    	for(Production p:pro)
    	{
    		G.addProduction(p.leftPart,p.rightPart, true);
    	}
//        G.vn.add("E");
//        G.vn.add("T");
//        G.vn.add("F");
//
//        G.vt.add("+");
//        G.vt.add("*");
//        G.vt.add("(");
//        G.vt.add(")");
//        G.vt.add("i");

//		添加产生式，以如下形式添加，但是addProduction会自动转换为如下形式：
//      E->E+T
//      E->T
//      T->T*F
//      T->F
//      F->(E)
//      F->i
//        G.addProduction("E", "E+T|T", true);
//        G.addProduction("T", "T*F|F", true);
//        G.addProduction("F", "(E)|i", true);

        //此处调用消除左递归

        EliminateLeftRecursion eliminateLeftRecursion = new EliminateLeftRecursion();
        List<String> strings = eliminateLeftRecursion.eliminateDirectLeftRecursion(G);
        G.clearGrammer();
        for (String str : strings) {
            String[] split = str.split("->");
            //System.out.println(str);
            G.addProduction(split[0], split[1], false);
        }
        
        for(Production p: G.production2)
        {
        	System.out.println(p.leftPart+"->"+p.rightPart);
        }


        //此处调用提取左公因子


        //此处求First与Follow
        GetFirst getfirst = new GetFirst();
        First[] first = getfirst.getFirst(G);
        FileWriter fw = new FileWriter("First.txt");
        for(First f:first)
        {
        	System.out.print("First("+f.vn + "):");
        	fw.write(f.vn+" ");
        	for(Pair<String, Production> pair:f.firstSet)
        	{
        		System.out.print(pair.getKey()+" ");
        		fw.write(pair.getKey()+" ");
        	}
        	System.out.println();
        	fw.write("\n");
        }
        fw.close();
        
        fw = new FileWriter("follow.txt");
        //修改的地方:此处把SolveFollowSet.solve中的follow返回
        SolveFollowSet solve = new SolveFollowSet();
        HashMap<String, String> follow = solve.getFollow(G, first);
        for(String key : follow.keySet())
        {
        	fw.write(key+" "+follow.get(key)+"\n");
        }
        fw.close();

        //此处构造LL1分析表
        SolveTable.solve();


    }
}
