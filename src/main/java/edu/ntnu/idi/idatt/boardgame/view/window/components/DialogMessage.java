package edu.ntnu.idi.idatt.boardgame.view.window.components;

import javafx.scene.Node;

/**
 * A dialog message that is displayed to the user on the top right of the screen.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public interface DialogMessage {

  /**
   * Retrieves the node that is hte
   *
   * @param message The message that is displayed to the user.
   */
  Node getDialog(String message);

}
