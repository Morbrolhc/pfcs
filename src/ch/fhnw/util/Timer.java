package ch.fhnw.util;

import ch.fhnw.uebung01.MainTrack;
import ch.fhnw.util.properties.IAnimatable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joel on 21.09.15.
 */
public class Timer implements Runnable{

    List<IAnimatable> objects;

    double oldTime;

    public Timer() {
        objects = new ArrayList<>();
        oldTime = System.nanoTime();
    }

    public Timer(List<IAnimatable> objects) {
        this.objects = objects;
        oldTime = System.nanoTime();
    }

    public void addObject(IAnimatable obj) {
        objects.add(obj);
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            double currentTime = (double)System.nanoTime();
            for(IAnimatable ani : objects)
                ani.update((currentTime - oldTime)/1_000_000_000);
            oldTime = currentTime;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
