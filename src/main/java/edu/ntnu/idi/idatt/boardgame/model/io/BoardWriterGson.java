package edu.ntnu.idi.idatt.boardgame.model.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.LadderTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.NormalTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.ReturnToStartTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;

public class BoardWriterGson implements BoardFileWriter {

  private final Gson gson;

  public BoardWriterGson() {
    // instantiate a new Gson object with pretty printing
    this.gson = new GsonBuilder().setPrettyPrinting().create();
  }

  @Override
  public void writeBoardFile(Board board, String filePath) {

    try (FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

      gson.toJson(board, bufferedWriter);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Board board = new Board(new HashMap<>());

    HashMap<Integer, Tile> tiles = new HashMap<>();
    tiles.put(1, new NormalTile(1, null));
    tiles.put(2, new NormalTile(2, null));
    tiles.put(3, new LadderTile(3, null, 1, board));
    tiles.put(4, new ReturnToStartTile(4, null, board));

    board.setTiles(tiles);

    BoardWriterGson boardWriterGson = new BoardWriterGson();
    boardWriterGson.writeBoardFile(board,
        "/Users/sigurdandris/Documents/IdeaProjects/IDATT2003-Boardgame-Project/src/main/resources/JSON/ExampleBoard.json");
  }
}