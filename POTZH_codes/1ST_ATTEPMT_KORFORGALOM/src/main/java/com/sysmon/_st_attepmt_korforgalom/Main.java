package com.sysmon._st_attepmt_korforgalom;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        try{
            City city = CityLoader.load("korforgalmak_1.txt");
            System.out.println(city.getInfoText());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
