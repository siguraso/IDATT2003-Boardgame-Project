package edu.ntnu.idi.idatt.boardgame.exception;

/**
 * Custom exception class for the game. This class extends the {@link RuntimeException} class and
 * represents a custom exception that can be thrown in the game.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class GameException extends RuntimeException {

  /**
   * Default constructor for the GameException class.
   *
   * @param message The message to be displayed when the exception is thrown.
   */
  public GameException(String message) {
    super(message);
  }

  /**
   * Constructor for the GameException class with a cause.
   *
   * @param message The message to be displayed when the exception is thrown.
   * @param cause   The cause of the exception.
   */
  public GameException(String message, Throwable cause) {
    super(message, cause);
  }

}
