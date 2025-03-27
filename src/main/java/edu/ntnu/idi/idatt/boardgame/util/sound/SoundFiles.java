package edu.ntnu.idi.idatt.boardgame.util.sound;

/**
 * An enum that contains the sound files that can be played in the application.
 *
 * @author MagnusNaesanGaarder
 * @version 1.0
 * @since 1.0
 */
public enum SoundFiles {
  BG_MUSIC() {
    @Override
    public String getSound() {
      return filepath + "bg_music.wav";
    }
  },
  BUTTON_CLICK() {
    @Override
    public String getSound() {
      return filepath + "button_click.m4a";
    }
  },
  GAME_WON() {
    @Override
    public String getSound() {
      return filepath + "game_won.m4a";
    }
  },
  PIECE_PLACED() {
    @Override
    public String getSound() {
      return filepath + "place_piece.m4a";
    }
  },
  PIECE_MOVED() {
    @Override
    public String getSound() {
      return filepath + "move_player.wav";
    }
  };
  private static final String filepath = "src/main/resources/Sounds/";

  public abstract String getSound();
}
