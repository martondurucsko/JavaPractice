package com.sysmon.hajogui;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;


public class HelloApplication extends Application {

    private HarborMonitor monitor;
    //
    private TextArea displayArea;
    // ------
    //kell egy ilyen
    protected TextField text;

    @Override
    public void start(Stage stage) throws IOException {

        monitor = new HarborMonitor(6);

        // inditom a servert külön szálon
        KikotoServer server = new KikotoServer(8989,monitor);
        Thread serverThread = new Thread(server::start);
        serverThread.setDaemon(true); // ha leáll a gui azis leáll
        serverThread.start();

        displayArea = new TextArea();
        displayArea.setEditable(false); // MINDIG FALSE
        displayArea.setPrefColumnCount(20);

        // refresh fuggveny
        Button refreshButton = new Button("REFRESH");
        refreshButton.setOnAction(event -> refresh());

        Button addNewBoat = new Button("6 uj hajo");
        addNewBoat.setOnAction(actionEvent -> startNewBoat());

        VBox root = new VBox(10);
        root.getChildren().addAll(displayArea,refreshButton,addNewBoat);


        stage.setTitle("HARBOR MONITOR GUI");
        stage.setScene(new Scene(root,500,500));


        stage.show();
        refresh();
        startNewBoat();
        //kattra bezár
        stage.setOnCloseRequest(event -> System.exit(0));






        //megnéz:
        //root.getChild().add(....)
        //text.setOnAction(actionEvent -> {String hajoId = text.getText();});
    }

    private void startNewBoat() {
        int ujHajokSzáma= 7 ;
        for (int i = 0; i < ujHajokSzáma; i++) {
            String boatId = "H" + i + "_" + System.currentTimeMillis() % 10000;
            new Thread(new HajoClient(boatId)).start();

        }
    }

    private void refresh(){
        //snapshot ami megkapja a LOGIKA snapshotját
        Map<Integer,String> snapshot = monitor.snapshot();

        StringBuilder sb = new StringBuilder();

        sb.append("KIKOTO ALLAPOT\n\n"); // miért kell két \n

        for (int i = 0; i < 6; i++) {
            String boat = snapshot.get(i); // kiveszi az i-hez (kikoto) tartozo stringet
            if (boat == null){
                sb.append("KIKOTO ").append(i).append(": ures\n");
            }
            else {
                sb.append("KIKOTO").append(i).append(" : ").append(boat).append("\n");  // kikoto : hajoId
            }

        }

            sb.append("\nLOG\n");
            sb.append(monitor.logSnapshot());

            displayArea.setText(sb.toString());
    }
    public static void main(String[] args) {
        launch(args);
    }
}
