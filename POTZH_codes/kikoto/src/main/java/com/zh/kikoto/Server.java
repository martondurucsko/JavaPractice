package com.zh.kikoto;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

    private final int port;
    private final HarbourMonitor monitor;

    public Server(int port, HarbourMonitor monitor){
        this.port = port;
        this.monitor = monitor;
    }


    @Override
    public void run() {
        //try catch
        //while
        //accept loop
        // GUI -> ServerSocket serverSocket = new ServerSocket(8989);

        try (ServerSocket guiServer = new ServerSocket(port);){
            Socket clientSocket;
            while (true){
                Socket socket = guiServer.accept();
                monitor.addLog("Kliens csatlakozott:" + socket.getInetAddress());
                Thread t = new Thread(new ClientHandler(socket, monitor));
                t.start();
                //monitor add log kliens csatlakozott

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}
