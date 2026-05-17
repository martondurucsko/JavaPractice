package com.zh.mentoallomas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MentoautoClient implements Runnable{


    static int mentoAutokSzama = 6;

    public String getAmbulanceId() {
        return ambulanceId;
    }

    private final String ambulanceId;

    IranyitoMonitor monitor;

    MentoautoClient(String ambulanceId){
        this.ambulanceId = ambulanceId;
    }


    @Override
    public void run() {
        //egy mentoauto elete + protocoll
        try(Socket mentoautoSocket = new Socket("localhost",8989);
            BufferedReader reader = new BufferedReader(new InputStreamReader(mentoautoSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(mentoautoSocket.getOutputStream(),true);
            )
        {
            //protocoll
            // amiket o mondd az hogy register
            // amit kaphat ra hogy ok
            // - > beall
            // akkor erre o azt mondja hogy available
            // ha erre alertet kap akkor kimegy
            // eltelik ido azt mondja done

            String actId = this.getAmbulanceId();
            writer.println("REGISTER " + ambulanceId);

            String line;
            while ((line= reader.readLine())!=null) {

                //megkapja hogy ok registered
                if (line.equals("OK REGISTERED")) {
                    writer.println("AVAILABLE " + ambulanceId);
                } else if (line.contains("OK AVAILABLE")) {
                    Random random = new Random();
                    int number = random.nextInt(10);
                    writer.println("ALERT DISTRICT"+number);
                } else if (line.contains("OK ASSIGNED")) {
                    Thread.sleep(2000);
                    writer.println("DONE "+actId);
                }
                 else if (line.contains("ERROR")) {
                        writer.println("QUIT");
                        break;

                }
                 writer.println("STATE");
            }
            writer.println("QUIT");




        } catch (UnknownHostException e) {
            System.out.println("nem ismeros a host: " + e);
        } catch (IOException e) {
            System.out.println("baj van a olvasasiraskor "+ e);
        } catch (InterruptedException e) {
            System.out.println("EGYIK SZÁL lehalt" + e);
        }


    }



    public static void main(String[] args){
        //itt csinalok soksok mentoautot

        ExecutorService executor = Executors.newFixedThreadPool(mentoAutokSzama);
        for (int i = 0; i < mentoAutokSzama; i++) {
            String actAmbulanceId = "A"+i;

            executor.submit(new MentoautoClient(actAmbulanceId));

        }
        executor.shutdown();


    }

}
