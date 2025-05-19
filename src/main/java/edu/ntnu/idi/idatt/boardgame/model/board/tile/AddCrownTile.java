package edu.ntnu.idi.idatt.boardgame.model.board.tile;


import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.AddCrownAction;
import edu.ntnu.idi.idatt.boardgame.model.player.ParioMartyPlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

public class AddCrownTile extends SpecialTile {

  /**
   * Constructor for the AddCrownTile class.
   *
   * @param tileNumber       The number of the tile on the board.
   * @param onscreenPosition The position of the tile on the screen.
   */
  public AddCrownTile(int tileNumber, int[] onscreenPosition) {
    this.tileNumber = tileNumber;
    this.onscreenPosition = onscreenPosition;
    this.tileAction = new AddCrownAction();

    this.tileType = TileType.ADD_CROWN;
  }

  @Override
  public void performAction(Player player) {
    try {
      if (((ParioMartyPlayer) player).getCoins() > 30) {
        tileAction.performAction(player);
        ((ParioMartyPlayer) player).removeCoins(30);

      } else {
        throw new IllegalArgumentException("Player does not have 30 or more coins.");
      }

    } catch (NullPointerException e) {
      throw new NullPointerException(e.getMessage());
    }
  }

}
