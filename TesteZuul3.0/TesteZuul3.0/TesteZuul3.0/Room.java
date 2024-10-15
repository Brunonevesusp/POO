import java.util.HashMap;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Bruno Neves Boa Sorte nUSP 14562528
 * @author  Murillo Domingos de Almeida nUSP 14804083
 * @version 2008.03.30
 */



public class Room {
    private String description;
    private HashMap<String, Room> exits;
    private Item hiddenItem;
    private MagicShop magicShop;

    public Room(String description) {
        this.description = description;
        exits = new HashMap<>();
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }

    private String getExitString() {
        StringBuilder returnString = new StringBuilder("Exits:");
        for (String exit : exits.keySet()) {
            returnString.append(" ").append(exit);
        }
        return returnString.toString();
    }

    public void setHiddenItem(Item item) {
        this.hiddenItem = item;
    }

    public Item getHiddenItem() {
        return hiddenItem;
    }

    public void setMagicShop(MagicShop shop) {
        this.magicShop = shop;
    }

    public MagicShop getMagicShop() {
        return magicShop;
    }
}
