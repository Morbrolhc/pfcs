package ch.fhnw.uebung02;

/**
 * Created by joel on 05.10.15.
 */
public class Car {
    float h, w, alpha, delta = 0;
    float tyreH, tyreW;

    public Car(float h, float w, float alpha){
        this.h = h;
        this.w = w;
        this.alpha = alpha;
        tyreH = h/4;
        tyreW = w/4;
    }
}
