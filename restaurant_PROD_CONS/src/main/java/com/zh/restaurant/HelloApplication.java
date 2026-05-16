package com.zh.restaurant;

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
    TextField fileField = new TextField("inputfile");
    TextArea logArea = new TextArea("logarea");
    TextArea statusArea = new TextArea("statusAREA");
    EtteremManager manager=new EtteremManager();


    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox(10);
        Scene scene = new Scene(root, 520, 440);



        logArea.setEditable(false);
        statusArea.setEditable(false);

        /// gombok
        Button loadButton = new Button("LOAD");
        Button fileChooserButton = new Button("CHOOSE FILE");
        Button startButton = new Button("START");
        Button stopButton = new Button("STOP");
        Button refreshButton = new Button("REFRESH");

        fileChooserButton.setOnAction(e->{
            try {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(stage);

                if (file == null) {
                    manager.addLog("BAJ VAN A FILEAL");
                    return;
                }
                fileField.setText(file.getPath());
            } catch (Exception ex) {
                logArea.appendText("baj a filebetoltesekor: " + ex);
            }
        });

        loadButton.setOnAction(e->{
            try{
                manager.loadSimulation(fileField.getText());
                logArea.setText("file loaded");
                refreshGui();
            } catch (Exception ex) {
                logArea.appendText("GÁZ"+ex);
            }
        });


        startButton.setOnAction(e->{
            try {
                manager.startsimulation();
                logArea.setText("SIM STARTED");
                refreshGui();
            } catch (Exception ex) {
                logArea.appendText("PARA inditaskor + "+ex);
            }
        });

        stopButton.setOnAction(e->{
            try {
                manager.stopSimulation();
                logArea.setText("SIM STOPPED");
                refreshGui();
            } catch (Exception ex) {
                logArea.setText("PARA allitaskor + "+ex);
            }
        });

        refreshButton.setOnAction(e->{
            try {
                refreshGui();

            } catch (Exception ex) {
                logArea.setText("PARA"+ex);
            }
        });

        /// setonactiion
        //try catchekre figyelni ha nincs betoltbe akkor ne lehessen
        // try catch ha mar be van toltve akkor ne menjen uj

        /// root.getChildren.Addall(összes+fieldek)

        root.getChildren().addAll(loadButton,startButton,fileChooserButton,stopButton,refreshButton,fileField,statusArea,logArea);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public void refreshGui(){
        statusArea.setText(manager.getStatus());
        logArea.setText(manager.logs.toString());
        //try catchre figyelni ha nincs betoltve akkor returnoljon
    }

    public static void main(String[] args){
        launch(args);
    }

}
