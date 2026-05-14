package com.zh.cargohub;

import javafx.application.Application;
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

    private final TextArea logArea = new TextArea();
    private final TextArea statusArea = new TextArea();

    private final TextField fileField = new TextField();
    private final SimulationManager manager = new SimulationManager();

    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox(10);
        Scene scene = new Scene(root, 620, 540);

        Button startButton = new Button("START");
        Button stopButton = new Button("STOP");
        Button loadButton = new Button("LOAD");
        Button chooseFileButton = new Button("CHOOSE FILE");
        Button refreshButton = new Button("REFRESH");

        logArea.setEditable(false);
        statusArea.setEditable(false);

        chooseFileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);

            if (file == null) {
                logArea.setText("No file selected");
                return;
            }

            fileField.setText(file.getAbsolutePath());
            logArea.setText("File chosen: " + file.getAbsolutePath());
        });

        loadButton.setOnAction(e -> {
            try {
                if (fileField.getText().isBlank()) {
                    logArea.setText("No file selected");
                    return;
                }

                File file = new File(fileField.getText());
                manager.loadScenario(file.toPath().toString());

                refreshGui();

            } catch (IOException ex) {
                logArea.setText("File loading error: " + ex.getMessage());
            } catch (Exception ex) {
                logArea.setText("Error: " + ex.getMessage());
            }
        });

        startButton.setOnAction(e -> {
            manager.startSimulation();
            refreshGui();
        });

        stopButton.setOnAction(e -> {
            manager.stopSimulation();
            refreshGui();
        });

        refreshButton.setOnAction(e -> {
            refreshGui();
        });

        root.getChildren().addAll(
                chooseFileButton,
                fileField,
                loadButton,
                startButton,
                stopButton,
                refreshButton,
                statusArea,
                logArea
        );

        stage.setTitle("Cargo Hub");
        stage.setScene(scene);
        stage.show();
    }

    private void refreshGui() {
        logArea.setText(manager.getLogsasText());
        statusArea.setText(manager.getSnaphot().toString()); // csak emiatt kell a statusarea
    }

    public static void main(String[] args) {
        launch(args);
    }
}