package edu.ntnu.idi.idatt.boardgame.controller;

import edu.ntnu.idi.idatt.boardgame.model.board.tile.AddCoinsTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.AddCrownTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.RandomActionTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.SpecialTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.TileType;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.HashSet;
import java.util.Set;

/**
 * Controller class for Pario Marty. This class extends the {@link GameController} class.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class ParioMartyGameController extends GameController {

  private Integer previousCrownTile = null;
  private Integer currentCrownTile = null;
  private int currentTurn = 1;
  private final Set<String> playersThisTurn = new HashSet<>();

  // player that i currently eligible to get a crown
  private Player crownPlayer;

  private Tile currentTile;

  /**
   * Constructor for the ParioMartyGameController class. This constructor initializes the game board
   * and sets the current player.
   *
   * @param playersController The controller object for the players in the game.
   */
  public ParioMartyGameController(PlayersController playersController, boolean useTwoDice) {
    super(playersController, useTwoDice);
  }

  @Override
  public void finishTurn() {
    // check the tile the current player is on
    currentTile = board.tiles().get(playersController.getCurrentPlayer().getPosition());

    // check what typa tile it is, do the action if it is a special tile

    if (!currentTile.getTileType().equals(TileType.NORMAL.getTileType())) {
      if (currentTile.getTileType().equals(TileType.RANDOM_ACTION.getTileType())) {
        ((RandomActionTile) currentTile).setPlayers(playersController.getPlayers());
      }
      if (currentTile.getTileType().equals(TileType.ADD_CROWN.getTileType())) {
        crownPlayer = playersController.getCurrentPlayer();
      } else {
        lastSpecialTile = playersController.getCurrentPlayer().getPosition();
        ((SpecialTile) currentTile).performAction(playersController.getCurrentPlayer());
      }
    }

    playersThisTurn.add(playersController.getCurrentPlayer().getName());

    // if the set of players this turn is equal to the number of players in the game,
    // every player has had their turn, and the total turn count is increased
    if (playersThisTurn.size() == playersController.getPlayers().size()
        && !playersController.getCurrentPlayer()
        .canRollAgain()) {
      currentTurn++;
      playersThisTurn.clear();
    }

    if (playersController.getCurrentPlayer() != null) {
      die.removeObserver(playersController.getCurrentPlayer());
    }

    playersController.nextPlayer();

    die.addObserver(playersController.getCurrentPlayer());

  }

  /**
   * Sets the crown tile on the board. This method randomly selects a tile from the board and
   * replaces it with a crown tile. If there was a previous crown tile, it is replaced with an
   * {@link AddCoinsTile}.
   */
  public void setCrownTile() {
    boolean hasPlacedCrownTile = false;
    previousCrownTile = currentCrownTile;

    while (!hasPlacedCrownTile) {
      currentCrownTile = (int) (Math.random() * board.tiles().size());
      Tile tile = board.tiles().get(currentCrownTile);

      System.out.println("Current tile: " + currentCrownTile);

      if (tile.getTileType().equals(TileType.ADD_COINS.getTileType())) {
        board.tiles().put(currentCrownTile, tile);
        new AddCrownTile(currentCrownTile, tile.getOnscreenPosition());

        hasPlacedCrownTile = true;
      }
    }

    if (previousCrownTile != null) {
      board.tiles().put(previousCrownTile,
          new AddCoinsTile(previousCrownTile,
              board.tiles().get(previousCrownTile).getOnscreenPosition(), 5));
    }

  }

  /**
   * Checks if the player that is eligible to buy a crown wants to buy it. This method should only
   * be called if the current tile is a crown tile.
   *
   * @param buyCrown a boolean indicating if the player wants to buy the crown.
   */
  public void checkCrownPurchase(boolean buyCrown) {
    if (buyCrown && currentTile.getTileType().equals(TileType.ADD_CROWN.getTileType())) {
      try {
        ((SpecialTile) currentTile).performAction(crownPlayer);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    }
  }

  /**
   * Accesses the previous crown tiles placement.
   *
   * @return the previous crown tile placement.
   */
  public Integer getLastCrownTile() {
    return previousCrownTile;
  }

  /**
   * Accesses the current {@link AddCrownTile} placement.
   *
   * @return the current {@link AddCrownTile} tile placement.
   */
  public Integer getCurrentCrownTile() {
    return currentCrownTile;
  }

  /**
   * Accesses the current turn number.
   *
   * @return the current turn number.
   */
  public int getCurrentTurn() {
    return currentTurn;
  }

}
