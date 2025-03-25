package edu.ntnu.idi.idatt.boardgame.model.io.board;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;

public interface BoardFileReader {

  /**
   * Reads a JSON file (using the GSON standard) containing a board and returns a board object.
   *
   * @param filePath The file path to the JSON object.
   * @return A board object containing the board from the JSON file.
   */
  Board readBoardFile(String filePath);

}
