package dataStructure;

import java.util.ArrayList;
import java.util.HashSet;

public class Grammar {
	public String S="";							//开始符号也可以是：production1[0].left
	public ArrayList<Production> production2;	//p2是把 E->A|b分开两个的产生式集合
	public ArrayList<Production> production1;	//p1是把 E->A|b当作一个产生式
	public HashSet<String> vt;	//终结符集合
	public HashSet<String> vn;	//非终结符集合
	
	public Grammar()
	{
		production1 = new ArrayList<Production>();
		production2 = new ArrayList<Production>();
		vt = new HashSet<String>();
		vn = new HashSet<String>();
	}
	
	
	public void addProduction(String left, String right)
	{
		this.production1.add(new Production(left,right));
		String[] r = right.split("\\|");
		for(int i=0;i<r.length;i++)
		{
			Production p = new Production();
			p.leftPart = left;
			p.rightPart = r[i];
			this.production2.add(p);
			System.out.println(p.leftPart +"->"+p.rightPart);
		}
	}
	

}
