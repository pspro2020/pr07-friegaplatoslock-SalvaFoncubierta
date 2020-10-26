package com.company;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        Bandeja bandejaLavados = new Bandeja();
        Bandeja bandejaSecados = new Bandeja();
        Thread fregador = new Thread(new Fregador(bandejaLavados));
        Thread secador = new Thread(new Secador(bandejaLavados, bandejaSecados));
        Thread organizador = new Thread(new Organizador(bandejaSecados));

        System.out.println("A fregar chavales.");

        fregador.start();
        secador.start();
        organizador.start();
        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fregador.interrupt();
        secador.interrupt();
        organizador.interrupt();

        try {
            fregador.join();
            secador.join();
            organizador.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("Feliz cumpleaños");
    }
}
