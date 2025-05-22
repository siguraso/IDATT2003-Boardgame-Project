package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.exception.InvalidPlayerException;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.RemoveCoinsAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.RemoveCrownAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.ReturnToStartAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.TileAction;
import edu.ntnu.idi.idatt.boardgame.model.player.ParioMartyPlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

public class MowserTile extends SpecialTile {

  private final TileAction[] tileActions = new TileAction[4];
  private int randomIndex;


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
      randomIndex = (int) (Math.random() * tileActions.length);
      tileAction = tileActions[randomIndex];
      try {
        tileAction.performAction(player);
      } catch (ClassCastException e) {
        throw new ClassCastException(e.getMessage());
      }

    } catch (NullPointerException e) {
      throw new NullPointerException(e.getMessage());
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
