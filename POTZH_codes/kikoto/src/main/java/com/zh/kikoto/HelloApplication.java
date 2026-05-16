package com.zh.kikoto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;

public class HelloApplication extends Application {
    TextArea logArea = new TextArea("LOGS: \n");
    TextArea statusArea = new TextArea("STATUS: \n");
    HarbourMonitor monitor;
    private boolean serverStarted = false;
    @Override
    public void start(Stage stage) throws IOException {

        VBox root = new VBox(10);
        Scene scene = new Scene(root, 520, 440);
        logArea.setEditable(false);
        statusArea.setEditable(false);



        Button startButton=new Button("START");
        Button refreshButton = new Button("REFRESH");

        startButton.setOnAction(e->{
            if (serverStarted){
                logArea.appendText("server already running");
                return;
            }
            monitor = new HarbourMonitor(3);

            Thread serverThread = new Thread(new Server(8989,monitor));
            serverThread.setDaemon(true);
            serverThread.start();

            serverStarted=true;
            logArea.appendText("SERVERSTARTED\n");
        });

        refreshButton.setOnAction(e->refreshGui());

        //root.getChildren addall
        root.getChildren().addAll(logArea,statusArea,refreshButton,startButton);

        stage.setTitle("KIKOTO GUI");
        stage.setScene(scene);
        stage.show();
    }

    private void refreshGui() {
        if (monitor == null) {
            logArea.setText("server not started yet");
            statusArea.setText("no state yet");
            return;
        }
        logArea.setText(monitor.getLogsToString());
        statusArea.setText(monitor.getStatus());
    }
    public static void main(String[] args){
        launch(args);
    }
}
