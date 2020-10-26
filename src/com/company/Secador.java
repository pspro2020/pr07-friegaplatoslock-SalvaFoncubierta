package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Secador implements Runnable{

    private Bandeja bandejaLavados = new Bandeja();
    private Bandeja bandejaSecados = new Bandeja();
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private Date date;

    public Secador(Bandeja bandejaLavados, Bandeja bandejaSecados) {
        this.bandejaLavados = bandejaLavados;
        this.bandejaSecados = bandejaSecados;
    }

    @Override
    public void run() {
        Plate plate;
        while (!Thread.currentThread().isInterrupted()){
            try {
                plate = bandejaLavados.cogerPlate();
            } catch (InterruptedException e) {
                System.out.println("El secador se ha interrumpido mientras cogía el plato de la bandeja.");
                return;
            }
            try {
                secarPlate(plate);
            } catch (InterruptedException e) {
                System.out.println("El secador se ha interrumpido mientras secaba el plato.");
                return;
            }
            try {
                bandejaSecados.ponerPlate(plate);
            } catch (InterruptedException e) {
                System.out.println("El secador se ha interrumpido mientras añadía el plato a la bandeja.");
                return;
            }
        }
        System.out.println("El secador se ha interrumpido.");
    }

    private void secarPlate(Plate plate) throws InterruptedException {
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(3)+1);
        date = new Date();
        System.out.printf("El secador ha secado el plato %d %s\n", plate.getNumber(), dateFormat.format(date));
    }

    private void cogerPlate() {
    }
}
