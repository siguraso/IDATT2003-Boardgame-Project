package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.ReturnToStartAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.RollAgainAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.SwapPlayersAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.TileAction;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.util.HashMap;

/**
 * Tile that performs a random action when a player lands on it.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class RandomActionTile extends SpecialTile {

  private final TileAction[] tileActions = new TileAction[3];
  private final transient Board board;

  /**
   * Constructor for the RandomActionTile class.
   *
   * @param tileNumber       The number of the tile on the board.
   * @param onscreenPosition The position of the tile on the screen.
   */
  public RandomActionTile(int tileNumber, int[] onscreenPosition, Board board) {
    this.tileNumber = tileNumber;
    this.onscreenPosition = onscreenPosition;
    this.board = board;

    initializeTileActions(null);
  }

  @Override
  public void performAction(Player player) {
    // initialize the tileAction with a random TileAction
    int randomAction = (int) (Math.random() * 3);
    this.tileAction = tileActions[randomAction];

    tileAction.performAction(player);
  }

  @Override
  public String getTileType() {
    return "RandomActionTile";
  }

  /**
   * Method that initializes the tile actions that can be performed when a player lands on the tile.
   * The tile actions are ReturnToStartAction, RollAgainAction and SwapPlayersAction.
   *
   * @param playerMap a {@link HashMap} containing the players that can be swapped with.
   */
  public void initializeTileActions(HashMap<Integer, Player> playerMap) {
    tileActions[0] = new ReturnToStartAction(board);
    tileActions[1] = new RollAgainAction();
    tileActions[2] = new SwapPlayersAction(playerMap);
  }

  /**
   * Method that returns the {@link TileAction} that was previously performed on this tile.
   *
   * @return the {@link TileAction} (represented as a String) that was performed on this tile.
   */
  public String getTileAction() {
    return tileAction.getClass().getSimpleName();
  }

}
