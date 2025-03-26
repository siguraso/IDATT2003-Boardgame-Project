package edu.ntnu.idi.idatt.boardgame.model.io.board;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;

/**
 * An interface for reading a file containing a board.
 *
 * @version 1.0
 * @since 1.0
 * @author siguraso
 */
public interface BoardFileReader {

  /**
   * Reads a file containing a board and returns a board object.
   *
   * @param filePath The file path to the readable file.
   * @return A {@link Board} object containing the board from the JSON file.
   */
  Board readBoardFile(String filePath);

}
