package edu.ntnu.idi.idatt.boardgame.model.board;

import edu.ntnu.idi.idatt.boardgame.model.board.tile.LadderTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.NormalTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.RandomActionTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.ReturnToStartTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.RollAgainTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.WinnerTile;
import edu.ntnu.idi.idatt.boardgame.model.io.board.BoardReaderGson;
import java.util.HashMap;
import java.util.stream.IntStream;

/**
 * A factory class for creating the various game boards for the different games.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class BoardFactory {

  private static final int LADDER_GAME_ROWS = 10;
  private static final int LADDER_GAME_COLS = 9;

  /**
   * Creates a new hardcoded board based on the given {@link BoardType}.
   *
   * <p>SNAKES_AND_LADDERS: A {@link Board} for the game Snakes and Ladders.</p>
   *
   * <p>PARIO_MARTI: A {@link Board} for the Pario Marti game.</p>
   *
   * @param boardType the boardType of board to create.
   * @return a new board of the given boardType.
   */
  public static Board createBoard(BoardType boardType, boolean useJson, String filePath) {
    if (!useJson) {
      return switch (boardType) {
        case LADDER_GAME_SPECIAL -> specialLadderGameBoard(boardType);
        case LADDER_GAME_REGULAR -> vanillaLadderGameBoard(boardType);
        default -> throw new IllegalArgumentException("Unknown BoardType: " + boardType);
      };
    } else {
      return switch (boardType) {
        case LADDER_GAME_SPECIAL -> specialLadderGameBoardJson(boardType);
        case LADDER_GAME_REGULAR -> vanillaLadderGameBoardJson(boardType);
        case LADDER_GAME_JSON -> ladderGameCustomJsonBoard(boardType, filePath);
        default -> throw new IllegalArgumentException("Unknown BoardType: " + boardType);
      };
    }
  }

  // specific board creation methods
  private static Board specialLadderGameBoard(BoardType boardType) {
    HashMap<Integer, Tile> tiles = getBlankBoard();
    Board specialLadderGameBoard = new Board(tiles);
    specialLadderGameBoard.setBoardType(boardType);

    // add the special tiles to the board
    // random action tiles
    tiles.put(3,
        new RandomActionTile(3, tiles.get(3).getOnscreenPosition(), specialLadderGameBoard));

    tiles.put(10,
        new RandomActionTile(10, tiles.get(10).getOnscreenPosition(), specialLadderGameBoard));

    tiles.put(43,
        new RandomActionTile(43, tiles.get(43).getOnscreenPosition(), specialLadderGameBoard));

    tiles.put(85,
        new RandomActionTile(85, tiles.get(85).getOnscreenPosition(), specialLadderGameBoard));

    // roll again tiles
    tiles.put(6,
        new RollAgainTile(6, tiles.get(6).getOnscreenPosition()));

    tiles.put(30,
        new RollAgainTile(30, tiles.get(30).getOnscreenPosition()));

    tiles.put(62,
        new RollAgainTile(62, tiles.get(62).getOnscreenPosition()));

    tiles.put(68,
        new RollAgainTile(68, tiles.get(68).getOnscreenPosition()));

    tiles.put(82,
        new RollAgainTile(82, tiles.get(82).getOnscreenPosition()));

    // return to start tiles
    tiles.put(22,
        new ReturnToStartTile(22, tiles.get(22).getOnscreenPosition()));

    tiles.put(42,
        new ReturnToStartTile(42, tiles.get(42).getOnscreenPosition()));

    tiles.put(56,
        new ReturnToStartTile(56, tiles.get(56).getOnscreenPosition()));

    tiles.put(65,
        new ReturnToStartTile(65, tiles.get(65).getOnscreenPosition()));

    // ladder tiles

    tiles.put(2,
        new LadderTile(2, tiles.get(2).getOnscreenPosition(), 40));

    tiles.put(24,
        new LadderTile(24, tiles.get(24).getOnscreenPosition(), 5));

    tiles.put(36,
        new LadderTile(36, tiles.get(36).getOnscreenPosition(), 52));

    tiles.put(49,
        new LadderTile(49, tiles.get(49).getOnscreenPosition(), 79));

    tiles.put(64,
        new LadderTile(64, tiles.get(64).getOnscreenPosition(), 27));

    tiles.put(74,
        new LadderTile(74, tiles.get(74).getOnscreenPosition(), 12));

    tiles.put(87,
        new LadderTile(87, tiles.get(87).getOnscreenPosition(), 70));

    // winner tile

    tiles.put(90, new WinnerTile(90, tiles.get(90).getOnscreenPosition()));

    return specialLadderGameBoard;
  }

  private static Board vanillaLadderGameBoard(BoardType boardType) {
    HashMap<Integer, Tile> tiles = getBlankBoard();
    Board vanillaLadderGameBoard = new Board(tiles);
    vanillaLadderGameBoard.setBoardType(boardType);

    // ladder tiles

    tiles.put(2,
        new LadderTile(2, tiles.get(2).getOnscreenPosition(), 40));

    tiles.put(8,
        new LadderTile(8, tiles.get(8).getOnscreenPosition(), 10));

    tiles.put(24,
        new LadderTile(24, tiles.get(24).getOnscreenPosition(), 5));

    tiles.put(33,
        new LadderTile(33, tiles.get(33).getOnscreenPosition(), 3));

    tiles.put(36,
        new LadderTile(36, tiles.get(36).getOnscreenPosition(), 52));

    tiles.put(42,
        new LadderTile(42, tiles.get(42).getOnscreenPosition(), 30));

    tiles.put(43,
        new LadderTile(43, tiles.get(43).getOnscreenPosition(), 62));

    tiles.put(49,
        new LadderTile(49, tiles.get(49).getOnscreenPosition(), 79));

    tiles.put(56,
        new LadderTile(56, tiles.get(56).getOnscreenPosition(), 37));

    tiles.put(64,
        new LadderTile(64, tiles.get(64).getOnscreenPosition(), 27));

    tiles.put(65,
        new LadderTile(65, tiles.get(65).getOnscreenPosition(), 82));

    tiles.put(68,
        new LadderTile(68, tiles.get(68).getOnscreenPosition(), 85));

    tiles.put(74,
        new LadderTile(74, tiles.get(74).getOnscreenPosition(), 12));

    tiles.put(87,
        new LadderTile(87, tiles.get(87).getOnscreenPosition(), 70));

    // winner tile

    tiles.put(90, new WinnerTile(90, tiles.get(90).getOnscreenPosition()));

    return vanillaLadderGameBoard;
  }

  private static Board vanillaLadderGameBoardJson(BoardType boardType) {
    BoardReaderGson boardFileReader = new BoardReaderGson();

    Board board = boardFileReader.readBoardFile("/JSON/LadderGameRegular.json", false);

    board.setBoardType(boardType);

    return board;
  }

  private static Board specialLadderGameBoardJson(BoardType boardType) {
    BoardReaderGson boardFileReader = new BoardReaderGson();

    Board board = boardFileReader.readBoardFile(
        "/JSON/LadderGameSpecial.json", false);

    board.setBoardType(boardType);

    return board;
  }

  private static Board ladderGameCustomJsonBoard(BoardType boardType, String filePath) {
    BoardReaderGson boardFileReader = new BoardReaderGson();

    Board board = boardFileReader.readBoardFile(filePath, true);

    board.setBoardType(boardType);

    return board;
  }

  private static HashMap<Integer, Tile> getBlankBoard() {
    HashMap<Integer, Tile> tiles = new HashMap<>();

    IntStream.range(0, 90).forEach(i -> {
      int row = (LADDER_GAME_ROWS - 1) - (i / LADDER_GAME_COLS);
      int col = ((row % 2) == (LADDER_GAME_ROWS % 2))
          ? (LADDER_GAME_COLS - 1 - i % LADDER_GAME_COLS) : i % LADDER_GAME_COLS;

      tiles.put((i + 1), new NormalTile((i + 1), new int[]{col, row}));
    });

    return tiles;
  }

}
