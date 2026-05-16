package com.zh.restaurant;

import java.util.*;

public class Étterem {
    private final List<Vendég> vendegek;
    private final List<Szakács> szakacsok = new ArrayList<>();
    private final List<Thread> vendegThreads = new ArrayList<>();
    private final List <Thread> szakacsThreads = new ArrayList<>();

    private final int allSzakacsCount;

    int totalOrders = 0;
    int finishedOrders = 0;

    private int submittedGuestCount = 0;

    // gui miatt kell - fontos szép huzás +pont
    private boolean started = false;

    Queue<Rendelés> rendelesSor = new LinkedList<>();
    Map <String, Integer> hatralevoKajakByVendeg = new HashMap<>();


    // itt vanmak a methodusok amiket majd a étterem manager meghiv és adja a GUINAK
    // itt érnek össze a adatszerkezetek még kell csinálni neki egy konstrutort is
    // és egy setEtterem fuggvenyt is
    // neki van loaderben egy loadja

    public Étterem(List<Vendég> vendegek, int allSzakacsCount) {
        this.vendegek=vendegek;
        this.allSzakacsCount = allSzakacsCount;

        for (Vendég vendeg:vendegek){
            vendeg.setEtterem(this);
        }


        for (int i = 0; i < allSzakacsCount ;i++) {
            Szakács szakacs = new Szakács(i+1);
            szakacs.setEtterem(this);
            szakacsok.add(szakacs);
        }

    }

    public synchronized void megrendel(Vendég rendeloVendeg){
        int hanyDbKaja = rendeloVendeg.getRendelesListaja().size(); // ennyi db rendelese van (pizza,hb, leves) = 3

        hatralevoKajakByVendeg.put(rendeloVendeg.getVendegId(), hanyDbKaja);


        for (Rendelés etel:rendeloVendeg.getRendelesListaja()){
            rendelesSor.add(etel);

            totalOrders++;
        }
        submittedGuestCount++;
        notifyAll();

    }


    // Szakács meghívja: kér egy kaját a sorból
    public synchronized Rendelés keszit(Szakács szakacs) throws InterruptedException {
        while (rendelesSor.isEmpty() && !allVendegSubmitted()){ //ha nincs rendeles és nincs minden kész
            wait();
        }

        if (rendelesSor.isEmpty() && allVendegSubmitted()){  // ha ures a sor es minden kesz
            return null;
        }
                                                         //csinal egy ordert
        Rendelés actRendeles = rendelesSor.poll();

        return actRendeles;
    }



    // Szakács meghívja sleep után: egy kaja elkészült
    // elkeszult egy kaja
    public synchronized void elkeszult(Rendelés actRendeles){
        String actVendegId = actRendeles.vendegId;

        int hatralevo = hatralevoKajakByVendeg.get(actVendegId);
        hatralevo--;
        hatralevoKajakByVendeg.put(actVendegId, hatralevo);

        finishedOrders++;

        notifyAll();

    }

    public synchronized void megkap(String vendegId) throws InterruptedException {
        // minden kaja elkszult rendelesbol akkor jo vagyok
        while (hatralevoKajakByVendeg.get(vendegId)>0){
            wait();
        }

    }



    private boolean allVendegSubmitted() {
        if (submittedGuestCount == vendegek.size()){
            return true;
        }
        return false;
    }

    public void startEtterem(){
        if (started){
            return;
        }
        started = true;

        for (Vendég vendég:vendegek){
            Thread vendegThread = new Thread(vendég);
            vendegThreads.add(vendegThread);
        }
        for(Szakács szakács: szakacsok){
            Thread szakacsThread =new Thread(szakács);
            szakacsThreads.add(szakacsThread);
        }
        for (Thread t : vendegThreads){
            t.start();
        }
        for (Thread t : szakacsThreads){
            t.start();
        }

    }
    public void stopEtterem(){
        for (Thread t : vendegThreads){
            t.interrupt();
        }
        for(Thread t : szakacsThreads){
            t.interrupt();
        }
    }


    public synchronized String getState(){
        return "Várakozo rendelesek szama: " + rendelesSor.size() + "\n" +
                "Elkészült rendelések száma: " + finishedOrders+ " / " + totalOrders + "\n" +
                "Leadott vendégek: " + submittedGuestCount + " / " + vendegek.size()+ "\n" +
                " Szakacsok szama: " + szakacsok.size() + "\n"+
                "Hátralévő étel vendégenként: " + hatralevoKajakByVendeg;

    }








}
