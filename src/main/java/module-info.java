module edu.ntnu.idi.idatt.boardgame {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires java.desktop;
  requires com.google.gson;

  opens edu.ntnu.idi.idatt.boardgame.view to javafx.graphics;
  opens edu.ntnu.idi.idatt.boardgame.model.board.tile to com.google.gson;
  opens edu.ntnu.idi.idatt.boardgame.model.board to com.google.gson;
  opens edu.ntnu.idi.idatt.boardgame.apps to javafx.fxml;
  opens edu.ntnu.idi.idatt.boardgame.view.window to javafx.graphics;
  exports edu.ntnu.idi.idatt.boardgame.apps;
}