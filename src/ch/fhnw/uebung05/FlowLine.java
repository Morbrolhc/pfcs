package ch.fhnw.uebung05;

import ch.fhnw.glbase.MyRenderer1;
import ch.fhnw.util.properties.IAnimatable;

import javax.media.opengl.GL3;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by joel on 17.12.15.
 */
public class FlowLine implements IAnimatable {

    MyRenderer1 renderer;
    ConcurrentLinkedDeque<double[]> list = new ConcurrentLinkedDeque<>();
    int cCounter = 0;
    double y;
    boolean color = false;
    boolean circulation = false, ideal = true;
    IdealFlow iFLow;
    CirculationFlow cFlow;

    public FlowLine(MyRenderer1 _renderer, double _y, double _r) {
        iFLow = new IdealFlow(_r);
        cFlow = new CirculationFlow(0.1);
        y = _y;
        renderer = _renderer;
    }

    @Override
    public void update(double dTime) {
        if(ideal) {
            list.add(new double[]{-1, y});
            if(++cCounter == 30) {
                color = !color;
                cCounter = 0;
            }
        }
        for (double[] p: list) {
            if(ideal) {
                double[] r = iFLow.runge(p, dTime*0.4);
                p[0] = r[0]; p[1] = r[1];
            }
            if(circulation) {
                double[] q = cFlow.runge(p, dTime*0.4);
                p[0] = q[0]; p[1] = p[1];
            }
        }
    }

    @Override
    public void draw(GL3 gl) {
        list.removeIf( p -> (p[1] > 1));
        renderer.rewindBuffer(gl);
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
}
