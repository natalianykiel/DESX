package project.View.krypto1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project.Model.FileHandler;
import project.Model.castTypes.ConverterGUI;
import project.Model.crypto.algorithm.DesX;

import javax.swing.JFileChooser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

public class HelloController {
    private final DesX desX = new DesX();
    //obiekt przechowujący referencję do wczytanego pliku
    private File normalFileBuffer;
    //tablics bajtów, która przechowuje zawartość wczytanego pliku w postaci bajtów przed zaszyfrowaniem
    private byte[] normalFileInByteForm;
    //obiekt przechowujacy referencję do wczytanego zaszyfrowanego pliku
    private File codedFileBuffor;
    //tablica bajtów, która przechowuje zawartość wczytanego zaszyfrowanego pliku w postaci bajtów przed deszyfrowaniem
    private byte[] codedFileInByteForm;
    //bool przechowywujacy informacje czy ostatnia operacja bylo kodowanie czy dekodowanie
    private boolean isCoded = false;
    //zmienna do przechowania rozszerzenia pliku
    private String extension;

    private String name;

    private FileHandler fh = new FileHandler();

    @FXML
    private TextArea normalText;

    @FXML
    private TextArea codedText;
    @FXML
    public TextField tkeyText2;
    @FXML
    public TextField tkeyText3;
    @FXML
    public TextField tkeyText1;
    @FXML
    public TextField fkeyText2;
    @FXML
    public TextField fkeyText3;
    @FXML
    public TextField fkeyText1;
    @FXML
    public TextField finfoText;

    //pole tekstowe, w którym użytkownik może wprowadzić nazwę zaszyfrowanego pliku przed zapisem
    @FXML
    public TextField saveCodedFile;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onTextEncryptClick() {
        try{
            byte[] kluczWewnetrzny = ConverterGUI.stringToByteTab(tkeyText1.getText());
            byte [] kluczDES = ConverterGUI.stringToByteTab(tkeyText2.getText());
            byte [] kluczZewnetrzny = ConverterGUI.stringToByteTab(tkeyText3.getText());

            //dane tekstowe w postaci byte'ów
            byte[] textAreaInByte = null;
            //wybrany tryb pracy na tekscie
                textAreaInByte = ConverterGUI.stringToByteTab(normalText.getText());
                textAreaInByte = ConverterGUI.completeTheBits(textAreaInByte);

                byte [] text = Base64.getEncoder().encode(desX.encrypt(textAreaInByte, kluczWewnetrzny, kluczDES, kluczZewnetrzny));

                codedText.setText(new String(text));
                normalText.clear();
        } catch (Exception e){
            try{
                //do uzupełnienia
            } catch (Exception ignored){}
        }
    }

    @FXML
    protected void onTextDecryptClick() {
        try{
            byte[] kluczWewnetrzny = ConverterGUI.stringToByteTab(fkeyText1.getText());
            byte [] kluczDES = ConverterGUI.stringToByteTab(fkeyText2.getText());
            byte [] kluczZewnetrzny = ConverterGUI.stringToByteTab(fkeyText3.getText());

            //dane tekstowe w postaci byte'ów
            byte[] textAreaInByte = null;


                byte [] temporary = Base64.getDecoder().decode(codedText.getText());
                byte [] text = desX.decrypt(temporary, kluczWewnetrzny, kluczDES, kluczZewnetrzny);

                //USUWANIE DOPEŁNIAJACYCH BYTE'ÓW
                text = ConverterGUI.cutLastBytes(text);
                //interpretacja znaków byte'owych na string-do wyswietlania
                normalText.setText(new String(text));
        } catch (Exception e){
            try{
                //do uzupełnienia
            } catch (Exception ex){}
        }
    }

    @FXML
    protected void onFileEncryptClick() {
        try{
            byte[] kluczWewnetrzny = ConverterGUI.stringToByteTab(fkeyText1.getText());
            byte [] kluczDES = ConverterGUI.stringToByteTab(fkeyText2.getText());
            byte [] kluczZewnetrzny = ConverterGUI.stringToByteTab(fkeyText3.getText());

            //dane tekstowe w postaci byte'ów
            byte[] textAreaInByte = null;

            textAreaInByte = normalFileInByteForm;
            textAreaInByte = ConverterGUI.completeTheBits((textAreaInByte));
            codedFileInByteForm = desX.encrypt(textAreaInByte, kluczWewnetrzny, kluczDES, kluczZewnetrzny);

            finfoText.setText("Plik został zaszyfrowany i przeniesiony do buforu");
            isCoded = true;


        } catch (Exception e){
            try{
                //do uzupełnienia
            } catch (Exception ignored){}
        }
    }

    @FXML
    protected void onFileDecryptClick() {
        try{
            byte[] kluczWewnetrzny;
            byte[] kluczDES;
            byte[] kluczZewnetrzny;

                kluczWewnetrzny = ConverterGUI.stringToByteTab(fkeyText1.getText());
                kluczDES = ConverterGUI.stringToByteTab(fkeyText2.getText());
                kluczZewnetrzny = ConverterGUI.stringToByteTab(fkeyText3.getText());

            //dane tekstowe w postaci byte'ów
            byte[] textAreaInByte = null;
            textAreaInByte = codedFileInByteForm;
                normalFileInByteForm = desX.decrypt(textAreaInByte, kluczWewnetrzny, kluczDES, kluczZewnetrzny);
                normalFileInByteForm = ConverterGUI.cutLastBytes(normalFileInByteForm);
                finfoText.setText("Odkodowany plik znajduje się w buforze");
                isCoded = false;


        } catch (Exception e){
            try{
                //do uzupełnienia
            } catch (Exception ex){}
        }
    }

    @FXML
    protected void getFile(ActionEvent event){
        try {
            Stage normalFileStage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");
            normalFileBuffer = fileChooser.showOpenDialog(normalFileStage);
            normalFileInByteForm = Files.readAllBytes(Path.of(normalFileBuffer.getAbsolutePath()));

            finfoText.setText("Wczytano plik: " + normalFileBuffer.getAbsolutePath());

            if(fh.getMetadata(getFileName(normalFileBuffer))==null) {
                System.out.println("Plik wczytany z buforu");
                extension = getFileExtension(normalFileBuffer);
                name = getFileName(normalFileBuffer);
            }else {
                System.out.println("Plik wczytany z bazy");
                String tab[] = new String[5];
                tab = fh.getMetadata(getFileName(normalFileBuffer));
                name = tab[0];
                extension = tab[1];
                fkeyText1.setText(tab[2]);
                fkeyText2.setText(tab[3]);
                fkeyText3.setText(tab[4]);
            }


        } catch (Exception ignored) {
        }
    }

    @FXML
    protected void saveFile(ActionEvent event) {
        try {
            Stage saveNormalFileStage = new Stage();
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Save file");
            String directory = "";
            directory = directoryChooser.showDialog(saveNormalFileStage).toString();
            String saveNormalFile;
            String fileExtension;

            if(isCoded){
                saveNormalFile = name+"encoded";
                fileExtension = ".txt";
                fh.writeToFile(saveNormalFile,
                        extension,
                        ConverterGUI.stringToByteTab(fkeyText1.getText()),
                        ConverterGUI.stringToByteTab(fkeyText2.getText()),
                        ConverterGUI.stringToByteTab(fkeyText3.getText()));
            }else{
                saveNormalFile = name+"decoded";
                fileExtension = "."+extension;
            }

            try (FileOutputStream fos = new FileOutputStream(directory+"\\"+saveNormalFile+fileExtension/*.getText()*/)) {
                fos.write(normalFileInByteForm);
                fos.close();
                //saveNormalFile.clear();
                finfoText.setText("Plik został zapisany w :" + directory+"\\"+saveNormalFile+fileExtension);
            } catch (Exception ignored) {


            }
        } catch (Exception ignored) {
        }

    }

    //OBSŁUGA PRZYCISKU GENEROWANIA KLUCZY -> WSZYTSKIE 3 NA RAZ
    @FXML
    public void onGenerate(ActionEvent actionEvent) throws UnsupportedEncodingException {
        genFirstKey();
        genSecondKey();
        genThirdKey();
    }

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
        String key = generateKey(8);
        tkeyText1.setText(key);
        fkeyText1.setText(key);
    }

    private void genSecondKey() throws UnsupportedEncodingException {
        String key = generateKey(8);
        tkeyText2.setText(key);
        fkeyText2.setText(key);
    }

    private void genThirdKey() throws UnsupportedEncodingException {
        String key = generateKey(8);
        tkeyText3.setText(key);
        fkeyText3.setText(key);
    }

    private static String getFileExtension(File file){
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    private static String getFileName(File file){
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }
}