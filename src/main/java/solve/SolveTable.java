package solve;

import java.util.HashMap;
import java.util.Map;

public class SolveTable {
    public String[][] table;
    public Map<String, Integer> mapVt, mapVn;

    public SolveTable(int rowCount, int columnCount) {
        table = new String[rowCount][columnCount];
        mapVt = new HashMap<String, Integer>();
        mapVn = new HashMap<String, Integer>();


    }
}
