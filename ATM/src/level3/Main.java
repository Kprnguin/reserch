package level3;


public class Main {
    private static final int ERROR = -1;

    public static void main(String[] args) {
        int operation = 0;
        String userId;
        int setValue;
        TracsactionMenu tracsactionMenu = new TracsactionMenu();
        UserAuthentication userAuthentication = new UserAuthentication();
        AmountInput amountInput = new AmountInput();
        TransactionResult transactionResult = new TransactionResult();

        operation = tracsactionMenu.start();

        //入金を選択したとき
        if (operation == 1) {
            userId = userAuthentication.identifyUser();

            int depositAmount = amountInput.start(operation, userId);

            //入金額の表示と、預け金額の総額表示
            transactionResult.display(userId, depositAmount,operation);
        }

        //出金を選択したとき
        else if (operation == 2) {
            userId = userAuthentication.identifyUser();
            userAuthentication.passwordCheck(userId);

            int withdrawal = amountInput.start(operation, userId);

            //出金額の表示と、預け金額の総額表示
            transactionResult.display(userId,withdrawal,operation);
        }
    }
}
