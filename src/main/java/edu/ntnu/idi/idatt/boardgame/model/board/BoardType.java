package edu.ntnu.idi.idatt.boardgame.model.board;

/**
 * Enum for the different types of boards that can be created.
 *
 * <p>LADDER_GAME: A board for the game Snakes and Ladders.</p>
 *
 * <p>PARIO_MARTY: A board for the Pario Marti game.</p>
 *
 * <p>There are different types of boards that can be created for each game,
 * which are represented by their game name, then the type of board that is created for that game.
 * </p>
 *
 * <p>For instance, the Ladder Game has a board that includes special tiles that do different
 * special actions which is represented by the enum value LADDER_GAME_SPECIAL, while there is also a
 * board without these special tiles which is represented by the enum value LADDER_GAME_REGULAR.
 * </p>
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public enum BoardType {
  LADDER_GAME_SPECIAL() {
    @Override
    public String getBoardName() {
      return "Ladder Game Special";
    }
  },
  LADDER_GAME_REGULAR() {
    @Override
    public String getBoardName() {
      return "Ladder Game Regular";
    }
  },
  LADDER_GAME_JSON() {
    @Override
    public String getBoardName() {
      return "Ladder Game .json";
    }
  },
  PARIO_MARTI() {
    @Override
    public String getBoardName() {
      return "Pario Marti";
    }
  };

  /**
   * Returns the path to the image file for the corresponding board type.
   *
   * @return String containing the path to the image file.
   */
  public String getFilePath() {
    return "/Images/boards/" + this.name().toLowerCase() + ".png";
  }

  /**
   * Returns the name of the corresponding board type.
   *
   * @return String containing the name of the corresponding board type.
   */
  public abstract String getBoardName();
}
