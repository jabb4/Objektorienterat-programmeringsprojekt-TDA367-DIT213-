module com.grouptwelve.roguelikegame {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;


    opens com.grouptwelve.roguelikegame to javafx.fxml;
    opens com.grouptwelve.roguelikegame.view to javafx.fxml;
    opens com.grouptwelve.roguelikegame.controller to javafx.fxml;

    exports com.grouptwelve.roguelikegame;
    exports com.grouptwelve.roguelikegame.view;
}
