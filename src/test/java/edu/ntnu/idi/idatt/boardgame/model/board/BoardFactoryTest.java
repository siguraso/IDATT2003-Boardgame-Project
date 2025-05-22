package edu.ntnu.idi.idatt.boardgame.model.board;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.exception.MalformedBoardException;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.TileType;
import java.util.HashMap;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.mockito.MockedStatic;
import edu.ntnu.idi.idatt.boardgame.model.io.board.LadderBoardReaderGson;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.*;
import java.util.Map;

/**
 * Test class for BoardFactory. To run this test, you will need to run it in java 21 or lower, since
 * the mock file reader is not supported in java 22+.
 *
 * <p>Test coverage is not at 100%, since the class uses json files from class path, which we will
 * is not tested here. This also means that the line coverage is low. We know that the file handling
 * works, since it is tested in the LadderBoardReaderGsonTest class.</p>
 */
class BoardFactoryTest {

  @Test
  @DisplayName("Test all of the board types of create board")
  void testCreateBoard() {
    Board regularBoard = BoardFactory.createBoard(BoardType.LADDER_GAME_REGULAR, false, null);
    HashMap<Integer, String> tileTypes = new HashMap<>(regularBoard.getTileTypes());

    IntStream.range(1, 91).forEach(i -> {
      if (!tileTypes.containsKey(i)) {
        fail("Tile " + i + " should be present in the board");
      }
      if (i == 1) {
        assertEquals(TileType.NORMAL.getTileType(), tileTypes.get(i));
      } else if (i == 90) {
        assertEquals(TileType.WINNER.getTileType(), tileTypes.get(i));
      } else if (i == 2 || i == 8 || i == 24 || i == 33 || i == 36 || i == 42 || i == 43 || i == 49
          || i == 56 || i == 64 || i == 65 || i == 68 || i == 74 || i == 87) {
        assertEquals(TileType.LADDER.getTileType(), tileTypes.get(i));
      } else {
        assertEquals(TileType.NORMAL.getTileType(), tileTypes.get(i));
      }
    });

    Board specialBoard = BoardFactory.createBoard(BoardType.LADDER_GAME_SPECIAL, false, null);
    HashMap<Integer, String> specialTileTypes = new HashMap<>(specialBoard.getTileTypes());

    IntStream.range(1, 91).forEach(i -> {
      if (!specialTileTypes.containsKey(i)) {
        fail("Tile " + i + " should be present in the board");
      }
      if (i == 90) {
        assertEquals(TileType.WINNER.getTileType(), specialTileTypes.get(i));
      } else if (i == 3 || i == 10 || i == 43 || i == 85) {
        assertEquals(TileType.RANDOM_ACTION.getTileType(), specialTileTypes.get(i));
      } else if (i == 2 || i == 24 || i == 36 || i == 49
          || i == 64 || i == 74 || i == 87) {
        assertEquals(TileType.LADDER.getTileType(), specialTileTypes.get(i));
      } else if (i == 6 || i == 30 || i == 62 || i == 68 || i == 82) {
        assertEquals(TileType.ROLL_AGAIN.getTileType(), specialTileTypes.get(i));
      } else if (i == 22 || i == 42 || i == 56 || i == 65) {
        assertEquals(TileType.RETURN_TO_START.getTileType(), specialTileTypes.get(i));
      } else {
        assertEquals(TileType.NORMAL.getTileType(), specialTileTypes.get(i));
      }
    });

    Board parioMartyBoard = BoardFactory.createBoard(BoardType.PARIO_MARTY, false, null);
    HashMap<Integer, String> parioTileTypes = new HashMap<>(parioMartyBoard.getTileTypes());

    IntStream.range(1, 36).forEach(i -> {
      if (!parioTileTypes.containsKey(i)) {
        fail("Tile " + i + " should be present in the board");
      }

      if (i == 1) {
        assertEquals(TileType.NORMAL.getTileType(), parioTileTypes.get(i));
      } else if (i == 2 || i == 3 || i == 5 || i == 7 || i == 9 || i == 10 || i == 11 || i == 14
          || i == 15 || i == 18 || i == 19 || i == 20 || i == 22 || i == 23 || i == 26 || i == 27
          || i == 30 || i == 31 || i == 33 || i == 34) {
        assertEquals(TileType.ADD_COINS.getTileType(), parioTileTypes.get(i));
      } else if (i == 13 || i == 21) {
        assertEquals(TileType.MOWSER.getTileType(), parioTileTypes.get(i));
      } else if (i == 24) {
        assertEquals(TileType.RETURN_TO_START.getTileType(), parioTileTypes.get(i));
      } else if (i == 28 || i == 6 || i == 16) {
        assertEquals(TileType.ROLL_AGAIN.getTileType(), parioTileTypes.get(i));
      } else if (i == 17 || i == 35) {
        assertEquals(TileType.RANDOM_ACTION.getTileType(), parioTileTypes.get(i));
      } else {
        assertEquals(TileType.REMOVE_COINS.getTileType(), parioTileTypes.get(i));
      }
    });

  }

  @Test
  @DisplayName("Test custom JSON board creation without file handling")
  void testCreateCustomJsonBoardWithoutFileHandling() {
    // Mock the board reader
    LadderBoardReaderGson mockReader = mock(LadderBoardReaderGson.class);

    try (MockedStatic<LadderBoardReaderGson> mockedReaderClass = mockStatic(
        LadderBoardReaderGson.class)) {
      // When static methods are called on the class, return our desired values
      mockedReaderClass.when(() -> LadderBoardReaderGson.getInstance()).thenReturn(mockReader);

      // Create a valid test board
      Map<Integer, Tile> validTiles = new HashMap<>();

      // Add the first tile as a normal tile
      validTiles.put(1, new NormalTile(1, new int[]{0, 0}));

      // Add some ladder tiles in between
      validTiles.put(2, new LadderTile(2, new int[]{1, 0}, 40));
      validTiles.put(24, new LadderTile(24, new int[]{3, 2}, 5));

      // Add middle tiles as normal tiles
      Map<Integer, Tile> finalValidTiles1 = validTiles;
      IntStream.rangeClosed(3, 89)
          .filter(i -> i != 2 && i != 24)
          .forEach(i -> finalValidTiles1.put(i, new NormalTile(i, new int[]{i % 10, i / 10})));

      // Add the last tile as winner tile
      validTiles.put(90, new WinnerTile(90, new int[]{9, 9}));

      Board validBoard = new Board(validTiles);

      // Setup the mock to return our valid board
      when(mockReader.readBoardFile("valid/path.json", true)).thenReturn(validBoard);

      // Test the valid case
      Board result = BoardFactory.createBoard(BoardType.LADDER_GAME_JSON, true, "valid/path.json");

      // Create a valid test board
      validTiles = new HashMap<>();

      // Add the first tile as a normal tile
      validTiles.put(1, new NormalTile(1, new int[]{0, 0}));

      // Add some ladder tiles in between
      validTiles.put(2, new LadderTile(2, new int[]{1, 0}, 40));
      validTiles.put(24, new LadderTile(24, new int[]{3, 2}, 5));

      // Add middle tiles as normal tiles
      Map<Integer, Tile> finalValidTiles = validTiles;
      IntStream.rangeClosed(3, 89)
          .filter(i -> i != 2 && i != 24)
          .forEach(i -> finalValidTiles.put(i, new NormalTile(i, new int[]{i % 10, i / 10})));

      // Add the last tile as winner tile
      validTiles.put(90, new WinnerTile(90, new int[]{9, 9}));

      validBoard = new Board(validTiles);

      // Setup the mock to return our valid board
      when(mockReader.readBoardFile("valid/path.json", true)).thenReturn(validBoard);

      // Test the valid case
      result = BoardFactory.createBoard(BoardType.LADDER_GAME_JSON, true, "valid/path.json");

      // Verify the board was correctly processed
      assertNotNull(result);
      assertEquals(90, result.tiles().size());
      assertEquals(TileType.NORMAL.getTileType(), result.getTileTypes().get(1));
      assertEquals(TileType.WINNER.getTileType(), result.getTileTypes().get(90));
      assertEquals(TileType.LADDER.getTileType(), result.getTileTypes().get(2));
    }
  }

  @Test
  @DisplayName("Negative test custom JSON board creation with file handling")
  void testCreateCustomJsonBoardWithFileHandling() {
    // mock the board reader
    LadderBoardReaderGson mockReader = mock(LadderBoardReaderGson.class);

    try (MockedStatic<LadderBoardReaderGson> mockedReaderClass = mockStatic(
        LadderBoardReaderGson.class)) {
      // when static methods are called on the class, make the mock return the values
      mockedReaderClass.when(() -> LadderBoardReaderGson.getInstance()).thenReturn(mockReader);

      // different exceptions:
      // not enough tiles
      Map<Integer, Tile> invalidTilesCount = new HashMap<>();
      IntStream.rangeClosed(1, 50).forEach(i ->
          invalidTilesCount.put(i, new NormalTile(i, new int[]{i % 10, i / 10})));
      Board invalidBoardCount = new Board(invalidTilesCount);
      when(mockReader.readBoardFile("invalid/count.json", true)).thenReturn(invalidBoardCount);

      MalformedBoardException countException = assertThrows(MalformedBoardException.class,
          () -> BoardFactory.createBoard(BoardType.LADDER_GAME_JSON, true, "invalid/count.json"));
      assertEquals("Board must have 90 tiles", countException.getMessage());

      // not in order
      Map<Integer, Tile> invalidTilesOrder = new HashMap<>();
      IntStream.rangeClosed(1, 89).forEach(i ->
          invalidTilesOrder.put(i, new NormalTile(i, new int[]{i % 10, i / 10})));
      invalidTilesOrder.put(91, new WinnerTile(91, new int[]{0, 0})); // Out of range tile number
      Board invalidBoardOrder = new Board(invalidTilesOrder);
      when(mockReader.readBoardFile("invalid/order.json", true)).thenReturn(invalidBoardOrder);

      MalformedBoardException orderException = assertThrows(MalformedBoardException.class,
          () -> BoardFactory.createBoard(BoardType.LADDER_GAME_JSON, true, "invalid/order.json"));
      assertEquals("Tiles must be in order from 1 to 90", orderException.getMessage());

      // first tile not normal
      Map<Integer, Tile> invalidFirstTile = new HashMap<>();
      invalidFirstTile.put(1,
          new LadderTile(1, new int[]{0, 0}, 40)); // First tile should be normal
      IntStream.rangeClosed(2, 89).forEach(i ->
          invalidFirstTile.put(i, new NormalTile(i, new int[]{i % 10, i / 10})));
      invalidFirstTile.put(90, new WinnerTile(90, new int[]{9, 9}));
      Board invalidBoardFirstTile = new Board(invalidFirstTile);
      when(mockReader.readBoardFile("invalid/first.json", true)).thenReturn(invalidBoardFirstTile);

      MalformedBoardException firstTileException = assertThrows(MalformedBoardException.class,
          () -> BoardFactory.createBoard(BoardType.LADDER_GAME_JSON, true, "invalid/first.json"));
      assertEquals("First tile must be a normal tile", firstTileException.getMessage());

      // last tile not winner
      Map<Integer, Tile> invalidLastTile = new HashMap<>();
      invalidLastTile.put(1, new NormalTile(1, new int[]{0, 0}));
      IntStream.rangeClosed(2, 89).forEach(i ->
          invalidLastTile.put(i, new NormalTile(i, new int[]{i % 10, i / 10})));
      invalidLastTile.put(90, new NormalTile(90, new int[]{9, 9})); // Last tile should be winner
      Board invalidBoardLastTile = new Board(invalidLastTile);
      when(mockReader.readBoardFile("invalid/last.json", true)).thenReturn(invalidBoardLastTile);

      MalformedBoardException lastTileException = assertThrows(MalformedBoardException.class,
          () -> BoardFactory.createBoard(BoardType.LADDER_GAME_JSON, true, "invalid/last.json"));
      assertEquals("Last tile must be a winner tile", lastTileException.getMessage());
    }
  }
}