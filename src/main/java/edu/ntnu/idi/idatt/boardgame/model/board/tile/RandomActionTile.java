package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardType;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.MoveToRandomTileAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.ReturnToStartAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.RollAgainAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.SwapPlayersAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.TileAction;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.List;
import java.util.Random;

/**
 * Tile that performs a random action when a player lands on it.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class RandomActionTile extends SpecialTile {

  private final transient TileAction[] tileActions = new TileAction[4];
  private int randomIndex;
  private final transient Random random = new Random();
  private final transient BoardType boardType;

  /**
   * Constructor for the RandomActionTile class.
   *
   * @param tileNumber       The number of the tile on the board.
   * @param onscreenPosition The position of the tile on the screen.
   */
  public RandomActionTile(int tileNumber, int[] onscreenPosition, BoardType boardType) {
    super(tileNumber, onscreenPosition);

    this.tileType = TileType.RANDOM_ACTION;
    this.boardType = boardType;

    initializeTileActions();
  }

  /**
   * Set the players that are currently in the game. Used to perform the swap action.
   *
   * @param players an {@link List} of {@link Player} instances that are currently in the game.
   */
  public void setPlayers(List<Player> players) {
    try {
      ((SwapPlayersAction) tileActions[2]).setPlayers(players);

    } catch (NullPointerException e) {
      throw new NullPointerException(e.getMessage());

    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());

    }
  }

  @Override
  public void performAction(Player player) {
    try {
      // initialize the tileAction with a random TileAction
      randomIndex = random.nextInt(0, tileActions.length);
      this.tileAction = tileActions[randomIndex];

      tileAction.performAction(player);
    } catch (NullPointerException e) {
      throw new NullPointerException(e.getMessage());
    }
  }

  private void initializeTileActions() {
    tileActions[0] = new ReturnToStartAction();
    tileActions[1] = new RollAgainAction();
    tileActions[2] = new SwapPlayersAction();
    tileActions[3] = new MoveToRandomTileAction(boardType);
  }

  /**
   * Method that returns the {@link TileAction} that was previously performed on this tile.
   *
   * @return the {@link TileAction} (represented as an integer) that was performed on this tile.
   */
  public int getTileAction() {
    if (tileAction == null) {
      throw new NullPointerException("No tile action has been performed yet.");
    }

    return randomIndex;
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
