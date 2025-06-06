package edu.ntnu.idi.idatt.boardgame.model.board.tile;

/**
 * An enum representing the different types of tiles on the board. Used when writing the board to a
 * JSON file.
 *
 * @author siguraso & MagnusNaessanGaarder
 * @version 1.0
 * @since 1.0
 */
public enum TileType {
  NORMAL() {
    @Override
    public String getTileType() {
      return "NormalTile";
    }
  },
  LADDER() {
    @Override
    public String getTileType() {
      return "LadderTile";
    }
  },
  RANDOM_ACTION() {
    @Override
    public String getTileType() {
      return "RandomActionTile";
    }
  },
  ROLL_AGAIN() {
    @Override
    public String getTileType() {
      return "RollAgainTile";
    }
  },
  RETURN_TO_START() {
    @Override
    public String getTileType() {
      return "ReturnToStartTile";
    }
  },
  WINNER() {
    @Override
    public String getTileType() {
      return "WinnerTile";
    }
  },
  ADD_COINS() {
    @Override
    public String getTileType() {
      return "AddCoinsTile";
    }
  },
  REMOVE_COINS() {
    @Override
    public String getTileType() {
      return "RemoveCoinsTile";
    }
  },
  ADD_CROWN() {
    @Override
    public String getTileType() {
      return "AddCrownTile";
    }
  },
  MOWSER() {
    @Override
    public String getTileType() {
      return "MowserTile";
    }
  };

  /**
   * Method to identify the type of tile by returning the name of the tile as a string.
   *
   * @return The name of the tile as a string
   */
  public abstract String getTileType();
}
