package test.game;

import main.game.CardMerchant;
import main.game.Game;
import main.game.Player;
import main.game.Presenter;
import main.game.controller.Controller;
import main.game.controller.PlayerInput;
import org.junit.Before;
import org.junit.Test;
import test.game.mocks.MockCardMerchant;
import test.game.mocks.MockController;
import test.game.mocks.MockPresenter;

import java.util.ArrayList;
import java.util.List;

import static main.game.Phase.ACTION;
import static main.game.Phase.BUY;
import static main.game.controller.InputAction.ADVANCE_TURN;
import static main.game.controller.InputAction.QUIT;
import static org.junit.Assert.*;

public class GameTest {

  private Game game;
  private List<Player> players;
  private Player player1;
  private Player player2;

  @Before
  public void setup() {
    CardMerchant cardMerchant = new MockCardMerchant();
    Presenter presenter = new MockPresenter();
    Controller controller = new MockController();
    players = new ArrayList<>();
    player1 = new Player("Test Player 1", cardMerchant);
    player2 = new Player("Test Player 2", cardMerchant);
    players.add(player1);
    players.add(player2);
    this.game = new Game(players, cardMerchant, presenter, controller);
  }

  @Test
  public void gameStartsWithFirstPlayersTurn() {
    assertEquals(player1, this.game.getCurrentPlayer());
  }

  @Test
  public void gameAdvancesToPlayerTwoOnEndOfTurn() {
    game.advanceTurn();
    game.advanceTurn();
    game.advanceTurn();
    assertEquals(ACTION, game.getPhase());
    assertEquals(player2, game.getCurrentPlayer());
  }

  @Test
  public void gameRollsOverToPlayerOnesTurnAfterLastPlayersTurn() {
    game.advanceTurn();
    game.advanceTurn();
    game.advanceTurn();
    assertEquals(player2, game.getCurrentPlayer());
    game.advanceTurn();
    game.advanceTurn();
    game.advanceTurn();
    assertEquals(player1, game.getCurrentPlayer());
  }

  @Test
  public void gameStartsInActionPhase() {
    assertEquals(ACTION, game.getPhase());
  }

  @Test
  public void advancePhasePlayerActionAdvancesPhase() {
    PlayerInput input = new PlayerInput();
    input.setInputAction(ADVANCE_TURN);
    game.processPlayerInput(input);
    assertEquals(BUY, game.getPhase());
  }

  @Test
  public void gameOverInitializedAsFalse() {
    assertFalse(game.isOver());
  }

  @Test
  public void quitPlayerInputSetsGameOverTrue() {
    PlayerInput input = new PlayerInput();
    input.setInputAction(QUIT);
    game.processPlayerInput(input);
    assertTrue(game.isOver());
  }
}
