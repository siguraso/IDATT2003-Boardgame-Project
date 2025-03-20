package edu.ntnu.idi.idatt.boardgame.model.io;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;

public class BoardWriterGson implements BoardFileWriter {

  BoardWriterGson() {
  }

  @Override
  public void writeBoardFile(Board board, String filePath) {

  }

  private String serializeBoard(Board board) {
    //JsonObject jsonObject = jsonParser.parse(gson.toJson(board)).getAsJsonObject();
    return null;
  }
}
