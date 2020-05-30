package sample;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileChooser {
    private String fileName;
    private File file;
    private long fileSize;

    public void open() throws IOException {
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Открытие");//Заголовок диалога
        File file = fileChooser.showOpenDialog(null);//Указываем текущую сцену CodeNote.mainStage
        this.fileName = file.getName();
        this.file = file;
        this.fileSize=file.length();


//        if (file != null) {
//            if (file.length() < 1000) {
//                byte[] input = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
//                ENorDE_crypt eNorDE_crypt = new ENorDE_crypt();
//            } else {
//                Alert.bigFileAlert(file);
    }
    public String getFileName(){
        return this.fileName;
    }
    public File getFile(){
        return  this.file;
    }
    Long getSize(){return this.fileSize;}
}