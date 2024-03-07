package com.example.krypto1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class HelloController {
    private String bufor;

    @FXML
    private TextArea ta1;

    @FXML
    private TextArea ta2;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onEncryptClick() {
        bufor = ta1.getText();
        System.out.println(bufor);
        ta1.clear();
    }
}