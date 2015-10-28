package ch.fhnw.uebung02;

/**
 * Created by joel on 05.10.15.
 */
public class Car {
    float h, w, alpha, beta;
    float tyreH, tyreW;
    float ym, v = 0;
    float maxA, currA = 0;

    public Car(float h, float w, float alpha){
        this.h = h;
        this.w = w;
        setAlpha(alpha);
        tyreH = h/4;
        tyreW = w/4;
        maxA = 1.2f*9.81f;
    }

    public void setAlpha(float alpha) {
        float tmpYm = h / 2 + (float) (w / Math.tan(alpha));

        float tmpA =(float) Math.pow(v, 2) / tmpYm;
        float tmpBeta = (float) Math.atan(w / (tmpYm + h / 2));
        if (Math.abs(tmpA) < maxA && Math.max(Math.abs(alpha), Math.abs(tmpBeta)) < 1) {
            this.alpha = alpha;
            ym = tmpYm;
            beta = tmpBeta;
            currA = tmpA;
        }
    }

    public void setV(float v) {
        float tmpA =(float) Math.pow(v, 2) / ym;
        if(Math.abs(tmpA) < maxA) {
            this.v = v;
            currA = tmpA;
        }
    }
}
