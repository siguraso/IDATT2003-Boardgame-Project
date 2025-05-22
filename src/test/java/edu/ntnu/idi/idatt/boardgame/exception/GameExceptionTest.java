package edu.ntnu.idi.idatt.boardgame.exception;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.view.window.components.helperComponents.LaddergameHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameExceptionTest {

  @Test
  @DisplayName("Test GameException with message")
  void testGameExceptionWithMessage() {
    String message = "This is a test exception";
    GameException exception = new GameException(message);
    assertEquals(message, exception.getMessage());
    assertNull(exception.getCause());
  }

  @Test
  @DisplayName("Test GameException with message and cause")
  void testGameExceptionWithMessageAndCause() {
    String message = "This is a test exception with cause";
    Throwable cause = new Throwable("This is the cause");
    GameException exception = new GameException(message, cause);
    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }
}