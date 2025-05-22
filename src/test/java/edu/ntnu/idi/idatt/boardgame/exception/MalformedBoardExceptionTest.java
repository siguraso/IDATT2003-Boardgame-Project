package edu.ntnu.idi.idatt.boardgame.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MalformedBoardExceptionTest {

  @Test
  @DisplayName("Test MalformedBoardException with message")
  void testMalformedBoardExceptionWithMessage() {
    String message = "This is a test exception";
    MalformedBoardException exception = new MalformedBoardException(message);
    assertEquals(message, exception.getMessage());
    assertNull(exception.getCause());
  }

  @Test
  @DisplayName("Test MalformedBoardException with message and cause")
  void testMalformedBoardExceptionWithMessageAndCause() {
    String message = "This is a test exception with cause";
    Throwable cause = new Throwable("This is the cause");
    MalformedBoardException exception = new MalformedBoardException(message, cause);
    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }

}