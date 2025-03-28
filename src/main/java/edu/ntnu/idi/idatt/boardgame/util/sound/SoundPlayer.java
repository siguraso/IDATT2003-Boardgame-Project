package edu.ntnu.idi.idatt.boardgame.util.sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Module that plays sounds based on what {@link SoundFiles} is passed through.
 */
public class SoundPlayer {

  private static AudioInputStream audioInputStream;
  private static Clip clip;

  private static String status;
  private static Long currentFrame;

  /**
   * Plays the soundFile that is passed through the method. The soundFile played is based off of
   * what enum from {@link SoundFiles} is passed through.
   *
   * @param soundFile the {@link SoundFiles} that is to be played.
   */
  public static void playSound(SoundFiles soundFile) {

    String soundPath = soundFile.getSound();

    try (InputStream inputStream = SoundPlayer.class.getResourceAsStream(soundPath);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {

      // get the audio system clip to play audio.
      clip = AudioSystem.getClip();

      // open the audio input stream
      audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);

      // open the clip and start playing the audio file.
      clip.open(audioInputStream);
      clip.start();

      status = "play";

    } catch (LineUnavailableException e) {
      throw new RuntimeException("Line is unavailable", e);

    } catch (UnsupportedAudioFileException e) {
      throw new IllegalArgumentException("Unsupported audio file", e);

    } catch (IOException e) {
      throw new RuntimeException("Error reading audio file", e);

    }

  }

  /**
   * Method for pausing the current sound.
   */
  public static void pause() {
    if (status.equals("pause")) {
      throw new IllegalArgumentException("Sound is already paused!");
    }

    currentFrame = clip.getMicrosecondPosition();

    clip.stop();

    status = "pause";
  }

  public static void resume() {
    if (status.equals("play")) {
      throw new IllegalArgumentException("Sound is already playing!");
    }


  }

  /**
   * Method for opening a new sound file.
   */
  public static void openSound(SoundFiles soundfile) {
    String soundPath = soundfile.getSound();

    if (soundPath.isEmpty()) {
      throw new NullPointerException("The sound file: " + soundfile + " does not exist");
    }

    try (InputStream inputStream = SoundPlayer.class.getResourceAsStream(soundPath);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {

      // get the audio system clip to play audio.
      clip = AudioSystem.getClip();

      // open the audio input stream
      audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);

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

}