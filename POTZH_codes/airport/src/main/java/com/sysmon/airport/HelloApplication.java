package com.sysmon.airport;

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

public class HelloApplication extends Application {
    private final TextField fileField = new TextField("airport_1.txt");
    private final TextArea textArea = new TextArea();
    private final AirportSimulationManager manager = new AirportSimulationManager();
    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox(10);
        Scene scene = new Scene(root, 520, 540);
        stage.setTitle("AIRPORT GUI");

        Button startButton = new Button("start");
        Button loadButton = new Button("load");
        Button stopButton = new Button("stop");
        Button chooseButton = new Button("chose file");
        Button refreshButton = new Button("refresh");

        textArea.setEditable(false);

        startButton.setOnAction(e->{
            try {

                manager.startSimulation();
                textArea.setText("simulation started");
            } catch (Exception ex) {
                textArea.setText("start error: " + ex.getMessage());
            }
        });

        loadButton.setOnAction(e->{
            try {

                manager.load(fileField.getText());
                textArea.setText(manager.getStateText());
            } catch (Exception ex) {
                textArea.setText("loaderror" + ex.getMessage());
            }
        });

        stopButton.setOnAction(e->{
            manager.stopsimulation();
            textArea.setText("simulation stopped");
        });

        chooseButton.setOnAction(e->{
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);

            if (file!=null){
                fileField.setText(file.getPath());
            }

        });

        refreshButton.setOnAction(e->{
            textArea.setText(manager.getStateText());

        });


        root.getChildren().addAll(startButton,loadButton,stopButton,chooseButton,refreshButton,fileField,textArea);
        stage.setScene(scene);
        stage.show();
    }

public static void main(String[] args){
        launch(args);
}

}
