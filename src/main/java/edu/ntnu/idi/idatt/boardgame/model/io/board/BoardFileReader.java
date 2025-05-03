package edu.ntnu.idi.idatt.boardgame.model.io.board;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;

/**
 * An interface for reading a file containing a board.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public interface BoardFileReader {

  /**
   * Reads a file containing a board and returns a board object.
   *
   * @param filePath         The file path to the readable file.
   * @param isCustomFilePath A boolean indicating if the file is a user specified JSON file.
   * @return A {@link Board} object containing the board from the JSON file.
   */
  Board readBoardFile(String filePath, boolean isCustomFilePath);

}
