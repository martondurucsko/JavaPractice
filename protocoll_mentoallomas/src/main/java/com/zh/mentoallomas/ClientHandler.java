package com.zh.mentoallomas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private final Socket clientSocket;
    private final IranyitoMonitor monitor;

    public ClientHandler(Socket clientSocket, IranyitoMonitor monitor) {
        this.clientSocket = clientSocket;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(),true);)
        {
            String line ;
            while ((line = reader.readLine())!=null){
                System.out.println(line);

                String[] parts = line.trim().split("\\s+");
                if (parts.length>2){
                    System.out.println("a forma: [parancs - mento azonosíto]");
                    continue;
                }

                String command = parts[0]; //register,available,ALERT,DONE,STATE,QUIT


                if (command.equals("REGISTER")) {
                    String ambulanceId = parts[1];
                    //ok registered VAGY ERROR ALREADY REGISTERED
                    if (monitor.isRegistered(ambulanceId)) { //== //ha ez true
                        writer.println("ERROR already registered");

                    }else {
                        monitor.addToRegistereds(ambulanceId);
                        writer.println("OK REGISTERED");
                    }

                } else if (command.equals("AVAILABLE")) {
                    String ambulanceId = parts[1];
                    // OK AVAILABLE
                    //vagy
                    //unknown anbulance
                    //akkor available ha nem busy
                    if (!monitor.isRegistered(ambulanceId)){
                        writer.println("ERROR unknown ambulance");
                    }
                    else {
                        String response = monitor.available(ambulanceId);
                        writer.println(response);
                    }


                } else if (command.equals("ALERT")) {
                    String location = parts[1];
                    //kivesz egy mentot és rakuldi (ASSIGNED) mentoID + es a districtet (hogy kapja meg a districtet)
                    String response = monitor.alerted(location);
                    writer.println(response);


                } else if (command.contains("DONE")) {
                    String ambulanceId = parts[1];
                    String response = monitor.done(ambulanceId);
                    writer.println(response);


            } else if (command.equals("STATE")) {
                    String response = monitor.getState();
                    writer.println(response);

                } else if (command.equals("QUIT")) {
                    writer.println("OK BYE");
                    break;
                    // writer bye
                }

                else {
                    writer.println("ERROR unknown command");
                }

                /// protocol
                // REGITERT kap -> ha van szabad hely azt mondja OK
                // kliens bealll -> azt mondja available
                // available-ek kaphatnak alertet akkor ok kimennek
            }





        }catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        //protocoll
    }
}
