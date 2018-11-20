import java.util.List;

/**
 * @author dmrfcoder
 * @date 2018/11/20
 */
public interface IEliminateLeftRecursion {
    void eliminateDirectLeftRecursion(String str);

    boolean isVn(char c);

    String arrayToString(List<String> ep);
}
