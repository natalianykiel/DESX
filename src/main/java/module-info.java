module com.example.krypto1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens project.View.krypto1 to javafx.fxml;
    exports project.View.krypto1;
        exports project.View;
        opens project.View to javafx.fxml;
}
