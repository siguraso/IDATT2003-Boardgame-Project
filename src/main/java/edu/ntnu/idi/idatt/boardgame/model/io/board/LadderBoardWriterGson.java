package edu.ntnu.idi.idatt.boardgame.model.io.board;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * A class that writes a {@link Board} object to a file in JSON format using the Gson library.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class LadderBoardWriterGson implements BoardFileWriter {

  private final Gson gson;

  /**
   * Constructor for the LadderBoardWriterGson class. Writes a {@link Board} object to a JSON file
   * in the GSON format. The file contains a JSON array of tiles, where each tile is represented as
   * a JSON object.
   */
  public LadderBoardWriterGson() {
    // Instantiate a new Gson object with pretty printing
    this.gson = new GsonBuilder().setPrettyPrinting().create();
  }

  @Override
  public void writeBoardFile(Board board, String filePath) {
    // Create a json array with the tiles in the board
    Map<Integer, Tile> tiles = board.tiles();
    JsonArray jsonTileArray = new JsonArray();

    tiles.keySet().forEach(key -> jsonTileArray.add(gson.toJsonTree(tiles.get(key))));

    // Wrap the json array in a json object with a "board" tag
    JsonObject jsonObject = new JsonObject();
    jsonObject.add("tiles", jsonTileArray);

    // Create a json string from the json object with pretty printing
    String jsonString = gson.toJson(jsonObject);

    try (FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
      bufferedWriter.write(jsonString);
    } catch (IOException e) {
      throw new RuntimeException("Error writing to file: " + filePath, e);
    }
  }
}