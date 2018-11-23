package dataStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dmrfcoder
 * @date 2018/11/23
 */
public class SolverTable {
    public String[][] table;
    public Map<String, Integer> mapVt, mapVn;

    public SolverTable(int rowCount, int columnCount) {
        table = new String[rowCount][columnCount];
        mapVt = new HashMap<String, Integer>();
        mapVn = new HashMap<String, Integer>();


    }


}

