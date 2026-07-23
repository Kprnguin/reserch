package level3;


import java.util.InputMismatchException;
import java.util.Scanner;

public class User {
    private final int ERROR = -1;

    public String login(){
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

    public void passwordCheck(String userId){
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

    private int passwordInput() {
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
}
