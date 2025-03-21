package edu.ntnu.idi.idatt.boardgame.model.io;

import com.google.gson.*;
import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.TileType;
import java.io.FileReader;
import javafx.concurrent.Task;

public class BoardReaderGson implements BoardFileReader, JsonDeserializer<Task> {

  private final Gson gson;

  public BoardReaderGson() {
    gson = new GsonBuilder().setPrettyPrinting().create();
  }

  @Override
  public Board readBoardFile(String filePath) {
    try (FileReader fileReader = new FileReader(filePath)) {

      return gson.fromJson(fileReader, Board.class);
    } catch (Exception e) {

      throw new RuntimeException(e);
    }
  }

  @Override
  public Task deserialize(JsonElement jsonElement, java.lang.reflect.Type type,
      JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    TileType tileType = TileType.valueOf(jsonObject.get("tileType").getAsString());

    int tileNumber = jsonObject.get("tileNumber").getAsInt();
    int[] onscreenPosition = new int[2];
    onscreenPosition[0] = jsonObject.get("onscreenPosition").getAsInt();
    onscreenPosition[1] = jsonObject.get("onscreenPosition").getAsInt();

    switch (tileType) {
      case NORMAL:
          
        break;
      case LADDER:
        break;
      case RETURN_TO_START:
        break;
      default:
        break;
    }

    return null;
  }

  public static void main(String[] args) {
    BoardReaderGson boardReaderGson = new BoardReaderGson();
    Board board = boardReaderGson.readBoardFile(
        "/Users/sigurdandris/Documents/IdeaProjects/IDATT2003-Boardgame-Project/src/main/resources/JSON/ExampleBoard.json");
    System.out.println(board.getTiles().get(1).getTileType());
  }


}
