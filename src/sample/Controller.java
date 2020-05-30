package sample;


import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.Text;

import java.lang.Math;
import java.io.*;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Controller<bool> {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button en_de_cipher;

    @FXML
    private TextField code_key;

    @FXML
    private TextField fileName;

    @FXML
    private Button OpenFileManager;

    @FXML
    private TextField control_sum;

    public String cip= "дешиф";
    private File file;
    private String fName="";
    private double rSpeed =0;
    private double wSpeed =0;
    private Long fileSize;

    @FXML
    void browser(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.open();
        String fName=fileChooser.getFileName();
        File file =fileChooser.getFile();
        this.file= file;
        this.fName= fName;
        this.fileSize= fileChooser.getSize();
        System.out.println(fName);
        if (!fName.substring(fName.lastIndexOf('.')+1, fName.length()).equals("cg")){
            en_de_cipher.setText("ЗАШИФРОВАТЬ");
            en_de_cipher.setTextFill(Paint.valueOf("0x38a130ff"));
            cip="зашиф";
            en_de_cipher.setLayoutY(200.0);
            control_sum.setVisible(false);
            control_sum.setEditable(false);
        }
        else{
            en_de_cipher.setText("ДЕШИФРОВАТЬ");
            en_de_cipher.setTextFill(Paint.valueOf("0x38a130ff"));
            cip= "дешиф";
            en_de_cipher.setLayoutY(238.0);
            control_sum.setVisible(true);
            control_sum.setEditable(true);
        }
        System.out.println(file);
        fileName.setText(fName);
    }

    @FXML
    void keycode(InputMethodEvent event) {

    }

    @FXML
    private Button benchmark;

    @FXML
    private Text benchresult;

    @FXML
    private Text benchresult1;

    @FXML
    void startbench(ActionEvent event) throws IOException {
        Benchmark benchmarker = new Benchmark();
        benchmarker.bench();
        this.wSpeed = benchmarker.getwSpeed();
        System.out.println(wSpeed);
        this.rSpeed = benchmarker.getrSpeed();
        System.out.println(rSpeed);
        benchmark.setVisible(false);
        benchmark.setDisable(true);
        benchresult.setDisable(false);
        benchresult1.setDisable(false);
        double WSpeed = 0;
        double RSpeed=0;
        if (wSpeed > 1024) {
            WSpeed = wSpeed / 1024;
            benchresult.setText("Скорость записи в файл: " + Math.round(WSpeed) + " Кб в секунду");
        } else {
            WSpeed = wSpeed;
            benchresult.setText("Скорость записи в файл: " + Math.round(WSpeed) + " байт в секунду");
        }
        if (rSpeed > 1024) {
            RSpeed = rSpeed / 1024;
            benchresult1.setText("Скорость чтения из файла: : " + Math.round(RSpeed) + " Кб в секунду");
        } else {
            RSpeed = rSpeed;
            benchresult1.setText("Скорость чтения из файла: : " + Math.round(RSpeed) + " байт в секунду");
        }
    }

    @FXML
    void cipher(ActionEvent event) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        if (wSpeed==0){
            startbench(null);
        }
        String key = code_key.getText();
        if (!key.isEmpty() && !this.fName.isEmpty()) {
            ReadingFile readFromFile = new ReadingFile();
            Encrypter encrypter = new Encrypter();
            WritingFile writingFile = new WritingFile();
            FileInputStream in = new FileInputStream(this.file);
            if (cip.equals("зашиф")) {
                double time=fileSize/wSpeed+fileSize/rSpeed;
                FileOpener.opener(time);
                String extension= this.fName.substring(this.fName.lastIndexOf('.'));
                System.out.println("control read do encrypt "+extension);
                readFromFile.readingNOTCriptedFile(in, extension);
                byte[] control= ControlSumm.checksum(in);
                en_de_cipher.setLayoutY(238.0);
                control_sum.setVisible(true);
                control_sum.setText(new String(control));
                byte[] fileContent = readFromFile.fileContent();
                encrypter.KeyGen(key);
                SecretKey secretKey = encrypter.getSecretKey();
                writingFile.writingENcryptedFile(fileContent, this.fName.substring(0, this.fName.lastIndexOf('.')), secretKey, String.valueOf(this.file).substring(0, String.valueOf(this.file).lastIndexOf('\\')+1));
            } else {
                String checkSum = control_sum.getText();
                encrypter.KeyGen(key);
                SecretKey secretKey = encrypter.getSecretKey();

                readFromFile.readingENCriptedFile(in, secretKey);
                byte[] content = readFromFile.getContent();
                String extension = readFromFile.getExtension();
                String controlSumm = readFromFile.getControl();
                if (!controlSumm.equals("ОШИБКА!")) {
                    writingFile.writingDEcryptedFile(content, this.fName.substring(0, this.fName.lastIndexOf('.')),String.valueOf(this.file).substring(0, String.valueOf(this.file).lastIndexOf('\\')+1), extension);
                    if (!checkSum.equals("")) {
                        if (checkSum.equals(controlSumm)) {
                            en_de_cipher.setText("УСПЕХ!");
                            en_de_cipher.setLayoutY(200.0);
                            control_sum.setVisible(false);
                            control_sum.setEditable(false);
                        } else {
                            control_sum.setText("НЕ СОВПАЛА!");
                        }
                    } else {
                        control_sum.setText("НЕ УКАЗАНА!");
                    }
                }
                else{
                    en_de_cipher.setTextFill(Paint.valueOf("0xe12727ff"));
                    en_de_cipher.setText("ОШИБКА!");
                    code_key.setText("НЕВЕРНЫЙ КЛЮЧ!");
                }


            }
            System.out.println("УРА");
        }
        else{
            en_de_cipher.setText("ОШИБКА");
            en_de_cipher.setTextFill(Paint.valueOf("0xe12727ff"));
            code_key.setText("НЕ УКАЗАН КЛЮЧ!");
            System.out.println(en_de_cipher.getTextFill());
        }
    }

    @FXML
    void initialize() {
    }
}
