package com.zh.restaurant;

import java.util.ArrayList;
import java.util.List;

public class Szakács implements Runnable{
    public final int allSzakacs;

    public List<String> getSzakacsok() {
        return szakacsok;
    }

    ///  be kell settelni hogy minden szakacson vegigmegyunk es adott szakacsnak ez az etterme

    List <String> szakacsok = new ArrayList<>();

    List<Thread> szakacsThread = new ArrayList<>();
    private Étterem etterem;
    Rendelés rendeles;

    Szakács(int allSzakacs){ //=3  és csinál egy szakacs listat amiben szakacsok vannak
        this.allSzakacs = allSzakacs;

        for (int i = 1; i <= allSzakacs; i++) {
            String szakacsId = "SZ" + i;
            szakacsok.add(szakacsId);
        }

    }


    public void setEtterem(Étterem etterem){
        this.etterem = etterem;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Rendelés act = etterem.keszit(this);
                if (act ==  null){
                    break;
                }
                Thread.sleep(act.elkeszitesiIdoInMs);

                etterem.elkeszult(act);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
