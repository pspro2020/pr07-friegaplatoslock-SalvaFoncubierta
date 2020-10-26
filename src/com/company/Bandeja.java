package com.company;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bandeja {
    private ArrayList<Plate> lista = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock(true);
    private Condition notEmpty = lock.newCondition();

    public void ponerPlate(Plate plate) throws InterruptedException{
        lock.lock();
        try {
            lista.add(plate);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Plate cogerPlate() throws InterruptedException {
        Plate plate;
        lock.lock();
        try {
            while (lista.isEmpty()) {
                notEmpty.await();
            }
            plate = lista.remove(0);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
        return plate;
    }

}
