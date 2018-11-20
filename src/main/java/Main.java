/**
 * @author dmrfcoder
 * @date 2018/11/20
 */
public class Main {

    public static void main(String[] args) {
        String ep1 = "P->Pa|Pb|Pc|Pd|e|f|g";
        EliminateLeftRecursion eliminateLeftRecursion = new EliminateLeftRecursion();
        eliminateLeftRecursion.eliminateDirectLeftRecursion(ep1);

    }
}
