package edu.ntnu.idi.idatt.boardgame.util.sound;

/**
 * An enum that contains the sound files that can be played in the application.
 *
 * @author MagnusNaesanGaarder & siguraso
 * @version 1.0
 * @since 1.0
 */
public enum SoundFile {
  BG_MUSIC() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "bg_music.wav";
    }
  },
  BUTTON_CLICK() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "button_click.wav";
    }
  },
  GAME_WON() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "game_won.wav";
    }
  },
  PIECE_PLACED() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "place_piece.wav";
    }
  },
  PIECE_MOVED() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "move_player.wav";
    }
  },
  PLAYER_FALL() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "player_fall.wav";
    }
  },
  PLAYER_CLIMB() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "player_climb.wav";
    }
  },
  RETURN_TO_START() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "return_to_start.wav";
    }
  },
  ROLL_AGAIN() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "roll_again.wav";
    }
  },
  RANDOM_ACTION_MOVE() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "random_action_move.wav";
    }
  },
  RANDOM_ACTION_SELECT() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "random_action_select.wav";
    }
  },
  RANDOM_ACTION_SHOW() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "random_action_show.wav";
    }
  },
  SWAP_PLAYERS() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "swap_players.wav";
    }
  },
  RANDOM_TILE() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "random_tile.wav";
    }
  },
  ROLL_DIE() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "roll_die.wav";
    }
  },
  ADD_COINS() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "add_coins.wav";
    }
  },
  REMOVE_COINS() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "remove_coins.wav";
    }
  },
  GET_CROWN() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "get_crown.wav";
    }
  },
  LOSE_CROWN() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "lose_crown.wav";
    }
  },
  MOWSER_LAND() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "mowser_land.wav";
    }
  },
  MOWSER_SHOW() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "mowser_show.wav";
    }
  },
  MOWSER_SELECT() {
    @Override
    public String getSoundPath() {
      return FILE_PATH + "mowser_select.wav";
    }
  };

  private static final String FILE_PATH = "/Sounds/";

  /**
   * Returns the path to the sound file.
   *
   * @return String containing the path to the sound file.
   */
  public abstract String getSoundPath();
}
