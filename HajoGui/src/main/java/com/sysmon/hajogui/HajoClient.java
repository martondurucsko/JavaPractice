package com.sysmon.hajogui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HajoClient implements Runnable{
    private final String hajoId;

    public HajoClient(String hajoId){
        this.hajoId = hajoId;
    }

    @Override
    public void run() {
        try (Socket hajoSocket = new Socket("localhost", 8989);
        BufferedReader reader = new BufferedReader(new InputStreamReader(hajoSocket.getInputStream()));
        PrintWriter writer = new PrintWriter(hajoSocket.getOutputStream(), true)) {


            String line;
            writer.println("ARRIVE " + hajoId); // hajo érkezett

            while ((line=reader.readLine())!=null){
                if (line.contains("ERROR")){
                    return;
                }
                if (line.equals("OK-ARRIVED")){
                    Thread.sleep(3000); // itt odaparkol
                    writer.println("PARKED");
                } else if (line.equals("OK-PARKED")) {
                    writer.println("LEAVE " + hajoId);
                    System.out.println("a server válasza: " + line);
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(hajoId + "megszakitva");
        }
        //életállapot
    }

    public static void main(String[] args){
        //n et létrehozok
        int allHajo = 5;
        ExecutorService executor = Executors.newFixedThreadPool(allHajo);
        for (int i = 0; i < allHajo; i++) {
            String id = "HU" + i;
            executor.submit(new HajoClient(id));
        }
        executor.shutdown();
    }
}
