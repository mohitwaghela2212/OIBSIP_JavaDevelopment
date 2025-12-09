// User.java
import java.util.ArrayList;

public class User {

    public String userId;
    public int balance;
    public ArrayList<Transaction> history;

    public User(String userId, int initialBalance) {
        this.userId = userId;
        this.balance = initialBalance;
        this.history = new ArrayList<>();

        // initial entry
        history.add(new Transaction("Initial Balance", initialBalance, initialBalance));
    }

    public boolean withdraw(int amount) {
        if (amount <= 0) return false;
        if (amount > balance) return false;

        balance -= amount;
        history.add(new Transaction("Withdraw", amount, balance));
        return true;
    }

    public void deposit(int amount) {
        if (amount <= 0) return;
        balance += amount;
        history.add(new Transaction("Deposit", amount, balance));
    }

    public boolean transfer(int amount) {
        if (amount <= 0) return false;
        if (amount > balance) return false;

        balance -= amount;
        history.add(new Transaction("Transfer", amount, balance));
        return true;
    }
}
