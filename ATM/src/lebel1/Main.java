package lebel1;

import lebel1.Table;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int flag;
        int operation;

        while (true) {
            System.out.println("-----------------------------");
            System.out.println("〇〇銀行ATM");
            System.out.println("何の操作を行いますか？");
            System.out.println("[1:入金][2:出金]");
            System.out.print("あなたの操作(数字を入力してください)：");
            Scanner input = new Scanner(System.in);
            try {
                operation = input.nextInt();
                System.out.println("-----------------------------");
                if(operation != 1 && operation != 2){
                    System.out.println("操作が間違っています");
                    System.out.println("もう一度操作を選択してください");
                    continue;
                }
            } catch (InputMismatchException e) {    //int型以外が入ったときの例外処理
                System.out.println("-----------------------------");
                System.out.println("数字を入力してください。");
                continue;
            }
            break;
        }



        //入金を選択したとき
        if (operation == 1) {
            //idを入力してもらう(キャッシュカードの認証)
            System.out.print("ユーザーIDを入力してください：");
            Scanner id = new Scanner(System.in);
            String userId = id.nextLine();
            System.out.println("-----------------------------");

            //idを参照する(存在しないとき例外処理)
            if (!Table.containsAccount(userId)) {
                System.out.println("不明なユーザーIDです");
                System.out.println("最初からやり直してください");
                return;
            }

            //入金金額を入力してもらう(入力金額が1,000,000円(お札100枚まで) or 0円未満の場合例外処理)
            int depositAmount;
            while (true) {
                System.out.println("現在の口座額：" + Table.getBalance(userId) + "円");
                System.out.println("入金金額を入れてください：");
                Scanner amount = new Scanner(System.in);
                try {
                    depositAmount = amount.nextInt();
                    System.out.println("入力額：" + depositAmount + "円");
                    System.out.println("-----------------------------");
                    //int型の例外処理
                    if (depositAmount < 0 || depositAmount > 1000000) {
                        System.out.println("金額が適切ではありません");
                        System.out.println("もう一度入力してください");
                        System.out.println("-----------------------------");
                        continue;
                    }
                } catch (InputMismatchException e) {    //int型以外が入ったときの例外処理
                    System.out.println("-----------------------------");
                    System.out.println("数字を入力してください。");
                    System.out.println("-----------------------------");
                    continue;
                }

                //入力内容に間違いがないかを確認する
                while (true) {
                    System.out.println("入力内容を確認してください\n");
                    System.out.println("入力金額：" + depositAmount + "円\n");
                    System.out.println("[1:次に進む][2:入力しなおす]");
                    System.out.print("あなたの操作(数字を入力してください)：");
                    Scanner input2 = new Scanner(System.in);
                    try {
                        flag = input2.nextInt();
                        System.out.println("-----------------------------");
                        //int型の例外処理
                        if (flag != 1 && flag != 2) {
                            System.out.println("操作が間違っています");
                            System.out.println("再度操作を入力してください");
                            System.out.println("-----------------------------");
                            continue;
                        }
                    } catch (InputMismatchException e) {    //int型以外が入ったときの例外処理
                        System.out.println("-----------------------------");
                        System.out.println("数字を入力してください。");
                        System.out.println("-----------------------------");
                        continue;
                    }
                    break;
                }
                if (flag == 2) {
                    continue;
                }
                break;
            }
            //入金額の表示と、預け金額の総額表示
            System.out.println("元の口座額：" + Table.getBalance(userId) + "円");
            System.out.println("入金額：" + depositAmount + "円");
            Table.updateBalance(userId, depositAmount);
            System.out.println("預け総額：" + Table.getBalance(userId) + "円");
        }




        //出金を選択したとき
        else if (operation == 2) {
            //idを入力してもらう(キャッシュカードの認証)
            System.out.print("ユーザーIDを入力してください：");
            Scanner id = new Scanner(System.in);
            String userId = id.nextLine();
            System.out.println("-----------------------------");

            //idを参照する(存在しないとき例外処理)
            if (!Table.containsAccount(userId)) {
                System.out.println("不明なユーザーIDです");
                System.out.println("最初からやり直してください");
                return;
            }

            //idに対してパスワードを参照する(3回間違えると操作できなくなる)
            int i = 0;
            int password;
            while (true){
                //パスワードを入力してもらう
                System.out.print("パスワードを入力してください：");
                Scanner pass = new Scanner(System.in);
                try {
                    password = pass.nextInt();
                    System.out.println("-----------------------------");
                } catch (InputMismatchException e) {    //int型以外が入ったときの例外処理
                    System.out.println("-----------------------------");
                    System.out.println("数字を入力してください。");
                    System.out.println("-----------------------------");
                    continue;
                }

                //パスワードが正しければbreak
                int correctPassword = Table.getPassword(userId);
                if (correctPassword == password) {
                    break;
                }
                System.out.println("パスワードが間違っています");
                i++;

                //3回間違えると操作を強制終了
                if(i == 3){
                    System.out.println("3回パスワードを間違えたため操作を終了します");
                    return;
                }
                System.out.println("もう一度入力してください");
                System.out.println("-----------------------------");
            }

            //出金金額を入力してもらう(入力金額が1,000,000円(お札100枚まで) or 0円未満の場合例外処理)
            int withdrawal;
            while (true) {
                System.out.println("現在の口座額：" + Table.getBalance(userId) + "円");
                System.out.println("出金金額を入れてください：");
                Scanner amount = new Scanner(System.in);
                try {
                    withdrawal = amount.nextInt();
                    System.out.println("-----------------------------");
                    //int型の例外処理
                    if (withdrawal < 0 || withdrawal > 1000000 || Table.getBalance(userId) < withdrawal) {
                        System.out.println("金額が適切ではありません");
                        System.out.println("もう一度入力してください");
                        System.out.println("-----------------------------");
                        continue;
                    }
                } catch (InputMismatchException e) {    //int型以外が入ったときの例外処理
                    System.out.println("-----------------------------");
                    System.out.println("数字を入力してください。");
                    System.out.println("-----------------------------");
                    continue;
                }

                //入力内容に間違いがないかを確認する
                while (true) {
                    System.out.println("入力内容を確認してください\n");
                    System.out.println("出金金額：" + withdrawal + "円\n");
                    System.out.println("[1:次に進む][2:入力しなおす]");
                    System.out.print("あなたの操作(数字を入力してください)：");
                    Scanner input2 = new Scanner(System.in);
                    try {
                        flag = input2.nextInt();
                        System.out.println("-----------------------------");
                        //int型の例外処理
                        if (flag != 1 && flag != 2) {
                            System.out.println("操作が間違っています");
                            System.out.println("再度操作を入力してください");
                            System.out.println("-----------------------------");
                            continue;
                        }
                    } catch (InputMismatchException e) {    //int型以外が入ったときの例外処理
                        System.out.println("-----------------------------");
                        System.out.println("数字を入力してください。");
                        System.out.println("-----------------------------");
                        continue;
                    }
                    break;
                }
                if(flag == 2){
                    continue;
                }
                break;
            }
            //出金額の表示と、預け金額の総額表示
            System.out.println("元の口座額：" + Table.getBalance(userId) + "円");
            System.out.println("出金額：" + withdrawal + "円");
            Table.updateBalance(userId, -1*withdrawal);//引き出すため入力した出金額に-1の積を渡す
            System.out.println("預け総額：" + Table.getBalance(userId) + "円");
        }
    }
}