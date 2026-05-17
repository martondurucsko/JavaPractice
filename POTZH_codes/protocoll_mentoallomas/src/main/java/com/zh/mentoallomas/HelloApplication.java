package com.zh.mentoallomas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    IranyitoMonitor monitor = new IranyitoMonitor();


    TextArea stateArea = new TextArea("STATE:\n");
    TextArea logArea = new TextArea("LOG AREA: \n");

    boolean isStarted = false;

    @Override
    public void start(Stage stage) throws IOException {

        VBox root = new VBox(10);
        Scene scene = new Scene(root, 620, 540);

        Button startServer = new Button("START SERVER");
        Button refreshButton = new Button("REFRESH");

        stateArea.setEditable(false);
        logArea.setEditable(false);
        /// fieldek seteditable

        /// buttonok

        /// setonaction -> is running false check
        // servernek kell egy olyan hogy Thread(server::server).start



        startServer.setOnAction(e->{
            try {

                if (isStarted){
                    logArea.setText("simulation already started");
                    return;
                }
                isStarted=true;
                MentoIranyitoServer server = new MentoIranyitoServer(8989,monitor);
                //new Thread(server::start).start();
                Thread serverThread = new Thread(server::start);
                serverThread.setDaemon(true);
                serverThread.start();

            } catch (Exception ex) {
                monitor.addLog("baj van elinditaskor" + ex);
            }

        });

        refreshButton.setOnAction(e->refreshGui());


        /// addchildren.addall


        root.getChildren().addAll(startServer,refreshButton,logArea,stateArea);

        stage.setTitle("mentoallomas");
        stage.setScene(scene);
        stage.show();
    }

    private void refreshGui() {
        logArea.setText(monitor.addLog("GUI REFRESHED"));
        stateArea.setText(monitor.getState());
    }

    public static void main(String[] args){
        launch(args);
    }
}
