package edu.ntnu.idi.idatt.boardgame.model.io;

import com.google.gson.*;
import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.LadderTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.NormalTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.RandomActionTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.ReturnToStartTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.TileType;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.WinnerTile;
import java.io.FileReader;
import java.util.HashMap;

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
  public Tile deserialize(JsonElement jsonElement, java.lang.reflect.Type type,
      JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    TileType tileType = TileType.valueOf(jsonObject.get("type").getAsString());

    int tileNumber = jsonObject.get("tileNumber").getAsInt();
    int[] onscreenPosition = new int[2];
    onscreenPosition[0] = jsonObject.get("onscreenPosition").getAsJsonArray().get(0).getAsInt();
    onscreenPosition[1] = jsonObject.get("onscreenPosition").getAsJsonArray().get(1).getAsInt();

    return switch (tileType) {
      case NORMAL -> new NormalTile(tileNumber, onscreenPosition);
      case LADDER ->
          new LadderTile(tileNumber, onscreenPosition, jsonObject.get("destinationTileNumber").getAsInt(), board);
      case RETURN_TO_START -> new ReturnToStartTile(tileNumber, onscreenPosition, board);
      case RANDOM_ACTION -> new RandomActionTile(tileNumber, onscreenPosition, board);
      case WINNER -> new WinnerTile(tileNumber, onscreenPosition);
    };

  }

  public static void main(String[] args) {
    BoardReaderGson boardReaderGson = new BoardReaderGson();
    Board board = boardReaderGson.readBoardFile(
        "/Users/sigurdandris/Documents/IdeaProjects/IDATT2003-Boardgame-Project/src/main/resources/JSON/ExampleBoard.json");
  }


}
