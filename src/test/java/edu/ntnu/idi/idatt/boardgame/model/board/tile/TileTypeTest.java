package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TileTypeTest {


  @Test
  @DisplayName("Test the getTileType method")
  void testGetTileType() {
    assertEquals("NormalTile", TileType.NORMAL.getTileType());
    assertEquals("LadderTile", TileType.LADDER.getTileType());
    assertEquals("RandomActionTile", TileType.RANDOM_ACTION.getTileType());
    assertEquals("RollAgainTile", TileType.ROLL_AGAIN.getTileType());
    assertEquals("ReturnToStartTile", TileType.RETURN_TO_START.getTileType());
    assertEquals("WinnerTile", TileType.WINNER.getTileType());
    assertEquals("AddCoinsTile", TileType.ADD_COINS.getTileType());
    assertEquals("RemoveCoinsTile", TileType.REMOVE_COINS.getTileType());
    assertEquals("AddCrownTile", TileType.ADD_CROWN.getTileType());
    assertEquals("MowserTile", TileType.MOWSER.getTileType());
  }
}