module com.example.krypto1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.krypto1 to javafx.fxml;
    exports com.example.krypto1;
}