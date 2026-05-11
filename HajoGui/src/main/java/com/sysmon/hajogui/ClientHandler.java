package com.sysmon.hajogui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private final HarborMonitor monitor;
    private final Socket socket;

    public ClientHandler(Socket socket, HarborMonitor monitor){
        this.socket = socket;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        try
                (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true))
        {

            String line;
            while ((line= reader.readLine())!=null){
                System.out.println("Kliens üzenete: " + line);

                String[] parts = line.split(" ");
                if (parts.length<2){
                    writer.println("ERROR Bad format");
                    System.out.println("a forma: [parancs - hajo azonosíto]");
                    continue;
                }

                String command = parts[0];
                String boatId = parts[1];

                if (command.equals("ARRIVE")){
                    monitor.enterHarbor(boatId);
                    writer.println("OK-ARRIVED");

                } else if (command.equals("LEAVE")) {
                    monitor.leaveHarbor(boatId);
                    writer.println("OK-LEFT");
                } else if (command.equals("PARKED")) {
                    writer.println("OK-PARKED");
                } else {
                    writer.println("ERROR BAD COMMAND");
                }


                // ARRIVE
                //OK arriveD
                // LEAVE
                //OK LEFT
                //BAD COMMAND
            }


        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("ClientHandler megszakitva");
        }

    }
}
