package com.sysmon._ndattemptcity;

public class Roundabout {


    Roundabout(int roundaboutId){
        this.roundaboutId = roundaboutId;
        occupied = false;
    }


    public int getRoundaboutId() {
        return roundaboutId;
    }

    public final int roundaboutId;

    public boolean occupied = false;
    public final Object lock = new Object();

    @Override
    public String toString(){
        return "Roundabout ID: " + roundaboutId;
    }

    // commonroundabout



    public void enteringRoundabout(Car carId) throws InterruptedException {
        synchronized (lock){
            while (occupied){
                lock.wait();
            }
            occupied = true;
            // Thread.sleep(1000);  auto alszik

        }
    }
    public void leavingRoundabout(Car carId) {
        synchronized (lock) {
            // az occupied check is lockon belul van
            if (!occupied) {
                System.out.println("nincs kocsi a körforgalomban");
            }
            int currentCar = carId.getCarId();
            occupied = false;
            lock.notifyAll();
            System.out.println(carId + " has left the roundabout");
        }
    }


}
