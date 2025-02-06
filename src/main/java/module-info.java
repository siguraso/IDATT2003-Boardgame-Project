module edu.ntnu.idi.idatt.boardgame {
  requires javafx.controls;
  requires javafx.fxml;

  opens edu.ntnu.idi.idatt.boardgame to javafx.fxml;
  exports edu.ntnu.idi.idatt.boardgame;
}