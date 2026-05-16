package com.zh.restaurant;

//ez a megoosztott eroforras ide jonnek majd a

import java.util.*;

/// synchronized logika
// queue kell ami nek van
    // ALLAPOTOK:
    // [sef oldalrol] : készít - ha a rendelesqueban van kaja -> és csökkenti a rendelésnek a itemCountját
    // elkészült - ha a minden kaja a (current)  rendelesbol elkeszult (rendelésnek van ételcountja) ha az 0 elkészült
    // [Vendég oldalról]: bead -

public class Rendelés {
    final int elkeszitesiIdoInMs;
    final String etelNev;
    final String vendegId;
    private Map <String, Rendelés> rendelesByVendeg = new HashMap<>();



    Rendelés(String vendegId, String etelNev, int elkeszitesiIdoInMs){
        this.etelNev = etelNev;
        this.elkeszitesiIdoInMs = elkeszitesiIdoInMs;
        this.vendegId = vendegId;
    }







}
