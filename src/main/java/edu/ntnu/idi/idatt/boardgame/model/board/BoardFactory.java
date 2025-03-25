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

  private final static int LADDER_GAME_ROWS = 10;
  private final static int LADDER_GAME_COLS = 9;

  /**
   * Creates a new hardcoded board based on the given {@link BoardType}.
   * <p>SNAKES_AND_LADDERS: A {@link Board} for the game Snakes and Ladders.</p>
   * <p>PARIO_MARTI: A {@link Board} for the Pario Marti game.</p>
   *
   * @param boardType the boardType of board to create.
   * @return a new board of the given boardType.
   */
  public static Board createBoard(BoardType boardType) {
    switch (boardType) {
      case LADDER_GAME_SPECIAL:
        return specialLadderGameBoard();

      case LADDER_GAME_VANILLA:
        return vanillaLadderGameBoard();

      default:
        throw new IllegalArgumentException("Unknown BoardType: " + boardType);
    }
  }

  // specific board creation methods

  private static Board specialLadderGameBoard() {
    HashMap<Integer, Tile> tiles = new HashMap<>();
    Board specialLadderGameBoard = new Board(tiles);

    IntStream.range(1, 91).forEach(i -> {
      int row = (LADDER_GAME_ROWS - 1) - (i / LADDER_GAME_COLS);
      int col =
          ((row % 2) == (LADDER_GAME_ROWS % 2)) ? (LADDER_GAME_COLS - 1 - i % LADDER_GAME_COLS)
              : i % LADDER_GAME_COLS;

      // check what type of tile to create
      switch (i) {
        case 1:
          tiles.put(i, new LadderTile(i, new int[]{col, row}, 40, specialLadderGameBoard));
          break;
        case 3:
          tiles.put(i, new RandomActionTile(i, new int[]{col, row}, specialLadderGameBoard));
          break;
        case 6:
          tiles.put(i, new RollAgainTile(i, new int[]{col, row}));
          break;
        case 10:
          tiles.put(i, new RandomActionTile(i, new int[]{col, row}, specialLadderGameBoard));
          break;
        case 22:
          tiles.put(i, new ReturnToStartTile(i, new int[]{col, row}, specialLadderGameBoard));
          break;
        case 24:
          tiles.put(i, new LadderTile(i, new int[]{col, row}, 5, specialLadderGameBoard));
          break;
        case 30:
          tiles.put(i, new RollAgainTile(i, new int[]{col, row}));
          break;
        case 36:
          tiles.put(i, new LadderTile(i, new int[]{col, row}, 52, specialLadderGameBoard));
          break;
        case 42:
          tiles.put(i, new ReturnToStartTile(i, new int[]{col, row}, specialLadderGameBoard));
          break;
        case 43:
          tiles.put(i, new RandomActionTile(i, new int[]{col, row}, specialLadderGameBoard));
          break;
        case 49:
          tiles.put(i, new LadderTile(i, new int[]{col, row}, 79, specialLadderGameBoard));
          break;
        case 56:
          tiles.put(i, new ReturnToStartTile(i, new int[]{col, row}, specialLadderGameBoard));
          break;
        case 62:
          tiles.put(i, new RollAgainTile(i, new int[]{col, row}));
          break;
        case 64:
          tiles.put(i, new LadderTile(i, new int[]{col, row}, 27, specialLadderGameBoard));
          break;
        case 65:
          tiles.put(i, new ReturnToStartTile(i, new int[]{col, row}, specialLadderGameBoard));
          break;
        case 68:
          tiles.put(i, new RollAgainTile(i, new int[]{col, row}));
          break;
        case 74:
          tiles.put(i, new LadderTile(i, new int[]{col, row}, 12, specialLadderGameBoard));
          break;
        case 82:
          tiles.put(i, new RollAgainTile(i, new int[]{col, row}));
          break;
        case 85:
          tiles.put(i, new RandomActionTile(i, new int[]{col, row}, specialLadderGameBoard));
          break;
        case 87:
          tiles.put(i, new LadderTile(i, new int[]{col, row}, 70, specialLadderGameBoard));
          break;
        case 90:
          tiles.put(i, new WinnerTile(i, new int[]{col, row}));
          break;
        default:
          tiles.put(i, new NormalTile(i, new int[]{col, row}));
          break;
      }
    });

    return specialLadderGameBoard;
  }

  private static Board vanillaLadderGameBoard() {
    HashMap<Integer, Tile> tiles = new HashMap<>();
    Board vanillaLadderGameBoard = new Board(tiles);

    IntStream.range(1, 91).forEach(i -> {
      int row = (LADDER_GAME_ROWS - 1) - (i / LADDER_GAME_COLS);
      int col =
          ((row % 2) == (LADDER_GAME_ROWS % 2)) ? (LADDER_GAME_COLS - 1 - i % LADDER_GAME_COLS)
              : i % LADDER_GAME_COLS;

      // check what type of tile to create
      switch (i) {
        case 1:
          tiles.put(i, new LadderTile(i, new int[]{col, row}, 40, vanillaLadderGameBoard));
          break;
        case 24:
          tiles.put(i, new LadderTile(i, new int[]{col, row}, 5, vanillaLadderGameBoard));
          break;
        case 36:
          tiles.put(i, new LadderTile(i, new int[]{col, row}, 52, vanillaLadderGameBoard));
          break;
        case 49:
          tiles.put(i, new LadderTile(i, new int[]{col, row}, 79, vanillaLadderGameBoard));
          break;
        case 64:
          tiles.put(i, new LadderTile(i, new int[]{col, row}, 27, vanillaLadderGameBoard));
          break;
        case 74:
          tiles.put(i, new LadderTile(i, new int[]{col, row}, 12, vanillaLadderGameBoard));
          break;
        case 87:
          tiles.put(i, new LadderTile(i, new int[]{col, row}, 70, vanillaLadderGameBoard));
          break;
        case 90:
          tiles.put(i, new WinnerTile(i, new int[]{col, row}));
          break;
        default:
          tiles.put(i, new NormalTile(i, new int[]{col, row}));
          break;
      }
    });

    return vanillaLadderGameBoard;
  }

}
