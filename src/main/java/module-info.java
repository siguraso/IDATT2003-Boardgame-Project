module edu.ntnu.idi.idatt.boardgame {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires java.desktop;

  opens edu.ntnu.idi.idatt.boardgame.view to javafx.graphics;
  opens edu.ntnu.idi.idatt.boardgame to javafx.fxml;
  opens edu.ntnu.idi.idatt.boardgame.view.window to javafx.graphics;
  exports edu.ntnu.idi.idatt.boardgame;
}