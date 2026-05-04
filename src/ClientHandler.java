/* kell egy konstruktor a hashmaphez
    ezt a konstruktort megcsinálta a ITELLI
    de a hashmapet AI és talá azért kellett mert itt is kell egy amivel lokál dolgozunk
    de átveszi az értékét serverről ...idk


   AMI KELL IDE:
   1) socket kapcsolat serverrel
   2) wrtire/read a runban
   3) protocollok
   Az autók csatlakoznak a szerverhez,
    foglalnak egy szabad állomást,
    tankolnak,
    majd elmennek

*/


import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ClientHandler implements Runnable {

    private HashMap <String,Boolean> allomasok;

    Socket client;
    boolean isFree() {
        for (String kut : allomasok.keySet()) {
            if (allomasok.get(kut)==true){
                return true;
            }
        }
        return false;
    }


    synchronized String tankol() throws InterruptedException {
        //ha nincs szabad kut -> wait
        // kulönben tankol sleep (5000);
        // notify all

        /*
        ------------
        EZ több okból szar.
        1) rossz a ciklusok logikája
         - elobb kell trueba mennie a whilenak
            és aztán megtalálni a true-t megtalálni
        2) break után halott a kód
        -----------
        for (String kut : allomasok.keySet()){
            while (allomasok.get(kut)==false){
                wait();
            }
            allomasok.put(kut,false);
            break;
            Thread.sleep(5000); //tankol
            allomasok.put(kut,true);
            notifyAll();
        }

         */

        while (isFree()==false){
            wait();
        }
        String szabadKut = null;
        for (String kut: allomasok.keySet()){
            szabadKut = kut;
            allomasok.put(kut,false);
            break;
        }
        /*
        ------
        allomasok.put(szabadKut,true);
        notifyAll();

        ez túlzsúfolja csinálunk egy elenged()-et ahol
        ahol ez a logika:
        allomasok.put(szabadKut,true);
        notifyAll();
        -------
         */
        return szabadKut;
    }

    synchronized void elenged(String kut) { // nem kell interrupted exception mert nincs wait()
        allomasok.put(kut,true);
        notifyAll();
    }

    {
        try {
            client = new Socket("localhost",8989);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClientHandler(Socket socket, HashMap<String, Boolean> allomasok) {
        this.allomasok = allomasok;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter writer = new PrintWriter(client.getOutputStream(),true);

            String line;
            while ((line= reader.readLine())!= null){
                String kut = tankol();
                Thread.sleep(5000);
                elenged(kut);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
