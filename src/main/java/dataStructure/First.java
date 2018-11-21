package dataStructure;
import javafx.util.*;
import java.util.HashSet;

public class First {
	public String vt;
	public HashSet<Pair<String,Production>> firstSet;
	
	public First(String VT)
	{
		this.vt=VT;
	}
	
	public void addElement(String element, Production production)
	{
		Pair<String,Production> pair = new Pair<String, Production>(element,production);
		firstSet.add(pair);
		
		//若first(A) = {a,b,c}, A->a|b|c
		//则
		//vt = "A"
		//firstSet = { [a,A->a] , [b,A->b] , [c, A->c]}
	}
}
