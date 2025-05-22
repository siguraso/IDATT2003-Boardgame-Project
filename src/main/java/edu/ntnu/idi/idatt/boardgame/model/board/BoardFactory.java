package edu.ntnu.idi.idatt.boardgame.model.board;

import edu.ntnu.idi.idatt.boardgame.exception.InvalidBoardException;
import edu.ntnu.idi.idatt.boardgame.exception.MalformedBoardException;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.AddCoinsTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.AddCrownTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.LadderTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.MowserTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.NormalTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.RandomActionTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.RemoveCoinsTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.ReturnToStartTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.RollAgainTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.TileType;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.WinnerTile;
import edu.ntnu.idi.idatt.boardgame.model.io.board.LadderBoardReaderGson;
import java.util.HashMap;
import java.util.Map;
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
   * <p>PARIO_MARTY: A {@link Board} for the Pario Marti game.</p>
   *
   * @param boardType the boardType of board to create.
   * @return a new board of the given boardType.
   */
  public static Board createBoard(BoardType boardType, boolean useJson, String filePath) {
    if (!useJson) {
      return switch (boardType) {
        case LADDER_GAME_SPECIAL -> specialLadderGameBoard();
        case LADDER_GAME_REGULAR -> vanillaLadderGameBoard();
        case PARIO_MARTY -> parioMartyBoard();
        default -> throw new InvalidBoardException("Unknown BoardType: " + boardType);
      };
    } else {
      return switch (boardType) {
        case LADDER_GAME_SPECIAL -> specialLadderGameBoardJson();
        case LADDER_GAME_REGULAR -> vanillaLadderGameBoardJson();
        case LADDER_GAME_JSON -> ladderGameCustomJsonBoard(filePath);
        default -> throw new InvalidBoardException("Unknown BoardType: " + boardType);
      };
    }
  }

  // specific board creation methods
  private static Board parioMartyBoard() {
    Map<Integer, Tile> tiles = new HashMap<>();
    Board board = new Board(tiles);

    tiles.put(1, new NormalTile(1, new int[]{1, 0}));
    tiles.put(2, new AddCoinsTile(1, new int[]{2, 0}, 5));
    tiles.put(3, new AddCoinsTile(2, new int[]{3, 0}, 5));
    tiles.put(4, new RemoveCoinsTile(3, new int[]{4, 0}, 5));
    tiles.put(5, new AddCoinsTile(4, new int[]{5, 0}, 5));
    tiles.put(6, new RollAgainTile(5, new int[]{6, 0}));
    tiles.put(7, new AddCoinsTile(6, new int[]{6, 1}, 5));
    tiles.put(8, new RemoveCoinsTile(7, new int[]{6, 2}, 5));
    tiles.put(9, new AddCoinsTile(8, new int[]{7, 2}, 5));
    tiles.put(10, new AddCoinsTile(9, new int[]{8, 2}, 5));
    tiles.put(11, new AddCoinsTile(10, new int[]{8, 3}, 5));
    tiles.put(12, new RemoveCoinsTile(11, new int[]{8, 4}, 5));
    tiles.put(13, new MowserTile(12, new int[]{8, 5}));
    tiles.put(14, new AddCoinsTile(13, new int[]{8, 6}, 5));
    tiles.put(15, new AddCoinsTile(14, new int[]{8, 7}, 5));
    tiles.put(16, new RollAgainTile(15, new int[]{7, 7}));
    tiles.put(17, new RandomActionTile(16, new int[]{6, 7}, board));
    tiles.put(18, new AddCoinsTile(17, new int[]{6, 8}, 5));
    tiles.put(19, new AddCoinsTile(18, new int[]{6, 9}, 5));
    tiles.put(20, new AddCoinsTile(19, new int[]{5, 9}, 5));
    tiles.put(21, new MowserTile(20, new int[]{4, 9}));
    tiles.put(22, new AddCoinsTile(21, new int[]{3, 9}, 5));
    tiles.put(23, new AddCoinsTile(22, new int[]{2, 9}, 5));
    tiles.put(24, new ReturnToStartTile(23, new int[]{2, 8}));
    tiles.put(25, new RemoveCoinsTile(24, new int[]{2, 7}, 5));
    tiles.put(26, new AddCoinsTile(25, new int[]{1, 7}, 5));
    tiles.put(27, new AddCoinsTile(26, new int[]{0, 7}, 5));
    tiles.put(28, new RollAgainTile(27, new int[]{0, 6}));
    tiles.put(29, new RemoveCoinsTile(28, new int[]{0, 5}, 5));
    tiles.put(30, new AddCoinsTile(29, new int[]{0, 4}, 5));
    tiles.put(31, new AddCoinsTile(30, new int[]{0, 3}, 5));
    tiles.put(32, new RemoveCoinsTile(31, new int[]{0, 2}, 5));
    tiles.put(33, new AddCoinsTile(32, new int[]{1, 2}, 5));
    tiles.put(34, new AddCoinsTile(33, new int[]{2, 2}, 5));
    tiles.put(35, new RandomActionTile(34, new int[]{2, 1}, board));

    return board;
  }


  private static Board specialLadderGameBoard() {
    Map<Integer, Tile> tiles = getBlankBoard();
    Board specialLadderGameBoard = new Board(tiles);

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

  private static Board vanillaLadderGameBoard() {
    Map<Integer, Tile> tiles = getBlankBoard();
    Board vanillaLadderGameBoard = new Board(tiles);

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

  private static Board vanillaLadderGameBoardJson() {
    LadderBoardReaderGson boardFileReader = new LadderBoardReaderGson();

    return boardFileReader.readBoardFile("/JSON/LadderGameRegular.json", false);
  }

  private static Board specialLadderGameBoardJson() {
    LadderBoardReaderGson boardFileReader = new LadderBoardReaderGson();

    return boardFileReader.readBoardFile(
        "/JSON/LadderGameSpecial.json", false);
  }

  private static Board ladderGameCustomJsonBoard(String filePath) {
    LadderBoardReaderGson boardFileReader = new LadderBoardReaderGson();
    Board board;

    try {
      board = boardFileReader.readBoardFile(filePath, true);
    } catch (RuntimeException e) {
      throw new RuntimeException(e.getMessage());
    }

    // check if the board is valid:
    // needs to be 90 tiles,
    // the first tile must be a normal tile, and the last tile must be winner tile
    // the tiles must be in order from 1 to 90
    if (board.tiles().size() != 90) {
      throw new MalformedBoardException("Board must have 90 tiles");

    } else if (board.tiles().keySet().stream().anyMatch(tile -> tile < 1 || tile > 90)) {
      throw new MalformedBoardException("Tiles must be in order from 1 to 90");

    } else if (!board.tiles().get(1).getTileType().equals(TileType.NORMAL.getTileType())) {
      throw new MalformedBoardException("First tile must be a normal tile");

    } else if (!board.tiles().get(90).getTileType().equals(TileType.WINNER.getTileType())) {
      throw new MalformedBoardException("Last tile must be a winner tile");
    }

    return board;
  }

  private static Map<Integer, Tile> getBlankBoard() {
    Map<Integer, Tile> tiles = new HashMap<>();

    IntStream.range(0, 90).forEach(i -> {
      int row = (LADDER_GAME_ROWS - 1) - (i / LADDER_GAME_COLS);
      int col = ((row % 2) == (LADDER_GAME_ROWS % 2))
          ? (LADDER_GAME_COLS - 1 - i % LADDER_GAME_COLS) : i % LADDER_GAME_COLS;

      tiles.put((i + 1), new NormalTile((i + 1), new int[]{col, row}));
    });

    return tiles;
  }

}
