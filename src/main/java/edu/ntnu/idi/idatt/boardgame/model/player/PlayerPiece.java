package edu.ntnu.idi.idatt.boardgame.model.player;

/**
 * <h1>Enum - PlayerPieces.</h1>
 *
 * <p>Enum for the different player pieces in the game.
 * Just some fun names for the player pieces.</p>
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public enum PlayerPiece {
  // literally just donkey kong
  KONKEY_DONG("file:src/main/resources/Images/player-pieces/konkey_dong.png"),
  // gabriel martinelli and mario infused into one
  MARIOTINELLI("file:src/main/resources/Images/player-pieces/mariotinelli.png"),
  // literally just luigi without his hat
  PAUL("file:src/main/resources/Images/player-pieces/paul.png"),
  // literally just luigi without his hat with inverted colors
  EVIL_PAUL("file:src/main/resources/Images/player-pieces/evil_paul.png"),
  // cloud strife but he is bald
  MY_LOVE("file:src/main/resources/Images/player-pieces/my_love.png"),
  // cloud strife but he is bald with a hat
  MY_LOVE_WITH_HAT("file:src/main/resources/Images/player-pieces/my_love_with_hat.png"),
  // literally paul blart mall cop
  PROPELLER_ACCESSORIES("file:src/main/resources/Images/player-pieces/propeller_accessories.png"),
  // the mogging snowman from the gif
  LOCKED_IN_SNOWMAN("file:src/main/resources/Images/player-pieces/locked_in_snowman.png");

  private final String imagePath;

  /**
   * Constructor for the PlayerPiece enum.
   *
   * @param imagePath The path to the image of the player piece.
   */
  PlayerPiece(String imagePath) {
    this.imagePath = imagePath;
  }

  /**
   * Used to get the image path of the player piece.
   *
   * @return The image path of the player piece.
   */
  public String getImagePath() {
    return imagePath;
  }

}
