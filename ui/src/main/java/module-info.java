module net.gat3way.achilles.ui {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires net.gat3way.achilles.crypt;
    opens net.gat3way.achilles.ui to javafx.fxml;
    exports net.gat3way.achilles.ui;
}