package project.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project.Model.castTypes.Converter;
import project.Model.crypto.algorithm.DesX.*;
import project.Model.crypto.algorithm.DesX;
import project.Model.castTypes.ConverterGUI;

import javafx.event.ActionEvent;

//OGÓLNE ZMIENNE + ZABAWA ZMEINNYMI
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class Menu {
    private final DesX desX = new DesX();

    @FXML //OGÓLNE ZMIENNE
    //wyswietlanie tekstu po enkrypcji//
    public TextArea normalText = new TextArea();
    //pole tekstowe zawierajace zakodowany tekst w Base64//
    public TextArea codedText = new TextArea();
    //przycisk generujący
    public Button onGenerate;
    //przycisk do operacji na plikach
    public RadioButton fileicon = new RadioButton();
    //wybór trybu operacji na plikach
    public RadioButton texticon = new RadioButton();
    //pola tekstowe do wprowadzania kluczy
    public TextArea keyText2;
    public TextArea keyText3;
    public TextArea keyText1;

    @FXML //ZABAWA NA PLIKACH
    //pole tekstowe, w którym wyświetlana jest ścieżka do wybranego pliku do zaszyfrowania
    public TextField uploadNormalFile;
    //przycisk służący do wyboru pliku do zaszyfrowania
    public Button chooseNormalFile;
    //przycisk służący do zapisu zaszyfrowanego pliku
    public Button SaveCodedFile;
    //pole tekstowe, w którym użytkownik może wprowadzić nazwę zaszyfrowanego pliku przed zapisem
    public TextField saveCodedFile;
    //przycisk służący do zapisu pliku po deszyfrowaniu
    public Button SaveNormalFile;
    //pole tekstowe, w którym użytkownik może wprowadzić nazwę pliku przed zapisem
    public TextField saveNormalFile;
    //pole tekstowe, w którym wyświetlana jest ścieżka do wybranego zaszyfrowanego pliku
    public TextField uploadCodedFile;
    //przycisk służący do wyboru zaszyfrowanego pliku do deszyfrowania
    public Button chooseCodedFile;



    //obiekt przechowujący referencję do wczytanego pliku
    private File normalFileBuffer;
    //tablics bajtów, która przechowuje zawartość wczytanego pliku w postaci bajtów przed zaszyfrowaniem
    private byte[] normalFileInByteForm;

    //obiekt przechowujacy referencję do wczytanego zaszyfrowanego pliku
    private File codedFileBuffor;
    //tablica bajtów, która przechowuje zawartość wczytanego zaszyfrowanego pliku w postaci bajtów przed deszyfrowaniem
    private byte[] codedFileInByteForm;

    //TWORZENIE MENU
    public void show() throws IOException {
        Stage menuStage = new Stage();
        menuStage.setResizable(false);
        menuStage.setTitle("Crypto");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("MainMenu.fxml")));
        Scene scene = new Scene(root);
        menuStage.setResizable(false);
        menuStage.setScene(scene);
        menuStage.show();
    }

    //@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        normalText.setWrapText(true);
        codedText.setWrapText(true);
        texticon.setSelected(true);
    }

    //OBSŁUGA NACISKANIA PRZYCISKÓW

    //AKCJA PO NACISNIĘCIU PRZYCISKU "ENCRYPT"

    public void onCodeButton (ActionEvent actionEvent){
        try{
            byte[] kluczWewnetrzny = ConverterGUI.stringToByteTab(keyText1.getText());
            byte [] kluczDES = ConverterGUI.stringToByteTab(keyText2.getText());
            byte [] kluczZewnetrzny = ConverterGUI.stringToByteTab(keyText3.getText());

            //dane tekstowe w postaci byte'ów
            byte[] textAreaInByte = null;
            //wybrany tryb pracy na tekscie
            if(texticon.isSelected()){
                textAreaInByte = ConverterGUI.stringToByteTab(normalText.getText());
                textAreaInByte = ConverterGUI.completeTheBits(textAreaInByte);

                byte [] text = Base64.getEncoder().encode(desX.encrypt(textAreaInByte, kluczWewnetrzny, kluczDES, kluczZewnetrzny));

                codedText.setText(new String(text));

            } //wybrany tryb pracy na plikach
            else{
                textAreaInByte = normalFileInByteForm;
                textAreaInByte = ConverterGUI.completeTheBits((textAreaInByte));
                codedFileInByteForm = desX.encrypt(textAreaInByte, kluczWewnetrzny, kluczDES, kluczZewnetrzny);
                codedText.setText("Plik został zaszyfrowany i przeniesiony do buforu");
            }
        } catch (Exception e){
            try{
                //do uzupełnienia
            } catch (Exception ignored){}
        }
    }
    public void onDecodedButton (ActionEvent actionEvent) throws IOException{
        try{
            byte[] kluczWewnetrzny = ConverterGUI.stringToByteTab(keyText1.getText());
            byte [] kluczDES = ConverterGUI.stringToByteTab(keyText2.getText());
            byte [] kluczZewnetrzny = ConverterGUI.stringToByteTab(keyText3.getText());

            //dane tekstowe w postaci byte'ów
            byte[] textAreaInByte = null;

            if(texticon.isSelected()){
                byte [] temporary = Base64.getDecoder().decode(codedText.getText());
                byte [] text = desX.decrypt(temporary, kluczWewnetrzny, kluczDES, kluczZewnetrzny);

                //USUWANIE DOPEŁNIAJACYCH BYTE'ÓW
                text = ConverterGUI.cutLastBytes(text);
                //interpretacja znaków byte'owych na string-do wyswietlania
                normalText.setText(new String(text));
            } else{
                textAreaInByte = codedFileInByteForm;
                normalFileInByteForm = desX.decrypt(textAreaInByte, kluczWewnetrzny, kluczDES, kluczZewnetrzny);
                normalFileInByteForm = ConverterGUI.cutLastBytes(normalFileInByteForm);
                normalText.setText("Odkodowany plik znajduje się w buforze");
            }
        } catch (Exception e){
            try{
                //do uzupełnienia
            } catch (Exception ex){}
        }
    }

    //WYBÓR PRACY NA PLIKU ALBO TEKSCIE

    //Ta metoda jest wywoływana po wyborze opcji pliku i czyści pola tekstowe
    public void onFIleChooose(ActionEvent actionEvent) {
        normalText.clear();
        codedText.clear();
        fileicon.setSelected(true);
        texticon.setSelected(false);
    }

    //Ta metoda jest wywoływana po wyborze opcji tekstu i czyści pola tekstowe
    public void onTextChoose(ActionEvent actionEvent) {
        normalText.clear();
        codedText.clear();
        texticon.setSelected(true);
        fileicon.setSelected(false);
    }

    //Ta metoda jest wywoływana po naciśnięciu przycisku "Choose" dla pliku normalnego
    //i wczytuje wybrany plik do bufora
    public void onNormalFileChoose(ActionEvent actionEvent) {
        try {
            Stage normalFileStage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");
            normalFileBuffer = fileChooser.showOpenDialog(normalFileStage);
            uploadNormalFile.setText(normalFileBuffer.getAbsolutePath());
            normalFileInByteForm = Files.readAllBytes(Path.of(normalFileBuffer.getAbsolutePath()));
            if(texticon.isSelected()) {
                String s = new String(normalFileInByteForm, System.getProperty("file.encoding"));
                normalText.setText(s);
            } else {
                normalText.setText("The file has been loaded into the buffer");
            }
        } catch (Exception ignored) {
        }
    }

    //Ta metoda jest wywoływana po naciśnięciu przycisku "Save"
    //i zapisuje zakodowany tekst lub plik do wybranego katalogu
    public void onSaveCodedFile(ActionEvent actionEvent) {
        if(texticon.isSelected()) { //save coded text as a file(from textField)
            try {
                Stage saveTextAsFile = new Stage();
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Save Text as a file");
                String directory = "";
                directory = directoryChooser.showDialog(saveTextAsFile).toString();

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(directory+"\\"+saveCodedFile.getText()));
                    writer.write(codedText.getText());
                    writer.close();
                    saveCodedFile.clear();
                } catch (Exception e) {

                }
            } catch (Exception ignored) {
            }
        } else { //save coded bytes from var as a file
            try {
                Stage saveFileStage = new Stage();
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Save file");
                String directory = "";
                directory = directoryChooser.showDialog(saveFileStage).toString();
                try (FileOutputStream fos = new FileOutputStream(directory+"\\"+saveCodedFile.getText())) {
                    fos.write(codedFileInByteForm);
                    fos.close();
                    saveCodedFile.clear();
                } catch (Exception ignored) {


                }
            } catch (Exception ignored) {
            }
        }
    }
    //tta metoda jest wywoływana po naciśnięciu przycisku "Save" i zapisuje tekst lub plik do wybranego katalogu

    public void onSaveFileChoose(ActionEvent actionEvent) {
        if(texticon.isSelected()) {
            try {
                Stage saveNormalTextAsFile = new Stage();
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Save Text as a file");
                String directory = "";
                directory = directoryChooser.showDialog(saveNormalTextAsFile).toString();

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(directory+"\\"+saveNormalFile.getText()));
                    writer.write(normalText.getText());
                    writer.close();
                    saveNormalFile.clear();
                } catch (Exception e) {


                }
            } catch (Exception ignored) {
            }
        } else {
            try {
                Stage saveNormalFileStage = new Stage();
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Save file");
                String directory = "";
                directory = directoryChooser.showDialog(saveNormalFileStage).toString();
                try (FileOutputStream fos = new FileOutputStream(directory+"\\"+saveNormalFile.getText())) {
                    fos.write(normalFileInByteForm);
                    fos.close();
                    saveNormalFile.clear();
                } catch (Exception ignored) {


                }
            } catch (Exception ignored) {
            }
        }
    }

    // Ta metoda jest wywoływana po naciśnięciu przycisku "Choose" dla zakodowanego pliku
    // //i wczytuje wybrany plik do bufora
    public void onchooseCodedFile(ActionEvent actionEvent) {
        try {

            Stage codedFileChoose = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Coded File");
            codedFileBuffor = fileChooser.showOpenDialog(codedFileChoose);
            uploadCodedFile.setText(codedFileBuffor.getAbsolutePath());
            codedFileInByteForm = Files.readAllBytes(Path.of(codedFileBuffor.getAbsolutePath()));
            if(texticon.isSelected()) {
                String s = new String(codedFileInByteForm, System.getProperty("file.encoding"));
                codedText.setText(s);
            } else {
                codedText.setText("file loaded into the buffer");
            }
        } catch (Exception ignored) {
        }
    }
/*
    //GENEROWANIE KLUCZY
    private String generateKey(int length) throws UnsupportedEncodingException {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private void genFirstKey() throws UnsupportedEncodingException {
        keyText1.setText(generateKey(8));
    }

    private void genSecondKey() throws UnsupportedEncodingException {
        keyText2.setText(generateKey(8));
    }

    private void genThirdKey() throws UnsupportedEncodingException {
        keyText3.setText(generateKey(8));
    }

    //OBSŁUGA PRZYCISKU GENEROWANIA KLUCZY -> WSZYTSKIE 3 NA RAZ
    public void onGenerate(ActionEvent actionEvent) throws UnsupportedEncodingException {
        genFirstKey();
        genSecondKey();
        genThirdKey();
    }
*/



}
