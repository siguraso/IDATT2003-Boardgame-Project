package edu.ntnu.idi.idatt.boardgame.view.window.components.helperComponents;


import java.util.Objects;

/**
 * A class representing a helper window for the Ladder Game. This window provides information about
 * the game, including its objective, contents, rules, and how to play.
 *
 * @author MagnusNaessanGaarder & siguraso
 * @version 1.0
 * @since 1.0
 */
public class LaddergameHelper extends HelperWindow {

  /**
   * Constructor for the LaddergameHelper class. This constructor initializes the window and its
   *
   * @param version the version of the game, either "Regular" or "Special"
   */
  public LaddergameHelper(String version) {
    super();
    if (version == null || version.isEmpty()) {
      throw new IllegalArgumentException("Version cannot be null or empty");
    }
    this.init(version);
    super.setScene(this);
  }

  private void init(String version) {
    switch (version) {
      case "Regular" -> {
        super.setTitle("Ladder Game - Help");
        super.setSubTitle("Explanation of the \"Ladder Game\", Regular version:");
        super.setImage(Objects.requireNonNull(this.getClass().getResourceAsStream(
            "/Images/boards/ladder_game_regular.png")));
        super.setDescription("""
            OBJECTIVE:
            The goal of the game is to reach the final square from the starting square
            on the board before anyone else.
            
            CONTENTS:
            The game consists of a regular board with 90 squares, 1 or 2 6-sided die(s), and
            player-pieces.
            
            HOW TO PLAY:
            The players are placed on the first square on the board, labeled "1". Players then take
            turns rolling a die and moving their piece forward according to the value(es) on the
            die(s). To throw the die(s), the player must press the button "Roll" under the die(s)
            picture(s) on the sidebar to right. The player then moves their piece forward the number
            of squares indicated by the die(s).
            
            The board has ladders that connect different squares. If a player lands on a space with
            a ladder, they can go to the place the ladder is connected to. However, the end-squares
            that the ladders transport you to cannot transport a player across the ladder the other
            way around. The ladder tiles are indicated with a green or a red background. The green
            ladder-tiles are the ones that transport the player closer to the goal, while the red
            ladder-tiles are the ones that transport the player to further from the board.
            
            To win the game, a player must land EXACTLY on the last square. If a player rolls a
            number that would take them past the last square, they must move back the number of
            spaces that would take them past the last square.""");
      }
      case "Special" -> {
        super.setTitle("Ladder Game - Help");
        super.setSubTitle("Explanation of the \"Ladder Game\", Special version:");
        super.setImage(Objects.requireNonNull(this.getClass().getResourceAsStream(
            "/Images/boards/ladder_game_special.png")));
        super.setDescription("""
            OBJECTIVE:
            The goal of the game is to reach the final square from the starting square
            on the board before anyone else.
            
            CONTENTS:
            The game consists of a special board with 90 squares, 1 or 2 6-sided die(s), and
            player-pieces.
            
            HOW TO PLAY:
            The players are placed on the first square on the board, labeled "1". Players then take
            turns rolling a die and moving their piece forward according to the value(es) on the
            die(s). To throw the die(s), the player must press the button "Roll" under the die(s)
            picture(s) on the sidebar to right. The player then moves their piece forward the number
            of squares indicated by the die(s).
            
            The board has ladders that connect different squares. If a player lands on a space with
            a ladder, they can go to the place the ladder is connected to. However, the end-squares
            that the ladders transport you to cannot transport a player across the ladder the other
            way around. The ladder tiles are indicated with a green or a red background. The green
            ladder-tiles are the ones that transport the player closer to the goal, while the red
            ladder-tiles are the ones that transport the player to further from the board.
            
            In addition to the ladders, there are also special tiles on the board. If a player lands
            on a special tile, an event corresponding to the special tile occurs. If a player lands
            on the Roll Again tile, they get to roll again. The RollAgain tile is indicated with a
            Blue background with a white die on it. If a player lands on the Return To Start tile,
            they must return to the starting square. The ReturnToStart tile is indicated with a
            yellow background with a black arrow on it. If a player lands on the Mystery tile, a
            random event occurs. The Mystery tile is indicated with a purple background with a
            question mark on it. The random event is chosen from a list of events.
            
            To win the game, a player must land EXACTLY on the last square. If a player rolls a
            number that would take them past the last square, they must move back the number of
            spaces that would take them past the last square.""");
      }
      default -> throw new IllegalArgumentException("Invalid version: " + version);
    }
    super.init();
  }
}
