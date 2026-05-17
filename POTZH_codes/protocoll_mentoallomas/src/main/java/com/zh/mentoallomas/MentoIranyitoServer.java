package com.zh.mentoallomas;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MentoIranyitoServer implements Runnable{

    private final IranyitoMonitor monitor;
    private final int port;

    MentoIranyitoServer(int port, IranyitoMonitor monitor){
        this.port = port;
        this.monitor = monitor;
    }


    public void start(){
        // guiserver
        // accept loop
        // while true
        try (ServerSocket mentoIranyitoServer = new ServerSocket(port)){

            while (true){
                Socket clientSocket = mentoIranyitoServer.accept();
                Thread serverThread = new Thread(new ClientHandler(clientSocket,monitor));
                serverThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void run() {
        this.start();
    }
}
