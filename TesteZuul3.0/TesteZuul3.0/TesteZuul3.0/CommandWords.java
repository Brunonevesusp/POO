/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Bruno Neves Boa Sorte nUSP 14562528
 * @author  Murillo Domingos de Almeida nUSP 14804083
 * @version 2008.03.30
 */

 
public class CommandWords {
    public enum CommandWord {
        GO, QUIT, HELP, LOOK, UNKNOWN, BACK, LIST, BUY, DEPOSIT, SHOW
    }

    public boolean isCommand(String input) {
        for (CommandWord command : CommandWord.values()) {
            if (command.toString().equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }
}
