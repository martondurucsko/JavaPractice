package com.sysmon.hajogui;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class KikotoServer {
    private final HarborMonitor monitor;
    private final int port;


    //gui miatt kell port is
    public KikotoServer(int port, HarborMonitor monitor){
        this.port = port;
        this.monitor=monitor;
    }
    // gui miatt
    public void start(){
        try
                (ServerSocket guisServer = new ServerSocket(port);)
        {

                System.out.println("server elindult ezen a proton: " + port);

                while (true){
                    Socket socket = guisServer.accept();

                    Thread t = new Thread(new ClientHandler(socket,monitor));
                    t.start();
                    System.out.println("kliens csatlakozott");
                }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args){
        HarborMonitor monitor = new HarborMonitor(6);
        KikotoServer kikotoServer = new KikotoServer(8989,monitor);
        kikotoServer.start();
        System.out.println("server elindult konzolon");
    }

}
