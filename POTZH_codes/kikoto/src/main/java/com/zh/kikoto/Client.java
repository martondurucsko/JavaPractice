package com.zh.kikoto;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client implements Runnable{
    public final String boatId;
    public static int boatCount = 7;



    Client(String boatId){
        this.boatId=boatId;
    }

    @Override
    public void run() {
        //try catch
        //csatlakozik a serverhez
        //elet+protocol
        try (Socket clientSocket = new Socket("localhost",8989);
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(),true);
        ){
            writer.println("ARRIVE " + boatId);
            System.out.println("hajo erkezett ID: " + boatId);
            String response = reader.readLine();
            if (response!=null && response.equals("OK")){
                Thread.sleep(2000);
                writer.println("LEAVE " +boatId);
                reader.readLine();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        //elindit n db hajot
        ExecutorService executor = Executors.newFixedThreadPool(boatCount);

        for (int i = 1; i <= boatCount; i++) {
            String boatId = "B"+i;
            executor.submit(new Client(boatId));
        }
        executor.shutdown();


    }


}
