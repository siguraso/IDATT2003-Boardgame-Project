package edu.ntnu.idi.idatt.boardgame.model.player;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerPieceTest {

  @Test
  void getImagePath() {
    PlayerPiece piece = PlayerPiece.KONKEY_DONG;
    String expectedPath = "/Images/player-pieces/konkey_dong.png";
    assertEquals(expectedPath, piece.getImagePath());
  }

  @Test
  void getPieceName() {
    PlayerPiece piece = PlayerPiece.KONKEY_DONG;
    String expectedName = "Konkey Dong";
    assertEquals(expectedName, piece.getPieceName());
  }
}