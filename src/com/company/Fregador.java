package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Fregador implements Runnable{

    private Bandeja bandejaLavados = new Bandeja();
    private int plateNumber = 1;
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private Date date;

    public Fregador(Bandeja bandejaLavados) {
        this.bandejaLavados = bandejaLavados;
    }

    @Override
    public void run() {
        Plate plate;
        while (!Thread.currentThread().isInterrupted()){
            try {
                plate = cogerPlate();
            } catch (InterruptedException e) {
                System.out.println("El fregador se ha interrumpido mientras fregaba el plato.");
                return;
            }
            try {
                bandejaLavados.ponerPlate(plate);
            } catch (InterruptedException e) {
                System.out.println("El fregador se ha interrumpido mientras añadía el plato a la bandeja.");
                return;
            }
        }
        System.out.println("El fregador se ha interrumpido.");
    }

    private Plate cogerPlate() throws InterruptedException{
        Plate plate = new Plate(plateNumber++) ;
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5)+4);
        date = new Date();
        System.out.printf("El fregador ha fregado el plato %d %s\n", plate.getNumber(), dateFormat.format(date));
        return plate;

    }
}
