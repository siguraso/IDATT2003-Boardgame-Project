package edu.ntnu.idi.idatt.boardgame.model.board;

import edu.ntnu.idi.idatt.boardgame.model.board.tile.LadderTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.NormalTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.RandomActionTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.ReturnToStartTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.RollAgainTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.WinnerTile;
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
  public static Board createBoard(BoardType boardType) {
    return switch (boardType) {
      case LADDER_GAME_SPECIAL -> specialLadderGameBoard(boardType);
      case LADDER_GAME_REGULAR -> vanillaLadderGameBoard(boardType);
      default -> throw new IllegalArgumentException("Unknown BoardType: " + boardType);
    };
  }

  // specific board creation methods
  private static Board specialLadderGameBoard(BoardType boardType) {
    HashMap<Integer, Tile> tiles = new HashMap<>();
    Board specialLadderGameBoard = new Board(tiles);
    specialLadderGameBoard.setBoardType(boardType);

    IntStream.range(0, 90).forEach(i -> {
      int row = (LADDER_GAME_ROWS - 1) - (i / LADDER_GAME_COLS);
      int col = ((row % 2) == (LADDER_GAME_ROWS % 2))
          ? (LADDER_GAME_COLS - 1 - i % LADDER_GAME_COLS) : i % LADDER_GAME_COLS;

      // check what type of tile to create
      switch (i + 1) {
        case 1:
          tiles.put((i + 1),
              new LadderTile((i + 1), new int[]{col, row}, 40, specialLadderGameBoard));
          break;
        case 3, 85, 10, 43:
          tiles.put((i + 1),
              new RandomActionTile((i + 1), new int[]{col, row}, specialLadderGameBoard));
          break;
        case 6, 30, 62, 68, 82:
          tiles.put((i + 1), new RollAgainTile((i + 1), new int[]{col, row}));
          break;
        case 22, 42, 56, 65:
          tiles.put((i + 1),
              new ReturnToStartTile((i + 1), new int[]{col, row}, specialLadderGameBoard));
          break;
        case 24:
          tiles.put((i + 1),
              new LadderTile((i + 1), new int[]{col, row}, 5, specialLadderGameBoard));
          break;
        case 36:
          tiles.put((i + 1),
              new LadderTile((i + 1), new int[]{col, row}, 52, specialLadderGameBoard));
          break;
        case 49:
          tiles.put((i + 1),
              new LadderTile((i + 1), new int[]{col, row}, 79, specialLadderGameBoard));
          break;
        case 64:
          tiles.put((i + 1),
              new LadderTile((i + 1), new int[]{col, row}, 27, specialLadderGameBoard));
          break;
        case 74:
          tiles.put((i + 1),
              new LadderTile((i + 1), new int[]{col, row}, 12, specialLadderGameBoard));
          break;
        case 87:
          tiles.put((i + 1),
              new LadderTile((i + 1), new int[]{col, row}, 70, specialLadderGameBoard));
          break;
        case 90:
          tiles.put((i + 1), new WinnerTile((i + 1), new int[]{col, row}));
          break;
        default:
          tiles.put((i + 1), new NormalTile((i + 1), new int[]{col, row}));
          break;
      }
    });

    return specialLadderGameBoard;
  }

  private static Board vanillaLadderGameBoard(BoardType boardType) {
    HashMap<Integer, Tile> tiles = new HashMap<>();
    Board vanillaLadderGameBoard = new Board(tiles);
    vanillaLadderGameBoard.setBoardType(boardType);

    IntStream.range(0, 90).forEach(i -> {
      int row = (LADDER_GAME_ROWS - 1) - (i / LADDER_GAME_COLS);
      int col = ((row % 2) == (LADDER_GAME_ROWS % 2))
          ? (LADDER_GAME_COLS - 1 - i % LADDER_GAME_COLS) : i % LADDER_GAME_COLS;

      // check what type of tile to create
      switch (i + 1) {
        case 1:
          tiles.put(i + 1, new LadderTile(i + 1, new int[]{col, row}, 40, vanillaLadderGameBoard));
          break;
        case 8:
          tiles.put(8,
              new LadderTile(8, new int[]{col, row}, 10, vanillaLadderGameBoard));
          break;
        case 24:
          tiles.put(i + 1, new LadderTile(i + 1, new int[]{col, row}, 5, vanillaLadderGameBoard));
          break;
        case 33:
          tiles.put(33, new LadderTile(33, new int[]{col, row}, 3, vanillaLadderGameBoard));
          break;
        case 36:
          tiles.put(i + 1, new LadderTile(i + 1, new int[]{col, row}, 52, vanillaLadderGameBoard));
          break;
        case 42:
          tiles.put(42,
              new LadderTile(42, new int[]{col, row}, 30, vanillaLadderGameBoard));
          break;
        case 43:
          tiles.put(43,
              new LadderTile(43, new int[]{col, row}, 62, vanillaLadderGameBoard));
          break;
        case 49:
          tiles.put(i + 1, new LadderTile(i + 1, new int[]{col, row}, 79, vanillaLadderGameBoard));
          break;
        case 56:
          tiles.put(56,
              new LadderTile(56, new int[]{col, row}, 37, vanillaLadderGameBoard));
          break;
        case 64:
          tiles.put(i + 1, new LadderTile(i + 1, new int[]{col, row}, 27, vanillaLadderGameBoard));
          break;
        case 65:
          tiles.put(65,
              new LadderTile(65, new int[]{col, row}, 82, vanillaLadderGameBoard));
          break;
        case 68:
          tiles.put(68, new LadderTile(i + 1, new int[]{col, row}, 85, vanillaLadderGameBoard));
          break;
        case 74:
          tiles.put(i + 1, new LadderTile(i + 1, new int[]{col, row}, 12, vanillaLadderGameBoard));
          break;
        case 87:
          tiles.put(i + 1, new LadderTile(i + 1, new int[]{col, row}, 70, vanillaLadderGameBoard));
          break;
        case 90:
          tiles.put(i + 1, new WinnerTile(i + 1, new int[]{col, row}));
          break;
        default:
          tiles.put(i + 1, new NormalTile(i + 1, new int[]{col, row}));
          break;
      }
    });

    return vanillaLadderGameBoard;
  }

}
