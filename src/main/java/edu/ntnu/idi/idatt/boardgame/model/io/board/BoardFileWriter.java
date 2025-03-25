package edu.ntnu.idi.idatt.boardgame.model.io.board;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;

public interface BoardFileWriter {

  /**
   * Writes a board to a JSON file with the GSON standard.
   *
   * @param board    The board object to write to the file.
   * @param filePath The file path to the JSON file.
   */
  void writeBoardFile(Board board, String filePath);
}
