package edu.ntnu.idi.idatt.boardgame.exception;

/**
 * Custom exception class for identifying invalid board configurations in the game. This class
 * extends the {@link GameException} class and represents a custom exception that can be thrown when
 * an invalid board is encountered.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class InvalidBoardException extends GameException {

  /**
   * Default constructor for the InvalidBoardException class.
   *
   * @param message The message to be displayed when the exception is thrown.
   */
  public InvalidBoardException(String message) {
    super(message);
  }

  /**
   * Constructor for the InvalidBoardException class with a cause.
   *
   * @param message The message to be displayed when the exception is thrown.
   * @param cause   The cause of the exception.
   */
  public InvalidBoardException(String message, Throwable cause) {
    super(message, cause);
  }
}
