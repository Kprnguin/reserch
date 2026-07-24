package lebel2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final int ERROR = -1;

    public static void main(String[] args) {
        int operation = 0;
        String userId;
        int setValue;

        while (operation != 1 && operation != 2) {  //1か2を選択するまで選択しなおす
            System.out.println("-----------------------------");
            System.out.println("〇〇銀行ATM");
            System.out.println("何の操作を行いますか？");
            System.out.println("[1:入金][2:出金]");
            System.out.print("あなたの操作(数字を入力してください)：");
            setValue = 2;
            operation = select(setValue);   //setValueは選択肢(ボタン)の数
        }

        //入金を選択したとき
        if (operation == 1) {
            userId = userLogin();

            int depositAmount = amountFlow(operation, userId);

            //入金額の表示と、預け金額の総額表示
            print(userId, depositAmount,operation);
        }

        //出金を選択したとき
        else if (operation == 2) {
            userId = userLogin();
            passwordCheck(userId);

            int withdrawal = amountFlow(operation, userId);

            //出金額の表示と、預け金額の総額表示
            print(userId,withdrawal,operation);
        }
    }



    public static String userLogin(){
        //idを入力してもらう(キャッシュカードの認証)
        System.out.print("ユーザーIDを入力してください：");
        Scanner id = new Scanner(System.in);
        String userId = id.nextLine();
        System.out.println("-----------------------------");
        //idを参照する(存在しないとき例外処理)
        if (!Table.containsAccount(userId)) {
            System.out.println("不明なユーザーIDです");
            System.out.println("最初からやり直してください");
            System.exit(1);
        }
        return userId;
    }


    //select気に食わないので今後おそらく直します。
    public static int select(int setValue){
        int flag;
        Scanner input = new Scanner(System.in);
        try {
            flag = input.nextInt();
            System.out.println("-----------------------------");
            if(flag <= 0 || setValue < flag){
                System.out.println("操作が間違っています");
                System.out.println("もう一度操作を選択してください");
            }
        } catch (InputMismatchException e) {    //int型以外が入ったときの例外処理
            System.out.println("-----------------------------");
            System.out.println("数字を入力してください。");
            return ERROR;
        }
        return flag;
    }


    public static int amountFlow(int operation, String userId){
        int flag = 2;
        int amount = 0;
        while (flag == 2) {
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
            flag = amountCheck(amount);
        }
        return amount;
    }


    public static int amountInput(int operation,String userId){
        //金額を入力してもらう(入力金額が1,000,000円(お札100枚まで) or 0円未満の場合例外処理)
        int amount;
        Scanner input = new Scanner(System.in);
        try {
            amount = input.nextInt();
            System.out.println("入力額：" + amount + "円");
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
                if(amount < 0 || amount > 1000000 ||Table.getBalance(userId) < amount){
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



    public static int amountCheck(int amount){
        //入力内容に間違いがないかを確認する
        int setValue = 2;//setValueは選択肢(ボタン)の数
        int flag = 0;
        while (flag < 1 || setValue < flag) {
            System.out.println("入力内容を確認してください\n");
            System.out.println("入力金額：" + amount + "円\n");
            System.out.println("[1:次に進む][2:入力しなおす]");
            System.out.print("あなたの操作(数字を入力してください)：");
            flag = select(setValue);
        }
        return flag;
    }



    public static int passwordInput() {
        int password;
        //パスワードを入力してもらう
        System.out.print("パスワードを入力してください：");
        Scanner pass = new Scanner(System.in);
        try {
            password = pass.nextInt();
            System.out.println("-----------------------------");
            return password;
        } catch (InputMismatchException e) {    //int型以外が入ったときの例外処理
            System.out.println("-----------------------------");
            System.out.println("数字を入力してください。");
            System.out.println("-----------------------------");
            return ERROR;
        }
    }



    public static void passwordCheck(String userId){
        //idに対してパスワードを参照する(3回間違えると操作できなくなる)
        int i = 0;
        int password;
        while (true){
            password = passwordInput();

            //パスワードが正しければreturn
            int correctPassword = Table.getPassword(userId);
            if (correctPassword == password) {
                return;
            }else if(password == -1){
                continue;
            }
            System.out.println("パスワードが間違っています");
            i++;

            //3回間違えると操作を強制終了
            if(i == 3){
                System.out.println("3回パスワードを間違えたため操作を終了します");
                System.exit(1);
            }
            System.out.println("もう一度入力してください");
            System.out.println("-----------------------------");
        }
    }



    public static void print(String userId,int amount,int operation){
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