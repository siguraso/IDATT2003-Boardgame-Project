package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.LadderAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.ReturnToStartAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.RollAgainAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.SwapPlayersAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.TileAction;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.HashMap;

public class RandomActionTile extends SpecialTile {

  TileAction[] tileActions = new TileAction[3];
  HashMap<Integer, Player> playerMap = new HashMap<>();
  private final Board board;

  /**
   * Constructor for the RandomActionTile class.
   *
   * @param tileNumber       The number of the tile on the board.
   * @param onscreenPosition The position of the tile on the screen.
   */
  public RandomActionTile(int tileNumber, int[] onscreenPosition, Board board,
      HashMap<Integer, Player> playerMap) {
    this.tileNumber = tileNumber;
    this.onscreenPosition = onscreenPosition;
    this.board = board;
    this.playerMap = playerMap;

    initializeTileActions();
  }

  @Override
  public void performAction(Player player) {
    // initialize the tileAction with a random TileAction
    int randomAction = (int) (Math.random() * 3);
    this.tileAction = tileActions[randomAction];

    tileAction.performAction(player);
  }

  // method to initialize the tileActions array.
  private void initializeTileActions() {
    tileActions[0] = new ReturnToStartAction(board);
    tileActions[1] = new RollAgainAction();    // TODO: make this work after implementing the roll again action
    tileActions[2] = new SwapPlayersAction(this.playerMap);
  }

}
