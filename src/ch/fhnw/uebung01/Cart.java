package ch.fhnw.uebung01;

/**
 * Created by joel on 21.09.15.
 */
public class Cart {
    double x;
    double v;
    double m;
    float r, g, b;

    public Cart(double x, double v, double m, float r, float g, float b){
        this.x = x;
        this.v = v;
        this.m = m;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void randomColor() {
        r = (float)Math.random();
        g = (float)Math.random();
        b = (float)Math.random();
    }

}
