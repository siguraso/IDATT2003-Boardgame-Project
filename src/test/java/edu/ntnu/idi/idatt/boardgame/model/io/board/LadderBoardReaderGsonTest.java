package edu.ntnu.idi.idatt.boardgame.model.io.board;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import com.google.gson.JsonParser;

/**
 * Test class for LadderBoardReaderGson, this class NEEDS to be run on Java 22 or under, since the
 * mock file reader is not supported in Java 23+.
 *
 * <p>The test coverage is only at 83%, since the class implements a methods from
 * JsonDeserializer, which we will not be testing here</p>
 *
 * <p>Also, line coverage is quite low, seeing as it does not test the events where it checks the
 * pre-written json files, as it would require file handling calls. It does not matter, since the
 * tiles of a board are serialized the same way either way, and the only real difference comes from
 * which files are read.</p>
 */
@ExtendWith(MockitoExtension.class)
class LadderBoardReaderGsonTest {

  private LadderBoardReaderGson ladderBoardReaderGson;

  @BeforeEach
  void setUp() {
    ladderBoardReaderGson = new LadderBoardReaderGson();
  }

  @Test
  @DisplayName("Test the constructor of LadderBoardReaderGson")
  void testConstructor() {
    try {
      new LadderBoardReaderGson();
    } catch (Exception e) {
      fail("Constructor should not throw an exception");
    }
  }

  @Test
  @DisplayName("Positive test the readBoardFile method with custom JSON")
  void testReadCustomBoard() {
    try (
        MockedConstruction<FileReader> mockedFileReader = mockConstruction(FileReader.class);
        MockedStatic<JsonParser> jsonParserMock = mockStatic(JsonParser.class)
    ) {
      JsonObject rootObject = new JsonObject();
      JsonArray tilesArray = createTestTilesJsonArray();
      rootObject.add("tiles", tilesArray);

      // mock file-reader and JsonParser
      jsonParserMock.when(() -> JsonParser.parseReader(any(FileReader.class)))
          .thenReturn(rootObject);

      // create a board with a fake json file path
      Board board = ladderBoardReaderGson.readBoardFile("DUMMYYEAH60DAYSS.json",
          true);

      assertNotNull(board, "Board should not be null");
      assertEquals("NormalTile", board.getTileTypes().get(1));
      assertEquals("LadderTile", board.getTileTypes().get(2));
      assertEquals("RandomActionTile", board.getTileTypes().get(3));
      assertEquals("RollAgainTile", board.getTileTypes().get(6));
      assertEquals("ReturnToStartTile", board.getTileTypes().get(22));
      assertEquals("WinnerTile", board.getTileTypes().get(90));
    }
  }

  @Test
  @DisplayName("Negative test the readBoardFile method")
  void testNegativeReadBoard() {
    try (MockedConstruction<FileReader> mockedConstruction = mockConstruction(
        FileReader.class, (mock, context) -> {
          throw new FileNotFoundException("File not found");
        })) {

      assertThrows(RuntimeException.class, () ->
          ladderBoardReaderGson.readBoardFile("YEAHYEAH60DAYSSS.json", true));
    }

    try (MockedStatic<JsonParser> jsonParserMock = mockStatic(JsonParser.class)) {
      jsonParserMock.when(() -> JsonParser.parseReader(any(FileReader.class)))
          .thenThrow(new RuntimeException(
              "Cannot invoke \"com.google.gson.JsonElement.getAsJsonArray()\""));

      assertThrows(RuntimeException.class, () ->
              ladderBoardReaderGson.readBoardFile("malformed.json", true),
          "Should throw RuntimeException when JSON is malformed");
    } catch (RuntimeException e) {
      fail("Should not throw RuntimeException");
    }
  }

  private JsonArray createTestTilesJsonArray() {
    // Create a json array the same way as the one in the writer class
    JsonArray array = new JsonArray();

    // normal tile
    JsonObject normalTile = new JsonObject();
    normalTile.addProperty("tileNumber", 1);
    normalTile.addProperty("tileType", "NORMAL");
    JsonArray normalPos = new JsonArray();
    normalPos.add(10);
    normalPos.add(10);
    normalTile.add("onscreenPosition", normalPos);
    array.add(normalTile);

    // ladder tile
    JsonObject ladderTile = new JsonObject();
    ladderTile.addProperty("tileNumber", 2);
    ladderTile.addProperty("tileType", "LADDER");
    ladderTile.addProperty("destinationTileNumber", 10);
    JsonArray ladderPos = new JsonArray();
    ladderPos.add(0);
    ladderPos.add(0);
    ladderTile.add("onscreenPosition", ladderPos);
    array.add(ladderTile);

    // random action tile
    JsonObject randomActionTile = new JsonObject();
    randomActionTile.addProperty("tileNumber", 3);
    randomActionTile.addProperty("tileType", "RANDOM_ACTION");
    JsonArray randomActionPos = new JsonArray();
    randomActionPos.add(0);
    randomActionPos.add(0);
    randomActionTile.add("onscreenPosition", randomActionPos);
    array.add(randomActionTile);

    // roll again tile
    JsonObject rollAgainTile = new JsonObject();
    rollAgainTile.addProperty("tileNumber", 6);
    rollAgainTile.addProperty("tileType", "ROLL_AGAIN");
    JsonArray rollAgainPos = new JsonArray();
    rollAgainPos.add(0);
    rollAgainPos.add(0);
    rollAgainTile.add("onscreenPosition", rollAgainPos);
    array.add(rollAgainTile);

    // return to start tile
    JsonObject returnToStartTile = new JsonObject();
    returnToStartTile.addProperty("tileNumber", 22);
    returnToStartTile.addProperty("tileType", "RETURN_TO_START");
    JsonArray returnToStartPos = new JsonArray();
    returnToStartPos.add(0);
    returnToStartPos.add(0);
    returnToStartTile.add("onscreenPosition", returnToStartPos);
    array.add(returnToStartTile);

    // winner tile
    JsonObject winnerTile = new JsonObject();
    winnerTile.addProperty("tileNumber", 90);
    winnerTile.addProperty("tileType", "WINNER");
    JsonArray winnerPos = new JsonArray();
    winnerPos.add(0);
    winnerPos.add(0);
    winnerTile.add("onscreenPosition", winnerPos);
    array.add(winnerTile);

    return array;
  }
}