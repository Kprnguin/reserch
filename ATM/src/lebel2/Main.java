package lebel2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int flag;
        int operation;
        String userId;
        int setValue;

        while (true) {  //1か2を選択するまで選択しなおす
            System.out.println("-----------------------------");
            System.out.println("〇〇銀行ATM");
            System.out.println("何の操作を行いますか？");
            System.out.println("[1:入金][2:出金]");
            System.out.print("あなたの操作(数字を入力してください)：");
            operation = select(2);   //setValueは選択肢の数
            if (operation == 1 || operation == 2) {
                break;
            }
        }

        //入金を選択したとき
        if (operation == 1) {
            userId = accountLogin();
            //idを参照する(存在しないとき例外処理)
            if (!Table.containsAccount(userId)) {
                System.out.println("不明なユーザーIDです");
                System.out.println("最初からやり直してください");
                return;
            }

            //入金金額を入力してもらう(入力金額が1,000,000円(お札100枚まで) or 0円未満の場合例外処理)
            int depositAmount = 0;
            while (true) {
                System.out.println("入金金額を入れてください：");
                depositAmount = amountInput();
                if (depositAmount == -1) {
                    continue;
                }

                //入力内容に間違いがないかを確認する
                while (true) {
                    System.out.println("入力内容を確認してください\n");
                    System.out.println("入力金額：" + depositAmount + "\n");
                    System.out.println("[1:次に進む][2:入力しなおす]");
                    System.out.print("あなたの操作(数字を入力してください)：");
                    setValue = 2;
                    flag = select(setValue);   //setValueは選択肢の数
                    if (0 < flag && flag <= setValue) {
                        break;
                    }
                }
                if (flag == 2) {    //入力を再度行う
                    continue;
                }
                break;
            }
            //入金額の表示と、預け金額の総額表示
            print(userId, depositAmount,operation);
        }

        //出金を選択したとき
        else if (operation == 2) {
            userId = accountLogin();
            //idを参照する(存在しないとき例外処理)
            if (!Table.containsAccount(userId)) {
                System.out.println("不明なユーザーIDです");
                System.out.println("最初からやり直してください");
                return;
            }

            //idに対してパスワードを参照する(3回間違えると操作できなくなる)
            int password = passwordCheck(userId);
            if (password == -1) {
                return;
            }

            //出金金額を入力してもらう(入力金額が1,000,000円(お札100枚まで) or 0円未満の場合例外処理)
            int withdrawal = 0;
            while (true) {
                System.out.println("出金金額を入れてください：");
                withdrawal = amountInput();
                if (withdrawal == -1) {
                    continue;
                }

                //入力内容に間違いがないかを確認する
                while (true) {
                    System.out.println("入力内容を確認してください\n");
                    System.out.println("出金金額：" + withdrawal + "\n");
                    System.out.println("[1:次に進む][2:入力しなおす]");
                    System.out.print("あなたの操作(数字を入力してください)：");
                    setValue = 2;
                    flag = select(setValue);   //setValueは選択肢の数
                    if (0 < flag && flag <= setValue) {
                        break;
                    }
                }
                if (flag == 2) {    //入力を再度行う
                    continue;
                }
                break;
            }
            //出金額の表示と、預け金額の総額表示
            print(userId,withdrawal,operation);
        }
    }



    public static String accountLogin(){
        //idを入力してもらう(キャッシュカードの認証)
        System.out.print("ユーザーIDを入力してください：");
        Scanner id = new Scanner(System.in);
        String userId = id.nextLine();
        System.out.println("-----------------------------");
        return userId;
    }



    public static int select(int setValue){
        int flag = 0;
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
            return -1;
        }
        return flag;
    }



    public static int amountInput(){
        int amount;
        Scanner input = new Scanner(System.in);
        try {
            amount = input.nextInt();
            System.out.println("入力額：" + amount);
            System.out.println("-----------------------------");
            //int型の例外処理
            if (amount < 0 || amount > 1000000) {
                System.out.println("金額が適切ではありません");
                System.out.println("もう一度入力してください");
                System.out.println("-----------------------------");
                return -1;
            }
        } catch (InputMismatchException e) {    //int型以外が入ったときの例外処理
            System.out.println("-----------------------------");
            System.out.println("数字を入力してください。");
            System.out.println("-----------------------------");
            return -1;
        }
        return amount;
    }



    public static int passwordInput() {
        int i = 0;
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
            return -1;
        }
    }



    public static int passwordCheck(String userId){
        //idに対してパスワードを参照する(3回間違えると操作できなくなる)
        int i = 0;
        int password;
        while (true){
            password = passwordInput();

            //パスワードが正しければreturn
            int correctPassword = Table.getPassword(userId);
            if (correctPassword == password) {
                return password;
            }else if(password == -1){
                continue;
            }
            System.out.println("パスワードが間違っています");
            i++;

            //3回間違えると操作を強制終了
            if(i == 3){
                System.out.println("3回パスワードを間違えたため操作を終了します");
                return -1;
            }
            System.out.println("もう一度入力してください");
            System.out.println("-----------------------------");
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