package sample;

import javafx.concurrent.WorkerStateEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FileOpener {
    public static void opener(double time){
        Thread thread = null;
        Stage newWindow = new Stage();
        Progress progress = new Progress(time);
        Label label = new Label("Подождите, операция выполняется!");
        ProgressBar progressBar = new ProgressBar(0);

        final Label statusLabel = new Label();
        statusLabel.setMinWidth(250);
        statusLabel.setTextFill(Color.BLUE);

        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);

        root.getChildren().addAll(label, progressBar, //
                statusLabel);
        Scene scene = new Scene(root, 320, 120, Color.WHITE);
        newWindow.setTitle("Обрабатываю файл...");
        newWindow.setResizable(false);
        newWindow.setScene(scene);
        newWindow.show();
////////////////////////////////////////////////////
        progressBar.setProgress(0);

        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(progress.progressProperty());
        statusLabel.textProperty().unbind();
        statusLabel.textProperty().bind(progress.messageProperty());
        Thread finalThread = thread;
        progress.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
                t -> {
                    String copied = progress.getValue();
                    newWindow.close();
                });

        thread = new Thread(progress);
        thread.start();

    }

}
