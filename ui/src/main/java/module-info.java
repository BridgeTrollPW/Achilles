module net.gat3way.achilles.ui {
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens net.gat3way.achilles.ui to javafx.fxml;
    exports net.gat3way.achilles.ui;
}