package level3;


import java.util.InputMismatchException;
import java.util.Scanner;

public class Account {
    private final int ERROR = -1;
    InputChecker inputChecker = new InputChecker();

    public int amountFlow(int operation, String userId){
        int flag;
        int amount;
        while (true) {
            System.out.println("現在の口座額：" + Table.getBalance(userId) + "円");
            if(operation == 1){
                System.out.println("入金金額を入れてください：");
            }else if(operation == 2) {
                System.out.println("出金金額を入れてください：");
            }
            amount = amountInput(operation,userId);
            if (amount == ERROR) {
                continue;
            }

            //入力内容に間違いがないかを確認する
            flag = check(amount);
            if (flag == 2) {    //入力を再度行う
                continue;
            }
            return amount;
        }
    }


    private int amountInput(int operation,String userId){
        //金額を入力してもらう(入力金額が1,000,000円(お札100枚まで) or 0円未満の場合例外処理)
        int amount;
        Scanner input = new Scanner(System.in);
        try {
            amount = input.nextInt();
            System.out.println("入力額：" + amount);
            System.out.println("-----------------------------");
            //int型の例外処理
            if(operation == 1){
                if(amount < 0 || amount > 1000000){
                    System.out.println("金額が適切ではありません");
                    System.out.println("もう一度入力してください");
                    System.out.println("-----------------------------");
                    return ERROR;
                }
            }if(operation == 2){
                if(amount < 0 || amount > 1000000 || Table.getBalance(userId) < amount){
                    System.out.println("金額が適切ではありません");
                    System.out.println("もう一度入力してください");
                    System.out.println("-----------------------------");
                    return ERROR;
                }
            }
        } catch (InputMismatchException e) {    //int型以外が入ったときの例外処理
            System.out.println("-----------------------------");
            System.out.println("数字を入力してください。");
            System.out.println("-----------------------------");
            return ERROR;
        }
        return amount;
    }



    private int check(int amount){
        //入力内容に間違いがないかを確認する
        int setValue;//setValueは選択肢(ボタン)の数
        int flag;
        while (true) {
            System.out.println("入力内容を確認してください\n");
            System.out.println("入力金額：" + amount + "\n");
            System.out.println("[1:次に進む][2:入力しなおす]");
            System.out.print("あなたの操作(数字を入力してください)：");
            setValue = 2;
            flag = inputChecker.select(setValue);
            if (0 < flag && flag <= setValue) {
                return flag;
            }
        }
    }
}
