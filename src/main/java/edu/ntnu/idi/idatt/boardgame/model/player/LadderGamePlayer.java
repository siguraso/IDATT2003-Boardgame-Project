package edu.ntnu.idi.idatt.boardgame.model.player;


/**
 * A player in the Ladder Game. This class extends the {@link Player} class and represents a player
 * in the Ladder Game.
 *
 * @author siguraso & MagnusNaessanGaarder
 * @version 1.0
 * @since 1.0
 */
public class LadderGamePlayer extends Player {

  /**
   * Constructor for the LadderGamePlayer class.
   *
   * @param name  The name of the player.
   * @param piece The piece that the player uses on the board, as defined in the {@link PlayerPiece}
   *              enum.
   */
  public LadderGamePlayer(String name, PlayerPiece piece) {
    super(name, piece);
  }

  @Override
  public void handleLadderAction(int destination) {
    moveTo(destination);
  }
}
