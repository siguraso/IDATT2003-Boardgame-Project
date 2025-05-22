package edu.ntnu.idi.idatt.boardgame.model.player;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlayerPieceTest {

  @Test
  @DisplayName("Test the get image path method")
  void getImagePath() {
    PlayerPiece piece = PlayerPiece.KONKEY_DONG;
    String expectedPath = "/Images/player-pieces/konkey_dong.png";
    assertEquals(expectedPath, piece.getImagePath());
  }

  @Test
  @DisplayName("Test the get piece name method")
  void getPieceName() {
    PlayerPiece piece = PlayerPiece.KONKEY_DONG;
    String expectedName = "Konkey Dong";
    assertEquals(expectedName, piece.getPieceName());
  }
}