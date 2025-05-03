package edu.ntnu.idi.idatt.boardgame.model.io.board;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.LadderTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.NormalTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.RandomActionTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.ReturnToStartTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.RollAgainTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.TileType;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.WinnerTile;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Objects;

/**
 * A class that reads a board from a file in JSON format using the Gson library.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class BoardReaderGson implements BoardFileReader, JsonDeserializer<Tile> {

  private final Gson gson;
  private Board board;

  /**
   * Constructor for the BoardReaderGson class. Reads a board from a JSON file in the GSON format.
   * The file contains a JSON array of tiles, where each tile is represented as a JSON object, which
   * gets translated into a {@link Board} object.
   */
  public BoardReaderGson() {
    gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(Tile.class, this)
        .create();
  }

  @Override
  public Board readBoardFile(String filePath, boolean isCustomJson) {
    HashMap<Integer, Tile> tiles = new HashMap<>();
    board = new Board(tiles);

    // If it is a custom JSON file, use the file path as is; otherwise, get the resource path
    if (!isCustomJson) {
      try {
        // Get the resource as a stream
        InputStream resourceStream = this.getClass().getResourceAsStream(filePath);
        if (resourceStream == null) {
          throw new RuntimeException("Resource not found: " + filePath);
        }

        // Use InputStreamReader to read the resource
        try (InputStreamReader reader = new InputStreamReader(resourceStream)) {
          JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonObject().get("tiles")
              .getAsJsonArray();

          // Create JSON elements in the JSON array based on the tile
          jsonArray.forEach(jsonElement -> {
            Tile tile = gson.fromJson(jsonElement, Tile.class);
            tiles.put(tile.getTileNumber(), tile);
          });

          return board;
        }
      } catch (IOException e) {
        throw new RuntimeException("An Unexpected IOException Occurred: " + e.getMessage());
      }
    } else {
      // Handle custom JSON files as before
      try (FileReader fileReader = new FileReader(filePath)) {
        JsonArray jsonArray = JsonParser.parseReader(fileReader).getAsJsonObject().get("tiles")
            .getAsJsonArray();

        jsonArray.forEach(jsonElement -> {
          Tile tile = gson.fromJson(jsonElement, Tile.class);
          tiles.put(tile.getTileNumber(), tile);
        });

        return board;
      } catch (IOException e) {
        if (e.getMessage().contains("No such file or directory")) {
          throw new RuntimeException("File not found: " + filePath);
        }
        throw new RuntimeException("An Unexpected IOException Occurred: " + e.getMessage());
      }
    }
  }

  // method to deserialize a tile from a json element
  @Override
  public Tile deserialize(JsonElement jsonElement, Type type,
      JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {

    // create a json object from the given json element
    JsonObject jsonObject = jsonElement.getAsJsonObject();

    // get the tile type from the json object
    TileType tileType = TileType.valueOf(jsonObject.get("tileType").getAsString());

    int tileNumber = jsonObject.get("tileNumber").getAsInt();
    int[] onscreenPosition = new int[2];
    onscreenPosition[0] = jsonObject.get("onscreenPosition").getAsJsonArray().get(0).getAsInt();
    onscreenPosition[1] = jsonObject.get("onscreenPosition").getAsJsonArray().get(1).getAsInt();

    return switch (tileType) {
      case NORMAL -> new NormalTile(tileNumber, onscreenPosition);
      case LADDER -> new LadderTile(tileNumber, onscreenPosition,
          jsonObject.get("destinationTileNumber").getAsInt());
      case RETURN_TO_START -> new ReturnToStartTile(tileNumber, onscreenPosition);
      case RANDOM_ACTION -> new RandomActionTile(tileNumber, onscreenPosition, board);
      case WINNER -> new WinnerTile(tileNumber, onscreenPosition);
      case ROLL_AGAIN -> new RollAgainTile(tileNumber, onscreenPosition);
    };
  }
}
