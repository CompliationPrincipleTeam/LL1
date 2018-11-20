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
     * @param str
     */
    @Override
    public void eliminateDirectLeftRecursion(String str) {
        String[] split = str.split("->");
        if (split.length != 2) {
            return;
        }

        String p = split[0];
        String expression = split[1];

        String[] expressions = expression.split("\\|");

        List<String> ep1 = new ArrayList<String>();
        List<String> ep2 = new ArrayList<String>();


        for (String item : expressions) {

            char c = item.charAt(0);
            if (isVn(c)) {
                ep1.add(item.charAt(1) + p + "'");
            } else {
                ep2.add(c + p + "'");
            }
        }


        String strEp1 = "P'->" + arrayToString(ep1);
        String strEp2 = p + "->" + arrayToString(ep2);


        System.out.println(strEp2);
        System.out.println(strEp1);

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
