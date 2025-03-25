package edu.ntnu.idi.idatt.boardgame.model.io.board;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;

public interface BoardFileWriter {

  /**
   * Writes a {@link Board} to file.
   *
   * @param board    The {@link Board} object to write to the file.
   * @param filePath The file path to the file to write to.
   */
  void writeBoardFile(Board board, String filePath);
}
