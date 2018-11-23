import dataStructure.Grammar;

import java.util.List;

/**
 * @author dmrfcoder
 * @date 2018/11/20
 */
public interface IEliminateLeftRecursion {
    List<String> eliminateDirectLeftRecursion(Grammar G);

    boolean isVn(char c);

    String arrayToString(List<String> ep);
}
