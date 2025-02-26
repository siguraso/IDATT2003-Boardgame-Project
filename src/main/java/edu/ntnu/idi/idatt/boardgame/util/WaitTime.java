package edu.ntnu.idi.idatt.boardgame.util;

public class WaitTime {

  /**
   * Waits for a given amount of milliseconds.
   *
   * @param ms the amount of milliseconds to wait.
   */
  public static void wait(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      System.out.println("WaitTime time was interupted.");
    }
  }

}
