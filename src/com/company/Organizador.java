package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Organizador implements Runnable{

    private Bandeja bandejaSecado = new Bandeja();
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private Date date;

    public Organizador(Bandeja bandejaSecado) {
        this.bandejaSecado = bandejaSecado;
    }

    @Override
    public void run() {
        Plate plate;
        while (!Thread.currentThread().isInterrupted()){
            try {
                plate = bandejaSecado.cogerPlate();
            } catch (InterruptedException e) {
                System.out.println("El organizador se ha interrumpido mientras cogía el plato de la bandeja.");
                return;
            }
            try {
                organizarPlate(plate);
            } catch (InterruptedException e) {
                System.out.println("El organizador se ha interrumpido mientras guardaba el plato.");
                return;
            }
        }
        System.out.println("El organizador se ha interrumpido.");
    }

    private void organizarPlate(Plate plate) throws InterruptedException {
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(2)+1);
        date = new Date();
        System.out.printf("El organizador ha organizado el plato %d %s\n", plate.getNumber(), dateFormat.format(date));
    }
}
