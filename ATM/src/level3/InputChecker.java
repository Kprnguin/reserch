package level3;


import java.util.InputMismatchException;
import java.util.Scanner;

public class InputChecker {
    private final int ERROR = -1;

    public int select(int setValue){
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
}
