package level3;


public class Main {
    private static final int ERROR = -1;

    public static void main(String[] args) {
        int operation;
        String userId;
        int setValue;
        User user = new User();
        Account account = new Account();
        InputChecker inputChecker = new InputChecker();

        while (true) {  //1か2を選択するまで選択しなおす
            System.out.println("-----------------------------");
            System.out.println("〇〇銀行ATM");
            System.out.println("何の操作を行いますか？");
            System.out.println("[1:入金][2:出金]");
            System.out.print("あなたの操作(数字を入力してください)：");
            setValue = 2;
            operation = inputChecker.select(setValue);   //setValueは選択肢(ボタン)の数
            if (operation == 1 || operation == 2) {
                break;
            }
        }

        //入金を選択したとき
        if (operation == 1) {
            userId = user.login();

            int depositAmount = account.amountFlow(operation, userId);

            //入金額の表示と、預け金額の総額表示
            print(userId, depositAmount,operation);
        }

        //出金を選択したとき
        else if (operation == 2) {
            userId = user.login();
            user.passwordCheck(userId);

            int withdrawal = account.amountFlow(operation, userId);

            //出金額の表示と、預け金額の総額表示
            print(userId,withdrawal,operation);
        }
    }


    public static void print(String userId,int amount,int operation){
        //入出金額の表示と、預け金額の総額表示
        System.out.println("元の口座額：" + Table.getBalance(userId));
        if(operation == 1){
            System.out.println("入金金額：" + amount);
        }
        if(operation == 2){
            System.out.println("出金金額：" + amount);
            amount = -1 * amount;
        }
        Table.updateBalance(userId, amount);//引き出すため入力した出金額に-1の積を渡す
        System.out.println("預け総額：" + Table.getBalance(userId));
    }
}
