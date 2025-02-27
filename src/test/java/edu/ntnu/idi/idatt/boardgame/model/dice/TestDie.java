package edu.ntnu.idi.idatt.boardgame.model.dice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TestDie {

  Die die;
  Die die2;

  @BeforeEach
  @Test
  void setUp() {
    die = new Die(6);
    die2 = new Die(10);
  }

  @Test
  void testThrowDie() {
    try {
      int throwResult = die.throwDie();
      assertTrue(throwResult >= 1 && throwResult <= 6);
      throwResult = die2.throwDie();
      assertTrue(throwResult >= 1 && throwResult <= 10);
    } catch (Exception e) {
      fail("The test failed as the class threw an exception.");
    }

  }

  @Test
  @DisplayName("Tests the class getter methods")
  void testGetters() {
    try {
      die.throwDie();
      die2.throwDie();
      assertTrue(die.getCurrentThrow() >= 1 && die.getCurrentThrow() <= 6);
      assertTrue(die2.getCurrentThrow() >= 1 && die2.getCurrentThrow() <= 10);
    } catch (Exception e) {
      fail("The test failed as the class threw an exception.");
    }
  }
}