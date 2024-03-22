package project.View.krypto1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HelloController {
    private String bufor;

    @FXML
    private TextArea tat1;

    @FXML
    private TextArea tat2;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onEncryptClick() {
        bufor = tat1.getText();
        System.out.println(bufor);
        tat1.clear();
    }

    @FXML
    protected void getFile(ActionEvent event){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Txt Files","*.txt"));
        File f = fc.showOpenDialog(null);

        try {
            Scanner sc = new Scanner(new File(String.valueOf(f)));
            bufor = "";
            while(sc.hasNextLine()){
                bufor = bufor + sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(bufor);

    }
}