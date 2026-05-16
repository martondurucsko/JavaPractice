package com.zh.restaurant;

import java.util.ArrayList;
import java.util.List;

public class Vendég implements Runnable{
    Rendelés rendeles;
    private final String vendegId;

    public List<Rendelés> getRendelesListaja() {
        return rendelesListaja;
    }

    public String getVendegId() {
        return vendegId;
    }

    private final List<Rendelés> rendelesListaja;
    private Étterem etterem;
    ///  be kelll setelni hogy ennek a vendégnek a setEttremje a this etterem

    List<Thread> vendegThread = new ArrayList<>();

    Vendég (String vendegId, List<Rendelés> rendelésListaja){
        this.vendegId = vendegId;
        this.rendelesListaja = rendelésListaja;
    }

    public void setEtterem(Étterem etterem){
        this.etterem = etterem;
    }


    @Override
    public void run() {
        try{
            // vendeg berakja a
            etterem.megrendel(this);

            etterem.megkap(vendegId);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
