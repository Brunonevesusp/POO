/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Bruno Neves Boa Sorte nUSP 14562528
 * @author  Murillo Domingos de Almeida nUSP 14804083
 * @version 2008.03.30
 */


import java.util.*;

public class Game {
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private BankAccount account;

    public Game() {
        createRooms();
        parser = new Parser();
        account = new BankAccount(); // inicializa a conta bancária
    }

    private void createRooms() {
        Room outside, theatre, pub, lab, office, attic, basement;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        attic = new Room("in the attic");
        basement = new Room("in the basement");

        // create the itens
        Item key, potion, poison, ring, coins, cereal, book;
        key = new Item("key", "open any door", 800);
        potion = new Item("potion", "heal you from harms", 1000);
        poison = new Item("poison", "kill monsters", 1000);
        ring = new Item("ring", "give you power", 1000);
        coins = new Item("coins", "buy things", 1000);
        cereal = new Item("cereal", "eat it", 1000);
        book = new Item("book", "read it", 1000);

        MagicShop basementShop = new MagicShop();
        basementShop.addItem(new Item("key", "Opens any door", 1000));
        basementShop.addItem(new Item("cloak", "Makes you invisible", 1500));
        basementShop.addItem(new Item("wand", "Casts spells", 2000));
        basementShop.addItem(new Item("potion", "Heals you", 500));
        basementShop.addItem(new Item("poison", "Kills monsters", 500));
        basementShop.addItem(new Item("ring", "Gives you power", 500));
        basementShop.addItem(new Item("coins", "Buy things", 500));
        basementShop.addItem(new Item("cereal", "Eat it", 500));
        basementShop.addItem(new Item("book", "Read it", 500));
        // Associação do MagicShop ao Basement
        basement.setMagicShop(basementShop);

        // create a magic shop
        MagicShop shop = new MagicShop();

        // Add items to rooms
        basement.setHiddenItem(key);
        outside.setHiddenItem(potion);
        theatre.setHiddenItem(poison);
        pub.setHiddenItem(ring);
        lab.setHiddenItem(coins);
        office.setHiddenItem(cereal);
        attic.setHiddenItem(book);

        // initialize room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);
        lab.setExit("down", basement);

        office.setExit("west", lab);
        office.setExit("up", attic);

        attic.setExit("down", office);

        basement.setExit("up", lab);

        currentRoom = outside;  // start game outside
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Goodbye!");
    }

    public void printLocationInfo() {
        System.out.println(currentRoom.getLongDescription());
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("look")) {
            look();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } else if (commandWord.equals("back")) {
            goBack();
        } else if (commandWord.equals("list")) {
            listItems();
        } else if (commandWord.equals("buy")) {
            buyItem(command);
        } else if (commandWord.equals("deposit")) {
            if (account != null) { // Verifica se a conta está inicializada
                if (command.hasSecondWord()) { // Verifica se há um segundo comando
                    try {
                        double amount = Double.parseDouble(command.getSecondWord());
                        if (amount > 0) {
                            account.deposit(amount);
                        } else {
                            System.out.println("Invalid amount.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount format.");
                    }
                } else {
                    System.out.println("Usage: deposit <amount>");
                }
            }
        } else if (commandWord.equals("show")) { // Adição do comando show (mostrar)
            showItems();
        }

        return wantToQuit;
    }

    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help look back list buy deposit show");
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            previousRoom = currentRoom;
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }

    public void goBack() {
        if (previousRoom != null) {
            currentRoom = previousRoom;
            previousRoom = null;
            printLocationInfo();
        } else {
            System.out.println("You can't go back any further.");
        }
    }

    private void look() {
        if (currentRoom.getHiddenItem() != null) {
            System.out.println("There is a " + currentRoom.getHiddenItem() + " here.");
        } else {
            System.out.println("There are no items in this room.");
        }
        if (currentRoom.getMagicShop() != null) {
            System.out.println("There is a Magic Shop here. You can 'list' items or 'buy <item>'");
        }
    }

    private void listItems() {
        MagicShop shop = currentRoom.getMagicShop();
        if (shop != null) {
            System.out.println(shop.itemList());
        } else {
            System.out.println("There is no magic shop here.");
        }
    }

    private void buyItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Buy what?");
            return;
        }

        String itemName = command.getSecondWord();
        MagicShop shop = currentRoom.getMagicShop();
        if (shop != null) {
            shop.buyItem(account, itemName); // Usar a conta bancária para comprar itens
        } else {
            System.out.println("There is no magic shop here.");
        }
    }

    private void showItems() {
        if (account != null) {
            List<Item> items = account.getItems();
            if (!items.isEmpty()) {
                System.out.println("Items in your possession:");
                for (Item item : items) {
                    System.out.println("- " + item.getName() + ": " + item.getDescription());
                }
            } else {
                System.out.println("You don't have any items.");
            }
        } else {
            System.out.println("You don't have a bank account.");
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
