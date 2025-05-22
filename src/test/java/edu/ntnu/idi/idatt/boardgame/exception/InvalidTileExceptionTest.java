package edu.ntnu.idi.idatt.boardgame.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InvalidTileExceptionTest {

  @Test
  @DisplayName("Test InvalidTileException with message")
  void testInvalidTileExceptionWithMessage() {
    String message = "This is a test exception";
    InvalidTileException exception = new InvalidTileException(message);
    assertEquals(message, exception.getMessage());
    assertNull(exception.getCause());
  }

  @Test
  @DisplayName("Test InvalidTileException with message and cause")
  void testInvalidTileExceptionWithMessageAndCause() {
    String message = "This is a test exception with cause";
    Throwable cause = new Throwable("This is the cause");
    InvalidTileException exception = new InvalidTileException(message, cause);
    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }
}