package com.zh.restaurant;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ÉtteremLoader {
    Map <String, Rendelés> rendelesByVendeg = new HashMap<>();
    List <Vendég> vendegek = new ArrayList<>();
    List <Szakács> szakacsok = new ArrayList<>();
    public static int allSzakacsCount;

    public Étterem load(String file){
        //CSAK SZÉPSÉG MIATT hogy ne maradjon benn ha ketszer toltok file-t
        //----
        vendegek.clear();
        szakacsok.clear();
        rendelesByVendeg.clear();
        allSzakacsCount = 0;
        //-----

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String firstLine = reader.readLine();

            String[] firstParts = firstLine.trim().split("\\s+");
            int allVendegCount = Integer.parseInt(firstParts[0]); // vendegszam
            allSzakacsCount = Integer.parseInt(firstParts[1]);

            for (int i = 0; i < allVendegCount; i++) { // vegig megy soronként
                String orderLines = reader.readLine();
                String[] orderParts = orderLines.trim().split("\\s+"); //[vendegid][hanyorder][kajanev][kajaelkeszitesiido(ms)][kaja2][elkeszitesiido2]....

                String vendegId=orderParts[0];
                int rendelesDbPerVendeg=Integer.parseInt(orderParts[1]); // ez egy fontos szám

                List<Rendelés> rendelesLista = new ArrayList<>(); // ebbe rakjuk bele a rendeleseit az embernek

                for (int j = 0; j < rendelesDbPerVendeg; j++) { // azért mert ennyi db rendelese van ha kulsot hasznalom kifutna
                    String etelNev = orderParts[2 + j * 2]; //azért ketto :[0][1] [2]<-ez a 2 (*2 mindig a rakovetkezos)
                    int elkeszitesIdoInMs = Integer.parseInt(orderParts[3 + j * 2]);
                    Rendelés rendeles = new Rendelés(vendegId,etelNev,elkeszitesIdoInMs);  // ezt itt hozzáadtam
                    rendelesLista.add(rendeles);
                }
                //itt pedig egy vendeg et csinalok (vendegId, List<Rendeles> rendeleslistaja)

                // ezt kinn
                Vendég vendeg = new Vendég(vendegId,rendelesLista);
                vendegek.add(vendeg);
            }



        } catch (IOException e) {
            System.out.println("probléma a file betöltésekor:" + e);
        }


        return new Étterem(vendegek,allSzakacsCount);
    }

}
