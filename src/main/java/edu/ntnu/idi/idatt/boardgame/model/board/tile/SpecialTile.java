package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.TileAction;
import edu.ntnu.idi.idatt.boardgame.model.observerPattern.BoardGameObservable;
import edu.ntnu.idi.idatt.boardgame.model.observerPattern.BoardGameObserver;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A special tile on the board, where the player can moveForward to, and a TileAction happens.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public abstract class SpecialTile implements Tile, Serializable {

  protected int tileNumber;
  protected int[] onscreenPosition;
  protected TileAction tileAction;

  protected List<BoardGameObserver> observers = new ArrayList<>();

  @Override
  public int getTileNumber() {
    return tileNumber;
  }

  @Override
  public int[] getOnscreenPosition() {
    return onscreenPosition;
  }

  /**
   * Performs a special tile action on a player.
   *
   * @param player the player to perform the action on.
   */
  public void performAction(Player player) {
    tileAction.performAction(player);
  }

  @Override
  public String getTileType() {
    return "SpecialTile";
  }

}
