package edu.ntnu.idi.idatt.boardgame.model.io.board;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.NormalTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardWriterGsonTest {

  private BoardWriterGson boardWriterGson;
  private BoardFileReader boardFileReader;

  @BeforeEach
  void setUp() {
    boardWriterGson = new BoardWriterGson();
    boardFileReader = new BoardReaderGson();
  }

  @Test
  void testWriteBoard() {
    // Positive tests
    String filePath = "src/test/resources/JSON/";

    HashMap<Integer, Tile> tiles = new HashMap<>();
    tiles.put(1, new NormalTile(1, new int[]{0, 0}));
    tiles.put(90, new NormalTile(90, new int[]{0, 0}));

    Board board = new Board(tiles);

    try {
      boardWriterGson.writeBoardFile(board, filePath + "TestBoard2.json");

      Board board2 = boardFileReader.readBoardFile(filePath + "TestBoard2.json", true);

      board.tiles().keySet().forEach(key -> {
        assertEquals(board.getTileTypes().get(key), board2.getTileTypes().get(key));
      });


    } catch (Exception e) {
      fail("Should not throw an exception");
    }
  }
}