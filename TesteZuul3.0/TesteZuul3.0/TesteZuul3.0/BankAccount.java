import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private double balance;
    private List<Item> items;

    public BankAccount() {
        this.balance = 1000;
        this.items = new ArrayList<>(); // Inicializa a lista de itens
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("You have deposited " + amount + " into your account");
        } else
            System.out.println("Invalid amount");
    }

    public void withdraw(double amount) {
        if(balance >= amount) {
            balance -= amount;
            System.out.println("You have withdrawn " + amount + " from your account");
        } else
            System.out.println("Insufficient funds");
    }

    public double getBalance() {
        return balance;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }
}
