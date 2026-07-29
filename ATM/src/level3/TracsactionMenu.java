package level3;

public class TracsactionMenu {
    private final int ERROR = -1;
    int setValue;
    InputValidator inputValidator;

    TracsactionMenu(){
        this.inputValidator = new InputValidator();
    }

    public int start() {
        int operation = ERROR;
        while (operation != 1 && operation != 2) {  //1か2を選択するまで選択しなおす
            System.out.println("-----------------------------");
            System.out.println("〇〇銀行ATM");
            System.out.println("何の操作を行いますか？");
            System.out.println("[1:入金][2:出金]");
            System.out.print("あなたの操作(数字を入力してください)：");
            setValue = 2;
            operation = inputValidator.validateSelection(setValue);   //setValueは選択肢(ボタン)の数
        }
        return operation;
    }
}
