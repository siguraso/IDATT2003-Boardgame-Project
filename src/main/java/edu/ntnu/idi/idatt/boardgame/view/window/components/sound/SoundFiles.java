package edu.ntnu.idi.idatt.boardgame.view.window.components.sound;

/**
 * An enum that contains the sound files that can be played in the application.
 *
 * @version 1.0
 * @since 1.0
 * @author MagnusNaesanGaarder
 */
enum SoundFiles {
    // TODO: Add more sound files
    BG_MUSIC("bg_music.wav"),
    BUTTON_CLICK("button_click.wav"),
    GAME_WON("game_won.wav"),
    PIECE_PLACED("piece_placed.wav"),
    PIECE_MOVED("piece_moved.wav");

    private final String fileName;

    SoundFiles(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}