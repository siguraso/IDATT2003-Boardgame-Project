package edu.ntnu.idi.idatt.boardgame.util.sound;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Class that plays background music based on what {@link SoundFile} is given.
 *
 * <p>This class differs from SfxPlayer as this class is designed to play longer sound files, and
 * is therefore a bit more complicated as a result.</p>
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class BgmPlayer implements SoundPlayer {

  private Clip clip;
  private Long clipTimePosition;

  /**
   * Class that plays background music based on the {@link SoundFile} that is currently opened.
   */
  public BgmPlayer() {
  }

  /**
   * Pauses the currently playing sound file.
   */
  public void pauseSound() {
    if (clip == null) {
      throw new IllegalStateException("No sound file is opened");
    }

    clipTimePosition = System.currentTimeMillis();
    clip.stop();
  }

  /**
   * Resumes the currently paused sound file.
   */
  public void resumeSound() {
    if (clip == null) {
      throw new IllegalStateException("No sound file is opened");
    }

    clip.setMicrosecondPosition(clipTimePosition);
    clip.start();
  }

  @Override
  public void playSound() {
    if (clip == null) {
      throw new IllegalStateException("No sound file is opened");
    }

    clip.start();
  }

  @Override
  public void stopSound() {
    if (clip == null) {
      throw new IllegalStateException("No sound file is opened");
    }

    clip.stop();
  }

  @Override
  public void openSoundFile(SoundFile soundFile) {
    try (InputStream inputStream = BgmPlayer.class.getResourceAsStream(soundFile.getSoundPath());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {

      clip = AudioSystem.getClip();

      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);

      clip.open(audioInputStream);

    } catch (LineUnavailableException e) {
      throw new RuntimeException("Line is unavailable", e);

    } catch (UnsupportedAudioFileException e) {
      throw new IllegalArgumentException("Unsupported audio file", e);

    } catch (IOException e) {
      throw new IllegalArgumentException("The sound file: " + soundFile + " does not exist", e);
    }
  }

}
