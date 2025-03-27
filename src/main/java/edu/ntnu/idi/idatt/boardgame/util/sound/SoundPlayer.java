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
 * Module that plays sounds based on what {@link SoundFile} is passed through.
 */
public class SoundPlayer {

  private static AudioInputStream audioInputStream;
  private static Clip clip;

  /**
   * Plays the soundFile that is passed through the method. The soundFile played is based off of
   * what enum from {@link SoundFile} is passed through.
   *
   * @param soundFile the {@link SoundFile} that is to be played.
   */
  public static void playSound(SoundFile soundFile) {

    String soundPath = soundFile.getFilePath();

    try (InputStream inputStream = SoundPlayer.class.getResourceAsStream(soundPath);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {

      // get the audio system clip to play audio.
      clip = AudioSystem.getClip();

      // open the audio input stream
      audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);

      // open the clip and start playing the audio file.
      clip.open(audioInputStream);
      clip.start();

    } catch (LineUnavailableException e) {
      throw new RuntimeException("Line is unavailable", e);

    } catch (UnsupportedAudioFileException e) {
      throw new IllegalArgumentException("Unsupported audio file", e);

    } catch (IOException e) {
      throw new RuntimeException("Error reading audio file", e);

    }

  }

}