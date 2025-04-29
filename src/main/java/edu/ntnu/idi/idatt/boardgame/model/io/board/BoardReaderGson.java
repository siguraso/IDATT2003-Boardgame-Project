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
import java.lang.reflect.Type;
import java.util.HashMap;

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

  public BoardReaderGson() {
    gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(Tile.class, this)
        .create();
  }

  @Override
  public Board readBoardFile(String filePath) {
    HashMap<Integer, Tile> tiles = new HashMap<>();
    board = new Board(tiles);

    try (FileReader fileReader = new FileReader(filePath)) {
      // get the "tiles" jsonarray from the file
      JsonArray jsonArray = JsonParser.parseReader(fileReader).getAsJsonObject().get("tiles")
          .getAsJsonArray();

      jsonArray.forEach(jsonElement -> {
        Tile tile = gson.fromJson(jsonElement, Tile.class);
        tiles.put(tile.getTileNumber(), tile);
      });

      return board;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

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
      case RETURN_TO_START -> new ReturnToStartTile(tileNumber, onscreenPosition, board);
      case RANDOM_ACTION -> new RandomActionTile(tileNumber, onscreenPosition, board);
      case WINNER -> new WinnerTile(tileNumber, onscreenPosition);
      case ROLL_AGAIN -> new RollAgainTile(tileNumber, onscreenPosition);
    };
  }
}
