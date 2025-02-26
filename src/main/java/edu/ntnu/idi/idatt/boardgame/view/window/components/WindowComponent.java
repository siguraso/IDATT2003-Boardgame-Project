package edu.ntnu.idi.idatt.boardgame.view.window.components;

import javafx.scene.Node;

/**
 * A dialog message that is displayed to the user on the top right of the screen.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public interface WindowComponent {

  /**
   * Retrieves the node that contains the window component
   */
  Node getComponent();
}
