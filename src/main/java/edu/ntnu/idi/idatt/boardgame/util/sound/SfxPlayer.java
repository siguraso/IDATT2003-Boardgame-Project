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
 * Module that plays sounds effects based on what {@link SoundFile} is given.
 *
 * <p>This class is different to BgmPlayer as this class is designed to play shorter sound files,
 * and is, therefore, less complicated as a result.</p>
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class SfxPlayer implements SoundPlayer {

  private Clip clip;

  private String status;

  /**
   * Class that plays sound effects based on the {@link SoundFile} that is currently opened.
   */
  public SfxPlayer() {
  }

  @Override
  public void openSoundFile(SoundFile soundfile) {
    String soundPath = soundfile.getSoundPath();

    if (soundPath.isEmpty()) {
      throw new NullPointerException("The sound file: " + soundfile + " does not exist");
    }

    // try-with-resources to safely close the input streams.
    try (InputStream inputStream = SfxPlayer.class.getResourceAsStream(soundPath);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {

      // get the audio system clip to play audio.
      clip = AudioSystem.getClip();

      // open the audio input stream
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);

      // open the clip and start playing the audio file.
      clip.open(audioInputStream);

    } catch (LineUnavailableException e) {
      throw new RuntimeException("Line is unavailable", e);

    } catch (UnsupportedAudioFileException e) {
      throw new IllegalArgumentException("Unsupported audio file", e);

    } catch (IOException e) {
      throw new RuntimeException("Error reading audio file", e);

    }

  }

  @Override
  public void playSound() {
    if (clip == null) {
      throw new NullPointerException("No sound file is opened");
    }

    clip.start();
  }

  @Override
  public void stopSound() {
    if (clip == null) {
      throw new NullPointerException("No sound file is opened");
    }

    clip.stop();
  }

}