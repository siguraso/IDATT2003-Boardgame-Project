package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.exception.InvalidBoardException;
import edu.ntnu.idi.idatt.boardgame.exception.InvalidPlayerException;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.RemoveCoinsAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.RemoveCrownAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.ReturnToStartAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.TileAction;
import edu.ntnu.idi.idatt.boardgame.model.player.ParioMartyPlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.Random;

/**
 * A special tile that only appears in the ParioMarty game. This tile has 4 tile actions that happen
 * randomly when the player lands on it. The actions are:
 * <ul>Remove 20 coins</ul>
 * <ul>Remove all coins (automatically set to 0, updated during the game to match a players coin
 * count</ul>
 * <ul>Remove 1 crown</ul>
 * <ul>Return to start</ul>
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class MowserTile extends SpecialTile {

  private final TileAction[] tileActions = new TileAction[4];
  private int randomIndex;
  private final transient Random random = new Random();


  /**
   * Constructor for the MowserTile class.
   *
   * @param tileNumber       The number of the tile on the board.
   * @param onscreenPosition The position of the tile on the screen.
   */
  public MowserTile(int tileNumber, int[] onscreenPosition) {
    super(tileNumber, onscreenPosition);
    this.tileType = TileType.MOWSER;

    initializeTileActions();
  }

  /**
   * Accesses the last {@link TileAction} that was performed on the tile.
   *
   * @return the last {@link TileAction} (represented as an int) that was performed on the tile.
   */
  public int getTileAction() {
    if (tileAction == null) {
      throw new NullPointerException("Tile action is null.");
    }

    return randomIndex;
  }

  @Override
  public void performAction(Player player) {
    try {
      // initialize the tileAction with a random TileAction
      randomIndex = random.nextInt(0, tileActions.length);
      tileAction = tileActions[randomIndex];

      tileAction.performAction(player);


    } catch (NullPointerException e) {
      throw new NullPointerException(e.getMessage());
    } catch (InvalidPlayerException e) {
      throw new InvalidPlayerException(e.getMessage());
    }
  }

  /**
   * Sets the amount of coins to remove from the player when the random action lands on the second
   * action, which is to remove all coins.
   *
   * @param player the player that has landed on the moweser tile.
   */
  public void setPlayerCoins(Player player) {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }
    try {
      ((RemoveCoinsAction) tileActions[1]).setCoinsToRemove(((ParioMartyPlayer) player).getCoins());
    } catch (ClassCastException e) {
      throw new InvalidPlayerException(e.getMessage());
    }
  }


  private void initializeTileActions() {
    // remove 20 coins from the player
    tileActions[0] = new RemoveCoinsAction(20);
    // remove all coins from the player (very nice)
    tileActions[1] = new RemoveCoinsAction(0);
    // remove 1 crown from the player (again, very nice)
    tileActions[2] = new RemoveCrownAction();
    // move back to start
    tileActions[3] = new ReturnToStartAction();
  }


}
