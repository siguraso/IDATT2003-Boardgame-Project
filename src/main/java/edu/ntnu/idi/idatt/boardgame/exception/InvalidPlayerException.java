package edu.ntnu.idi.idatt.boardgame.exception;

/**
 * Custom exception class for invalid players in the game. This class extends the
 * {@link GameException} class and represents a custom exception that can be thrown when an invalid
 * player tries to perform an action that they cannot perform.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class InvalidPlayerException extends GameException {

  /**
   * Default constructor for the InvalidPlayerException class.
   *
   * @param message The message to be displayed when the exception is thrown.
   */
  public InvalidPlayerException(String message) {
    super(message);
  }

  /**
   * Constructor for the InvalidPlayerException class with a cause.
   *
   * @param message The message to be displayed when the exception is thrown.
   * @param cause   The cause of the exception.
   */
  public InvalidPlayerException(String message, Throwable cause) {
    super(message, cause);
  }

}
