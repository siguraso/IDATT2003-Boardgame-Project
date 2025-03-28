package edu.ntnu.idi.idatt.boardgame.controller;

import edu.ntnu.idi.idatt.boardgame.model.dice.Die;
import edu.ntnu.idi.idatt.boardgame.model.observerPattern.BoardGameObserver;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * <h1>Class - GameController.</h1>
 *
 * <p>A controller-class to controll the flow of the game</p>
 *
 * @author Magnus Næssan Gaarder
 * @version 1.0
 * @see BoardGameObserver
 * @since 1.0
 */
public class GameController implements BoardGameObserver {
  private final Die dice;
  private final Player player;

  public GameController(Die dice, Player player) {
    this.dice = dice;
    this.player = player;
    dice.addObserver(this);
  }

  public void rollDice() {
    dice.throwDie();
  }

  @Override
  public void update(int i) {
    player.move(i);
  }
}
