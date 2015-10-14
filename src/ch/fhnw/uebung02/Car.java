package ch.fhnw.uebung02;

/**
 * Created by joel on 05.10.15.
 */
public class Car {
    float h, w, alpha, beta;
    float tyreH, tyreW;
    float ym, v = 0;

    public Car(float h, float w, float alpha){
        this.h = h;
        this.w = w;
        setAlpha(alpha);
        tyreH = h/4;
        tyreW = w/4;
    }

    public void setAlpha(float alpha) {

        this.alpha = alpha;
        ym = h / 2 + (float) (w / Math.tan(alpha));
        beta = (float) Math.atan(w / (ym + h / 2));
    }
}
