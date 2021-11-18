module PE.CountingSoftware {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens abstraction to javafx.graphics, javafx.fxml;
    opens presentation to javafx.graphics,javafx.fxml;
    opens controller to javafx.graphics,javafx.fxml;

    exports abstraction;
    exports presentation;
    exports controller;
}