import dataStructure.Grammar;

/**
 * @author dmrfcoder
 * @date 2018/11/20
 */
public class Main {
	public static Grammar G = new Grammar();
	
    public static void main(String[] args) {
        String ep1 = "P->Pa|Pb|Pc|Pd|e|f|g";
        EliminateLeftRecursion eliminateLeftRecursion = new EliminateLeftRecursion();
        eliminateLeftRecursion.eliminateDirectLeftRecursion(ep1);
        
        
        //初始化G的vt集合与vn集合
        G.vn.add("E");
        G.vn.add("T");
        G.vn.add("F");
        
        G.vt.add("+");
        G.vt.add("*");
        G.vt.add("(");
        G.vt.add(")");
        G.vt.add("i");
        
//		添加产生式，以如下形式添加，但是addProduction会自动转换为如下形式：
//      E->E+T
//      E->T
//      T->T*F
//      T->F
//      F->(E)
//      F->i
        G.addProduction("E", "E+T|T");
        G.addProduction("T", "T*F|F");
        G.addProduction("F", "(E)|i");
        
        //此处调用消除左递归
        
        
        
        //此处调用提取左公因子
        
        
        //此处求First与Follow
        
        
        //此处构造LL1分析表
        

    }
}
