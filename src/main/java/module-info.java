module PE.CountingSoftware {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens abstraction to javafx.graphics;
    opens presentation to javafx.graphics;
}