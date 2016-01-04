package ch.fhnw.uebung05;

import ch.fhnw.glbase.MyRenderer1;
import ch.fhnw.util.properties.IAnimatable;

import javax.media.opengl.GL3;
import java.awt.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by joel on 17.12.15.
 */
public class FlowLine implements IAnimatable {

    MyRenderer1 renderer;
    ConcurrentLinkedDeque<double[]> list = new ConcurrentLinkedDeque<>();
    boolean circulation = false, ideal = true, random = false;
    IdealFlow iFLow;
    CirculationFlow cFlow;

    public FlowLine(MyRenderer1 _renderer, double _r) {
        iFLow = new IdealFlow(_r);
        cFlow = new CirculationFlow(0.1);
        renderer = _renderer;
    }

    private void addPoints() {
        if(!random) {
            for(float f = -0.45f; f < 0.48; f += 0.1f) {
                list.add(new double[]{-1, f});
            }
        } else {
            for (int i = 0; i < 6; i++) {
                list.add(new double[]{-1, Math.random() - 0.5});
            }
        }
    }

    @Override
    public void update(double dTime) {
        if(ideal) {
            addPoints();
        }
        for (double[] p: list) {
            if(ideal) {
                double[] r = iFLow.runge(p, dTime);
                p[0] = r[0]; p[1] = r[1];
            }
            if(circulation) {
                double[] q = cFlow.runge(p, dTime);
                p[0] = q[0]; p[1] = q[1];
            }
        }
    }

    @Override
    public void draw(GL3 gl) {
        list.removeIf( p -> (p[0] > 1));
        renderer.rewindBuffer(gl);
        gl.glPointSize(2f);
        for (double[] p : list) {
            renderer.putVertex((float)p[0], (float)p[1], 0);
        }
        renderer.copyBuffer(gl, list.size());
        gl.glDrawArrays(GL3.GL_POINTS, 0, list.size());
    }

    public void setCirculation(boolean circulation) {
        this.circulation = circulation;
    }

    public void setIdeal(boolean ideal) {
        this.ideal = ideal;
    }

    public void setRandom() { random = !random;}
}
