package edu.ntnu.idi.idatt.boardgame.util.sound;

/**
 * An enum that contains the sound files that can be played in the application.
 *
 * @author MagnusNaesanGaarder & siguraso
 * @version 1.0
 * @since 1.0
 */
public enum SoundFile {
  // TODO: Add more sound files
  BG_MUSIC("bg_music.wav"),
  BUTTON_CLICK("button_click.wav"),
  GAME_WON("game_won.wav"),
  PIECE_PLACED("piece_placed.wav"),
  MOVE_PLAYER(
      "/Sounds/move_player.wav");

  private final String filePath;

  private SoundFile(String filePath) {
    this.filePath = filePath;
  }

  public String getFilePath() {
    return filePath;
  }
}