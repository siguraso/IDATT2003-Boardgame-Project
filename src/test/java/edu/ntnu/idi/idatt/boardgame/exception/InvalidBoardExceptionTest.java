package edu.ntnu.idi.idatt.boardgame.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InvalidBoardExceptionTest {

  @Test
  @DisplayName("Test InvalidBoardException with message")
  void testInvalidBoardExceptionWithMessage() {
    String message = "This is a test exception";
    InvalidBoardException exception = new InvalidBoardException(message);
    assertEquals(message, exception.getMessage());
    assertNull(exception.getCause());
  }

  @Test
  @DisplayName("Test InvalidBoardException with message and cause")
  void testInvalidBoardExceptionWithMessageAndCause() {
    String message = "This is a test exception with cause";
    Throwable cause = new Throwable("This is the cause");
    InvalidBoardException exception = new InvalidBoardException(message, cause);
    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }
}