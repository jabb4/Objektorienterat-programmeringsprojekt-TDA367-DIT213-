module com.grouptwelve.roguelikegame {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.grouptwelve.roguelikegame to javafx.fxml;
    exports com.grouptwelve.roguelikegame;
}