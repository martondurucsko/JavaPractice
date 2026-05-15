package com.zh.hospital;

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
    //ez lehet nem kell TESTeljuk
    //private final HospitalLoader loader = new HospitalLoader();
    TextField fileField = new TextField();
    TextArea logArea = new TextArea("logarea");
    TextArea stateArea = new TextArea("statearea");

    HospitalSimulationManager manager = new HospitalSimulationManager();
    HospitalLoader loader = new HospitalLoader();

    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox(10);
        Scene scene = new Scene(root, 520, 440);

        Button startButton = new Button("START");
        Button loadButton = new Button("LOAD");
        Button refreshButton = new Button("REFRESH");
        Button stopButton = new Button("STOP");
        Button chooseButton = new Button("CHOOSE");

        chooseButton.setOnAction(e->{

                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    fileField.setText(file.getPath());
                }

        });



        loadButton.setOnAction(e->{
            try {
                manager.loadHospital(fileField.getText()) ;
                logArea.setText("FILE loaded:" + fileField.getText());
                refreshGui();
            }catch (Exception ex){
                logArea.setText("problema a file betoltesekor" + ex);
                System.out.println("baj van fileal" + ex);
            }
        });

        startButton.setOnAction(e->{
            try {
                manager.startSimulation();
                logArea.setText("SIM STARTED");
                refreshGui();
            } catch (Exception ex) {
                logArea.setText("STRAT GOMB LENYOMASAKO problema" + ex);
            }
        });

        stopButton.setOnAction(e->{
            try {
                manager.stopSimulation();
                logArea.setText("simulation stopped");
                refreshGui();
        } catch (Exception ex) {
            logArea.setText("STOP GOMB LENYOMASAKO problema" + ex);
        }
        });

        refreshButton.setOnAction(e->{
            try {
                logArea.setText("gui refreshed");
                refreshGui();

            } catch (Exception ex) {
                logArea.setText("REFRESH GOMB LENYOMASAKO problema" + ex);
            }
        });
        /// buttons
        /// setonaction

        /// root.getcild.addall(fileField, textarea (/logarea))

        /// refreshGui() -> logarea.setText(getSnapshot()) -  ez a getState nem??

        root.getChildren().addAll(chooseButton,startButton,stopButton,loadButton,refreshButton,stateArea,logArea,fileField);
        stage.setTitle("Hospital GUI");
        stage.setScene(scene);
        stage.show();
    }
    public void refreshGui(){
        stateArea.setText(manager.getHospitalState());
        logArea.setText(manager.getHospitalLogs());
    }
    public static void main (String[] args){
        launch(args);
    }
}
