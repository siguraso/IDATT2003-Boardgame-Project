package edu.ntnu.idi.idatt.boardgame.model.player;

/**
 * <h1>Enum - PlayerPieces.</h1>
 *
 * <p>Enum for the different player pieces in the game.
 * Just some fun names for the player pieces.</p>
 *
 * @author siguraso & MagnusNaessanGaarder
 * @version 1.0
 * @since 1.0
 */
public enum PlayerPiece {
  // literally just donkey kong
  KONKEY_DONG("konkey_dong.png",
      "Konkey Dong"),
  // gabriel martinelli and mario infused into one
  MARIOTINELLI("mariotinelli.png", "Mariotinelli"),
  // literally just luigi without his hat
  PAUL("paul.png", "Paul"),
  // literally just luigi without his hat with inverted colors
  EVIL_PAUL("evil_paul.png", "Evil Paul"),
  // cloud strife but he is bald
  MY_LOVE("my_love.png", "My Love"),
  // cloud strife but he is bald with a hat
  MY_LOVE_WITH_HAT("my_love_with_hat.png", "My Love (hat)"),
  // literally paul blart mall cop
  PROPELLER_ACCESSORIES("propeller_accessories.png", "Propeller Accessories"),
  // the mogging snowman from the gif
  LOCKED_IN_SNOWMAN("locked_in_snowman.png", "Locked in Snowman"),
  ;

  private final String imageFileName;
  private final String pieceName;
  private static final String IMAGE_PATH_PREFIX = "/Images/player-pieces/";

  /**
   * Constructor for the PlayerPiece enum.
   *
   * @param imageFileName The path to the image of the player piece.
   */
  PlayerPiece(String imageFileName, String pieceName) {
    if (imageFileName == null || pieceName == null) {
      throw new NullPointerException("Image path and pieceName cannot be null.");
    } else if (imageFileName.isEmpty() || pieceName.isEmpty()) {
      throw new IllegalArgumentException("Image path and pieceName cannot be empty.");
    }
    this.imageFileName = imageFileName;
    this.pieceName = pieceName;
  }

  /**
   * Used to get the image path of the player piece.
   *
   * @return The image path of the player piece.
   */
  public String getImagePath() {
    return IMAGE_PATH_PREFIX + imageFileName;
  }

  /**
   * Used to get the name of the player piece.
   *
   * @return The name of the player piece.
   */
  public String getPieceName() {
    return pieceName;
  }

}
