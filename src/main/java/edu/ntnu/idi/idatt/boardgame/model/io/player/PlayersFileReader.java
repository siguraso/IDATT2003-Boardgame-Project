package edu.ntnu.idi.idatt.boardgame.model.io.player;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.HashMap;

/**
 * An interface for reading a file containing players to a {@link HashMap} of {@link Player} objects.
 * <p>This way the user can save player profiles from games</p>
 *
 * @version 1.0
 * @since 1.0
 * @author siguraso
 */
public interface PlayersFileReader {

    /**
     * Reads a file containing players and returns a list of player objects.
     *
     * @param filePath The file path to the readable file.
     * @return A {@link HashMap} of {@link Player} objects containing the players from the JSON file.
     */
    HashMap<String,Player> readPlayersFile(String filePath);
}
