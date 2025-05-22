package edu.ntnu.idi.idatt.boardgame.exception;

/**
 * Custom exception class for encountering malformed board configurations, especially when loading
 * from a file. This class extends the {@link GameException} class and represents a custom exception
 * that can be thrown when a board is malformed.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class MalformedBoardException extends GameException {

  /**
   * Default constructor for the MalformedBoardException class.
   *
   * @param message The message to be displayed when the exception is thrown.
   */
  public MalformedBoardException(String message) {
    super(message);
  }

  /**
   * Constructor for the MalformedBoardException class with a cause.
   *
   * @param message The message to be displayed when the exception is thrown.
   * @param cause   The cause of the exception.
   */
  public MalformedBoardException(String message, Throwable cause) {
    super(message, cause);
  }
}
