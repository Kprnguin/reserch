package level3;


import java.util.HashMap;
import java.util.Map;

public class Table {

    private static final Map<String, Integer> accountTable = Map.of(
            "qwer",1234,
            "asdf",2345,
            "zxcv",3456
    );


    private static final Map<String, Integer> balanceTable = new HashMap<>(Map.of(
            "qwer", 100000,
            "asdf", 50000,
            "zxcv", 1111
    ));

    public static boolean containsAccount(String id) {
        return accountTable.containsKey(id);
    }
    public static int getPassword(String id) {
        return accountTable.getOrDefault(id, 0);
    }
    public static int getBalance(String id) {
        return balanceTable.getOrDefault(id, 0);
    }

    public static void updateBalance(String id , int amount) {
        amount = getBalance(id) + amount;
        balanceTable.put(id,amount);
    }

}