package ch.fhnw.uebung05;

import ch.fhnw.util.math.Dynamic;

/**
 * Created by joel on 17.12.15.
 */
public class CirculationFlow extends Dynamic {

    double w;

    public CirculationFlow(double _w) {
        w = _w;
    }
    @Override
    public double[] f(double[] x) {
        double[] ret = new double[2];
        double q = x[0]*x[0]+x[1]*x[1];
        ret[0] = -x[1]/q;
        ret[1] = x[0]/q;
        return ret;
    }
}
