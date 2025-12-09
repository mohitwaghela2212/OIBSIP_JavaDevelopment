public class Transaction {
    String type;
    int amount;
    int balanceAfter;

    public Transaction(String type, int amount, int balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }
}
