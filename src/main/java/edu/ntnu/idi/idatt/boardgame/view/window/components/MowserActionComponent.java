package edu.ntnu.idi.idatt.boardgame.view.window.components;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * This class represents the action component for the Mowser tile in the game. It extends the
 * RandomActionComponent class and provides a list of actions that can be performed when a player
 * lands on a Mowser tile.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class MowserActionComponent extends RandomActionComponent {

  @Override
  public Node getComponent() {
    actionsListView.getItems()
        .addAll("Lose 20 coins!", "Lose a crown!", "Lose all of your coins!",
            "Return back to start!");

    // make the list view not selectable by mouse or keyboard
    actionsListView.setMouseTransparent(true);
    actionsListView.setFocusTraversable(false);

    actionsListView.getSelectionModel().select(0);

    header.setText("Mowser Tile!");

    // from css sheet
    int listHeight = 30;
    actionsListView.setMaxHeight(listHeight * actionsListView.getItems().size() + (double) 3);
    actionsListView.setPrefHeight(listHeight * actionsListView.getItems().size() + (double) 3);

    header.getStyleClass().add("header");
    header.setStyle("-fx-text-fill: text_wht;");

    listViewContainer.getChildren().addAll(header, actionsListView);

    listViewContainer.setAlignment(javafx.geometry.Pos.CENTER);
    listViewContainer.setSpacing(10);

    StackPane stackPane = new StackPane();
    stackPane.getChildren().add(listViewContainer);

    stackPane.getStyleClass().add("mowser-action-list");

    return stackPane;
  }
}
