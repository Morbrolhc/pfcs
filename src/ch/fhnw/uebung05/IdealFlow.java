package ch.fhnw.uebung05;

import ch.fhnw.util.math.Dynamic;

/**
 * Created by joel on 17.12.15.
 */
public class IdealFlow extends Dynamic{

    double r;
    double r2;

    public IdealFlow(double _r) {
        r = _r;
        r2 = r*r;
    }

    @Override
    public double[] f(double[] x) {
        double[] ret = new double[2];
        double x2y2 = x[0]*x[0]+x[1]*x[1];
        ret[0] = 1 + (r2/x2y2) - (2*r2*x[0]*x[0]/(x2y2*x2y2));
        ret[1] = -(2*r2*x[0]*x[1]/(x2y2*x2y2));
        return ret;
    }
}
