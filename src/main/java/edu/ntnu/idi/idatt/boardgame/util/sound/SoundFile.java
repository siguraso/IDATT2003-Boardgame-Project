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
      return filepath + "bg_music.wav";
    }
  },
  BUTTON_CLICK() {
    @Override
    public String getSoundPath() {
      return filepath + "button_click.wav";
    }
  },
  GAME_WON() {
    @Override
    public String getSoundPath() {
      return filepath + "game_won.wav";
    }
  },
  PIECE_PLACED() {
    @Override
    public String getSoundPath() {
      return filepath + "place_piece.wav";
    }
  },
  PIECE_MOVED() {
    @Override
    public String getSoundPath() {
      return filepath + "move_player.wav";
    }
  },
  PLAYER_FALL() {
    @Override
    public String getSoundPath() {
      return filepath + "player_fall.wav";
    }
  },
  PLAYER_CLIMB() {
    @Override
    public String getSoundPath() {
      return filepath + "player_climb.wav";
    }
  },
  RETURN_TO_START() {
    @Override
    public String getSoundPath() {
      return filepath + "return_to_start.wav";
    }
  };

  private static final String filepath = "/Sounds/";

  /**
   * Returns the path to the sound file.
   *
   * @return String containing the path to the sound file.
   */
  public abstract String getSoundPath();
}
