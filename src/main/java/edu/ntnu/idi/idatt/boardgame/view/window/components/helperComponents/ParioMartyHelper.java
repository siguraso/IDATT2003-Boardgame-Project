package edu.ntnu.idi.idatt.boardgame.view.window.components.helperComponents;

import java.util.Objects;

/**
 * A class representing a helper window for the Pario Marty game. This window provides information
 * about the game, including its objective, contents, rules, and how to play.
 *
 * @author MagnusNaessanGaarder & siguraso
 * @version 1.0
 * @since 1.0
 */
public class ParioMartyHelper extends HelperWindow {

  /**
   * Constructor for the ParioMartyHelper class. This constructor initializes the window and its
   */
  public ParioMartyHelper() {
    // Prevent instantiation
    super();
    init();
  }

  @Override
  public void init() {
    super.setTitle("Pario Marty - Help");
    super.setSubTitle("Explanation of the \"Pario Marty\" game:");
    super.setImage(Objects.requireNonNull(this.getClass().getResourceAsStream(
        "/Images/boards/pario_marty.png")));
    super.setDescription("""
        OBJECTIVE:
        The goal of the game is to gain the most crowns and coins before the
        15 turns has passed.
        
        CONTENTS:
        The game consists of a board with 35 tiles, 1 or 2 6-sided die(s), and
        player-pieces
        
        RULES:
        The winner is determined by the amount of crowns and coins each player has. The player
        with the most crowns wins the game. If certain players have the same amount of crowns, the
        winner will be determined by the most amount of coins those players have.
        
        HOW TO PLAY:
        The players are placed on the start square on the board, labeled "Start". Players then
        take turns rolling a die and moving their piece forward according to the value(es) on the
        die(s). To throw the die(s), the player must press the button "Roll" under the die(s)
        picture(s) on the sidebar to right. The player then moves their piece forward the number
        of squares indicated by the die(s). When moving past the last square, the player moves
        the remaining number of spaces from the first spaces on the board.
        
        In addition to the ladders, there are also special tiles on the board. If a player lands
        on a special tile, an event corresponding to the special tile occurs. If a player lands
        on the Roll Again tile, they get to roll again. The RollAgain tile is indicated with a
        Blue background with a white die on it. If a player lands on the Return To Start tile,
        they must return to the starting square. The ReturnToStart tile is indicated with a
        yellow background with a black arrow on it. If a player lands on the Mystery tile, a
        random event occurs. The Mystery tile is indicated with a purple background with a
        question mark on it. The random event is chosen from a list of events.
        
        To win the game, a player collect more crowns and stars than the other players before
        the amount of turns reaches zero. If no players have any crowns the amount of coins
        determines who wins. If two or more players have the same amount of crowns and coins, 
        all these players win the game.""");
    super.init();
  }

}
