package edu.ntnu.idi.idatt.boardgame.model.board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTypeTest {

  @Test
  @DisplayName("Tests the accessor for the board file path for the image")
  void testGetFilePath() {
    assertEquals("/Images/boards/ladder_game_special.png",
        BoardType.LADDER_GAME_SPECIAL.getFilePath());
    assertEquals("/Images/boards/ladder_game_regular.png",
        BoardType.LADDER_GAME_REGULAR.getFilePath());
    assertEquals("/Images/boards/ladder_game_json.png", BoardType.LADDER_GAME_JSON.getFilePath());
    assertEquals("/Images/boards/pario_marty.png", BoardType.PARIO_MARTY.getFilePath());
  }

  @Test
  @DisplayName("Test the accessor for the name of the board")
  void testGetBoardName() {
    assertEquals("Ladder Game Special", BoardType.LADDER_GAME_SPECIAL.getBoardName());
    assertEquals("Ladder Game Regular", BoardType.LADDER_GAME_REGULAR.getBoardName());
    assertEquals("Ladder Game .json", BoardType.LADDER_GAME_JSON.getBoardName());
    assertEquals("Pario Marti", BoardType.PARIO_MARTY.getBoardName());
  }

}