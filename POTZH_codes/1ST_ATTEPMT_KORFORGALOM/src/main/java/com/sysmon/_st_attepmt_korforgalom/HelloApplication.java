package com.sysmon._st_attepmt_korforgalom;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private final TextArea textArea = new TextArea();
    private final SimulationManager manager = new SimulationManager();

    @Override
    public void start(Stage stage) throws IOException {

        Button loadButton = new Button("Load");
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");

        TextField fileField = new TextField("korforgalmak_1.txt");

        textArea.setEditable(false);


        loadButton.setOnAction(e -> {
            try {
                manager.load(fileField.getText());
                textArea.setText(manager.getStateText());
            } catch (Exception ex) {
                textArea.setText("Load error: " + ex.getMessage());
            }
        });

        startButton.setOnAction(e -> {
            try {
                manager.startSimulation();
                textArea.appendText("\nSimulation started\n");
            } catch (Exception ex) {
                textArea.appendText("\nStart error: " + ex.getMessage() + "\n");
            }
        });

        stopButton.setOnAction(e -> {
            manager.stopSimulation();
            textArea.appendText("\nSimulation stopped\n");
        });



        VBox root = new VBox(10);
        root.getChildren().addAll(fileField, loadButton, startButton, stopButton, textArea);

        Scene scene = new Scene(root, 620, 440);
        stage.setTitle("Roundabout SIM");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args){
        launch();
    }
}
