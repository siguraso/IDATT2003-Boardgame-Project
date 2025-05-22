package edu.ntnu.idi.idatt.boardgame.exception;

/**
 * Custom exception class for invalid tiles of any type in the game. This class extends the
 * {@link GameException} class and represents a custom exception that can be thrown when an invalid
 * tile is encountered.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class InvalidTileException extends GameException {

  /**
   * Default constructor for the InvalidTileException class.
   *
   * @param message The message to be displayed when the exception is thrown.
   */
  public InvalidTileException(String message) {
    super(message);
  }

  /**
   * Constructor for the InvalidTileException class with a cause.
   *
   * @param message The message to be displayed when the exception is thrown.
   * @param cause   The cause of the exception.
   */
  public InvalidTileException(String message, Throwable cause) {
    super(message, cause);
  }
}
