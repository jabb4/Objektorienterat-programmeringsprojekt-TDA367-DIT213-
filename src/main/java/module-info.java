module com.grouptwelve.roguelikegame {
    requires javafx.controls;
    requires javafx.fxml;



    opens com.grouptwelve.roguelikegame to javafx.fxml;
    exports com.grouptwelve.roguelikegame;
}