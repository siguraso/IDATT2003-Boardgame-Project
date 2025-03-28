package edu.ntnu.idi.idatt.boardgame.util.sound;

/**
 * Interface for playing all kinds of sound files.
 *
 * <p>The different implementations of this interface should be able to play different kinds of
 * sound files, e.g.:</p>
 * <ul> - Sound effects</ul>
 * <ul> - Background music</ul>
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public interface SoundPlayer {

  /**
   * Plays the currently opened {@link SoundFile}.
   */
  void playSound();

  /**
   * Stops the currently playing {@link SoundFile}.
   */
  void stopSound();

  /**
   * Opens a {@link SoundFile} that is to be played.
   *
   * @param soundFile the {@link SoundFile} that is to be played.
   */
  void openSoundFile(SoundFile soundFile);
}
