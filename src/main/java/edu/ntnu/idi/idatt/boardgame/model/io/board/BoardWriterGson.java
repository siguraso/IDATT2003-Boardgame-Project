package edu.ntnu.idi.idatt.boardgame.model.io.board;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.LadderTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.NormalTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.ReturnToStartTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;

/**
 * A class that writes a {@link Board} object to a file in JSON format using the Gson library.
 *
 * @version 1.0
 * @since 1.0
 * @author siguraso
 */
public class BoardWriterGson implements BoardFileWriter {

  private final Gson gson;

  public BoardWriterGson() {
    // Instantiate a new Gson object with pretty printing
    this.gson = new GsonBuilder().setPrettyPrinting().create();
  }

  @Override
  public void writeBoardFile(Board board, String filePath) {
    // Create a json array with the tiles in the board
    HashMap<Integer, Tile> tiles = board.getTiles();
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
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Test method to write a board to a file.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    Board board = new Board(new HashMap<>());

    HashMap<Integer, Tile> tiles = new HashMap<>();
    tiles.put(1, new NormalTile(1, new int[]{0, 0}));
    tiles.put(2, new NormalTile(2, new int[]{0, 0}));
    tiles.put(3, new LadderTile(3, new int[]{0, 0}, 1, board));
    tiles.put(4, new ReturnToStartTile(4, new int[]{0, 0}, board));

    board.setTiles(tiles);

    BoardWriterGson boardWriterGson = new BoardWriterGson();
    boardWriterGson.writeBoardFile(board,
        "/Users/sigurdandris/Documents/IdeaProjects/IDATT2003-Boardgame-Project/src/main/resources/JSON/ExampleBoard.json");
  }
}