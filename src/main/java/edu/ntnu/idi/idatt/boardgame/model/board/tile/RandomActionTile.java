package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.MoveToRandomTileAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.ReturnToStartAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.RollAgainAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.SwapPlayersAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.TileAction;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Tile that performs a random action when a player lands on it.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class RandomActionTile extends SpecialTile {

  private final TileAction[] tileActions = new TileAction[4];
  private final transient Board board;
  private ArrayList<Player> players;

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

    initializeTileActions();
  }

  /**
   * Set the players that are currently in the game. Used to perform the swap action.
   *
   * @param players an {@link ArrayList} of {@link Player} instances that are currently in the
   *                game.
   */
  public void setPlayers(ArrayList<Player> players) {
    this.players = players;

    Arrays.stream(tileActions).forEach(tileAction -> {
      if (tileAction.getClass().getSimpleName().equals("SwapPlayersAction")) {
        ((SwapPlayersAction) tileAction).setPlayers(players);
      }
    });
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
   */
  public void initializeTileActions() {
    tileActions[0] = new ReturnToStartAction(board);
    tileActions[1] = new RollAgainAction();
    tileActions[2] = new SwapPlayersAction();
    tileActions[3] = new MoveToRandomTileAction(board);
  }

  /**
   * Method that returns the {@link TileAction} that was previously performed on this tile.
   *
   * @return the {@link TileAction} (represented as a String) that was performed on this tile.
   */
  public String getTileAction() {
    return tileAction.getClass().getSimpleName();
  }

  /**
   * Accesses the last {@link Player} that was swapped with using the {@link SwapPlayersAction}.
   *
   * @return the {@link Player} that was swapped with.
   */
  public Player getPlayerToSwapWith() {
    if (((SwapPlayersAction) tileActions[2]).getPlayerToSwapWith() == null) {
      throw new NullPointerException("No players have been swapped yet.");
    }

    return ((SwapPlayersAction) tileActions[2]).getPlayerToSwapWith();
  }

}
