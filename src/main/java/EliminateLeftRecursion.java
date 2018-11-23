import dataStructure.Grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmrfcoder
 * @date 2018/11/20
 * 消除左递归
 */
public class EliminateLeftRecursion implements IEliminateLeftRecursion {

    /**
     * 消除直接左递归
     *
     * @param G
     */
    @Override
    public List<String> eliminateDirectLeftRecursion(Grammar G) {

        List<String> reslutGrammer = new ArrayList<String>();


        for (String str : G.strGrammer) {
            List<String> strings = eliminateItemDirectLeftRecursion(str);
            if (strings != null) {
                for (String s : strings) {
                    reslutGrammer.add(s);
                }
            }
        }

        return reslutGrammer;


    }


    private List<String> eliminateItemDirectLeftRecursion(String str) {
        String[] split = str.split("->");
        if (split.length != 2) {
            return null;
        }

        String p = split[0];
        String expression = split[1];

        String[] expressions = expression.split("\\|");

        List<String> ep1 = new ArrayList<String>();
        List<String> ep2 = new ArrayList<String>();

        List<String> result = new ArrayList<String>();


        for (String item : expressions) {

            char c = item.charAt(0);
            if (isVn(c)) {
                if (c == p.charAt(0)) {
                    ep2.add(item.substring(1) + p + "'");
                    ep2.add("$");
                } else {
                    ep1.add(item + p + "'");
                }


            } else {
                ep1.add(item);
            }
        }


        String strEp1;
        String strEp2;

        if (!"".equals(arrayToString(ep1))) {
            strEp1 = p + "->" + arrayToString(ep1);
            result.add(strEp1);
        }

        if (!"".equals(arrayToString(ep2))) {
            strEp2 = p + "'->" + arrayToString(ep2);
            result.add(strEp2);
        }


        return result;

    }

    /**
     * 根据大小写判断对应字符是否是非终结符
     *
     * @param c
     * @return
     */
    @Override
    public boolean isVn(char c) {
        return Character.isUpperCase(c);
    }

    @Override
    public String arrayToString(List<String> ep) {
        if (ep.size() == 0) {
            return "";
        }
        String str = "";
        for (String item : ep) {
            str = str + item + "|";
        }


        return str.substring(0, str.length() - 1);
    }

}
