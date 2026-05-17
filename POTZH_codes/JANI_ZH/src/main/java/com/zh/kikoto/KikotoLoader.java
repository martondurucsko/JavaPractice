package com.zh.kikoto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KikotoLoader {
    List<ViziFolyoso> viziFolyosoList = new ArrayList<>();
    Map<String,ViziFolyoso> vizifolyosoByName = new HashMap<>();
    Map <String,ViziFolyoso> viziFolyosoByHajo = new HashMap<>();
    List <Hajo> hajok = new ArrayList<>();
    Map<Integer, Kikoto> kikotoById = new HashMap<>();



    public KikotoManager load(String inputFile){
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))){

            String firstLine = reader.readLine();
            String[] firstParts = firstLine.trim().split("\\s+"); // [kikoto] [vizifolyoso] [hajo]

            int allKikotoCount = Integer.parseInt(firstParts[0]);
            int allViziFolyoso = Integer.parseInt(firstParts[1]);
            int allHajo = Integer.parseInt(firstParts[2]);

            //kikoto db kikoto
            for (int i = 0; i < allKikotoCount; i++) {
                int dockId = i;
                Kikoto kikoto = new Kikoto(dockId);
                kikotoById.put(dockId,kikoto);
            }

            //vizifolyosok
            for (int i = 0; i < allViziFolyoso; i++) {
                String viziFolyosoLine = reader.readLine();
                String[] viziFolyosoParts = viziFolyosoLine.trim().split("\\s+");
                if (viziFolyosoParts.length!=4){
                    throw new Exception("A vizifolyosok leirasanak negy adatboll kell aljon");
                }

                int startPoint = Integer.parseInt(viziFolyosoParts[0]);  //kikotoId
                int endPoint = Integer.parseInt(viziFolyosoParts[1]);    //kikotoId
                String viziFolyosoNev = viziFolyosoParts[2];
                int travelTimeInMs = Integer.parseInt(viziFolyosoParts[3])*1000;

                ViziFolyoso viziFolyoso = new ViziFolyoso(startPoint,endPoint,viziFolyosoNev,travelTimeInMs); // elkeszult az objectum
                viziFolyosoList.add(viziFolyoso);  // bekerul a listaba
                vizifolyosoByName.put(viziFolyosoNev,viziFolyoso);
            }
            
            
            //hajok
            for (int i = 0; i < allHajo; i++) {
                String hajoLines = reader.readLine();
                String[] hajoParts = hajoLines.trim().split("\\s+"); // [routelenght] [routesString] ....

                String hajoId = "H" + (i+1); // H1

                int hajoUtHossz = Integer.parseInt(hajoParts[0]);
                List <ViziFolyoso> route = new ArrayList<>();

                for (int j = 0; j < hajoUtHossz; j++) {
                    String viziFolyosoNev = hajoParts[j+1];
                    ViziFolyoso actFolyoso = vizifolyosoByName.get(viziFolyosoNev);
                    route.add(actFolyoso);
                }

                Hajo ujHajo = new Hajo(hajoId,route);
                hajok.add(ujHajo); //bekerult a listaba
                
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new KikotoManager(hajok,viziFolyosoList, kikotoById);
    }
}
