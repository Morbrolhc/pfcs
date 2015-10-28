package ch.fhnw.uebung01;

/**
 * Created by joel on 21.09.15.
 */
public class Timer implements Runnable{

    MainTrack inst;

    double oldTime;

    public Timer(MainTrack inst) {
        this.inst = inst;
        oldTime = System.nanoTime();
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            double currentTime = (double)System.nanoTime();
            inst.update((currentTime - oldTime)/1_000_000_000);
            oldTime = currentTime;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
