package test.game;

import main.game.controller.Controller;
import main.game.controller.InputAction;
import main.game.controller.NoSuchAction;
import main.game.controller.PlayerInput;

import java.util.Scanner;
import java.util.regex.Pattern;

import static main.game.controller.InputAction.*;

public class ConsoleController implements Controller {

  public PlayerInput acceptInput() {
    System.out.println("What would you like to do?");
    Scanner scanner = new Scanner(System.in);
    scanner.useDelimiter(Pattern.compile("\\n"));
    PlayerInput input = new PlayerInput();
    InputAction inputAction;
    String command = scanner.next().toLowerCase();
    switch (command) {
      case "play card":
        inputAction = PLAY_CARD;
        break;
      case "buy card":
        inputAction = BUY_CARD;
        break;
      case "advance turn":
        inputAction = ADVANCE_TURN;
        break;
      case "quit":
        inputAction = QUIT;
        break;
      default:
        throw new NoSuchAction(String.format("Can't process Command '%s'.", command));
    }
    input.setInputAction(inputAction);
    String nextMessage = "";
    switch (inputAction) {
      case ADVANCE_TURN:
      case QUIT:
        return input;
      case BUY_CARD:
        nextMessage = "Which card would you like to buy?";
        break;
      case PLAY_CARD:
        nextMessage = "Which card would you like to play?";
        break;
    }
    System.out.println(nextMessage);
    input.setCardName(scanner.next());
    return input;
  }
}
