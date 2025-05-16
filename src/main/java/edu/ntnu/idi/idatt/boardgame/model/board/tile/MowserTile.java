package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.RemoveCoinsAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.RemoveCrownAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.ReturnToStartAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.TileAction;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

public class MowserTile extends SpecialTile {

  private final TileAction[] tileActions = new TileAction[4];


  /**
   * Constructor for the MowserTile class.
   *
   * @param tileNumber       The number of the tile on the board.
   * @param onscreenPosition The position of the tile on the screen.
   */
  public MowserTile(int tileNumber, int[] onscreenPosition) {
    this.tileNumber = tileNumber;
    this.onscreenPosition = onscreenPosition;
    this.tileType = TileType.MOWSER;

    initializeTileActions();
  }

  /**
   * Accesses the last {@link TileAction} that was performed on the tile.
   *
   * @return the last {@link TileAction} (represented as a String) that was performed on the tile.
   */
  public String getTileAction() {
    if (tileAction == null) {
      throw new NullPointerException("Tile action is null.");
    }

    return tileAction.getClass().getSimpleName();
  }

  @Override
  public void performAction(Player player) {
    try {
      // initialize the tileAction with a random TileAction
      int randomIndex = (int) (Math.random() * tileActions.length);
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
