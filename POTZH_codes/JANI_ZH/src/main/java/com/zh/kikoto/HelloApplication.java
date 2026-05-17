package com.zh.kikoto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HelloApplication extends Application {


    private KikotoManager manager = new KikotoManager();

    TextArea logArea = new TextArea("LOGS");
    TextArea statusArea = new TextArea("status");
    TextField fileField = new TextField("inputFile");


    KikotoLoader loader = new KikotoLoader();

    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox(10);
        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("KIKOTO SIM");

        /// fieldek seteditable(false)

        logArea.setEditable(false);
        statusArea.setEditable(false);
        fileField.setEditable(false);

        Button loadButton = new Button("LOAD");
        Button fileChosserButton = new Button("CHOOSE FILE");
        Button startButton = new Button("START");
        Button stopButton = new Button("STOP");

        startButton.setOnAction(e->{
            try{
                manager.startSimulation();
            } catch (Exception ex) {
                logArea.setText("inditaskor baj:" + ex);
            }

        });
        fileChosserButton.setOnAction(e->{
            try {

                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(stage);

                if (file==null){
                    return;
                }

                fileField.setText(file.getAbsolutePath());


            } catch (Exception ex) {
                manager.addLog("baj a filevalasztasakor:" + ex);
            }
        });

        loadButton.setOnAction(e->{
            try {
                manager=loader.load(fileField.getText());


            } catch (Exception ex) {
                logArea.setText("betolteskor para: " + ex);
            }
        });

        stopButton.setOnAction(e->{
            try {
                manager.stopSimulation();
            } catch (Exception ex) {
                logArea.setText("para leallitaskor: " + ex);
            }
        });

        /// button

        /// setonaction - try catch !!  - choosenal / loadnal absolutefieldfield

        /// root.addchildren.addall(fielaek, gombok)


        /// refresh gui-> logarea statusArea (status lehet nem is kell)

        root.getChildren().addAll(loadButton,fileChosserButton,startButton,stopButton,fileField,logArea,statusArea);
        stage.setScene(scene);
        stage.show();
    }


    public static void main (String[] args){
        launch(args);
    }

}
