package com.zh.kikoto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private final Socket socket;
    private final HarbourMonitor monitor;

    public ClientHandler(Socket socket, HarbourMonitor monitor) {
        this.socket = socket;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        //try catch-ek egyik a threadre masik a sokcet kapcsra

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
        ){
            //protocol
            String line;
            while ((line = reader.readLine())!=null){
                String[] parts = line.trim().split("\\s+");
                if (parts.length!=2){
                    monitor.addLog("BAD FORMAT - kikoto - hajo protocoll csak 2 adat lehet [command] [nev]");
                    continue;
                }
                String command = parts[0];
                String boatId = parts[1];
                if (command.equals("ARRIVE")){
                    monitor.enteringHarbour(boatId);
                    writer.println("OK");
                    monitor.addLog(boatId +" has entered the harbour");
                } else if (command.equals("LEAVE")) {
                    monitor.leaveHarbour(boatId);
                    writer.println("OK");
                    monitor.addLog(boatId + " has left the harbour");
                }
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
