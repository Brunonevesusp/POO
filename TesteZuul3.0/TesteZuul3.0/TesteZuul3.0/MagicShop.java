import java.util.ArrayList;
import java.util.List;

public class MagicShop {
    private List<Item> items;

    public MagicShop() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public String itemList() {
        StringBuilder itemList = new StringBuilder("Items available in the Magic Shop:\n");
        for (Item item : items) {
            itemList.append(item.toString()).append("\n");
        }
        return itemList.toString();
    }

    public void buyItem(BankAccount account, String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                if (account.getBalance() >= item.getPrice()) {
                    account.withdraw(item.getPrice());
                    account.addItem(item);
                    items.remove(item);
                    System.out.println("You bought a " + itemName);
                } else {
                    System.out.println("You don't have enough money to buy this item.");
                }
                return;
            }
        }
        System.out.println("Item not found in the Magic Shop.");
    }
}
