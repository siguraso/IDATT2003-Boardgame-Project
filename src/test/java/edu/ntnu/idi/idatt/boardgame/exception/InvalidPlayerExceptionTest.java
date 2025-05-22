package edu.ntnu.idi.idatt.boardgame.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InvalidPlayerExceptionTest {

  @Test
  @DisplayName("Test InvalidPlayerException with message")
  void testInvalidPlayerExceptionWithMessage() {
    String message = "This is a test exception";
    InvalidPlayerException exception = new InvalidPlayerException(message);
    assertEquals(message, exception.getMessage());
    assertNull(exception.getCause());
  }

  @Test
  @DisplayName("Test InvalidPlayerException with message and cause")
  void testInvalidPlayerExceptionWithMessageAndCause() {
    String message = "This is a test exception with cause";
    Throwable cause = new Throwable("This is the cause");
    InvalidPlayerException exception = new InvalidPlayerException(message, cause);
    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }

}