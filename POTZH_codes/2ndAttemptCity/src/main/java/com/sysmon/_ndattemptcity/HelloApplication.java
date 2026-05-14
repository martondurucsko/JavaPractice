package com.sysmon._ndattemptcity;

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
    TextArea textArea = new TextArea();
    TextField textField = new TextField();
    private City city;
    private CityLoader loader;

    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox(10);

        Button Startsimulation = new Button("START");
        Startsimulation.setOnAction(e -> {
            if (city == null) {
                textArea.setText("First load a simulation file.");
                return;
            }

            city.startSimulation();
            textArea.appendText("\nSimulation started.");
        });

        Button loadsimulation = new Button("LOAD");

        loadsimulation.setOnAction(e-> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                try {
                    city = CityLoader.load(file.getPath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

                });
        

        textField = new TextField("korforgalmak_1.txt");

        textArea.setEditable(false);


        //root.getChildren().add()
        root.getChildren().addAll(textField,textArea,Startsimulation,loadsimulation);
        Scene scene = new Scene(root, 500, 540);
        stage.setTitle("City manager!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }


}
