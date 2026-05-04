/*
    Benzinkut
    Van egy benzinkút, rajta 3 töltőállomás.
    Az autók csatlakoznak a szerverhez,
    foglalnak egy szabad állomást,
    tankolnak,
    majd elmennek

    több szál, -> minden kocsi uj szál és van 3 töltőállomás szál
    KLIENS SZERVER KLIENSHANDLER

    WAIT MMODEL - szabad töltőállomás

    HASHMAP:
    kut 1 - free
    kut 2 - free
    kut 3 - free
    free = trure
    occupied = false

    ÖTLET:
    SERVER létrehoz egy portot és nyitja accept
    HASHMAP

    CLIENTHANDLERBEN:
    PROTOCOL

    CLIENT
    MEGCSINÁLJA a kocsikat és csatlakozik a clienthandlerhez


 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    static HashMap<String, Boolean> allomasMap = new HashMap<>(); // generikus tipus a BAL OLDALON



    public static void main(String[] args) {
        allomasMap.put("kut1",true);
        allomasMap.put("kut2",true);
        allomasMap.put("kut3",true);

        ServerSocket petrolServer;

        {
            try {
                petrolServer = new ServerSocket(8989);


                /*
                ----
                EZ AZ EGÉSZ A CLIENT HANDLER FELADATA:
                -----
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream())); // ez a clientet olvassa
                PrintWriter writer = new PrintWriter(client.getOutputStream(), true); //PRINTWRITER !!!!

                //olvasói rész while

                String line;
                while ((line = reader.readLine()) != null) {

                }
                */

                while (true){ //mindig uj kliensre vár
                    Socket client = petrolServer.accept(); //  EZ NEM MENT EGYBŐL - EZT MEG KELL TANULNI
                    new Thread(new ClientHandler(client,allomasMap)).start();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
