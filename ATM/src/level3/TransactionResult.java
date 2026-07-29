package level3;

public class TransactionResult {

    public void display(String userId, int amount, int operation){
        //入出金額の表示と、預け金額の総額表示
        System.out.println("元の口座額：" + Table.getBalance(userId) + "円");
        if(operation == 1){
            System.out.println("入金金額：" + amount + "円");
        }
        if(operation == 2){
            System.out.println("出金金額：" + amount + "円");
            amount = -1 * amount;
        }
        Table.updateBalance(userId, amount);//引き出すため入力した出金額に-1の積を渡す
        System.out.println("預け総額：" + Table.getBalance(userId) + "円");
    }
}
