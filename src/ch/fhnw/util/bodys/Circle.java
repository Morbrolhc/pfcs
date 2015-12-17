package ch.fhnw.util.bodys;

import ch.fhnw.glbase.MyRenderer1;
import ch.fhnw.util.properties.IDrawable;

import javax.media.opengl.GL3;

/**
 * Created by joel on 17.12.15.
 */
public class Circle implements IDrawable {

    double r;
    int resolution;
    double x, y;
    MyRenderer1 renderer;

    public Circle(MyRenderer1 _renderer, double _r, int _resolution, double _x, double _y) {
        r = _r;
        renderer = _renderer;
        resolution = _resolution;
        x = _x; y = _y;
    }

    @Override
    public void draw(GL3 gl) {
        renderer.rewindBuffer(gl);
        double phi = (2*Math.PI)/resolution;
        renderer.putVertex((float)x, (float)y, 0);
        for (int i = 0; i <= resolution; i++) {
            renderer.putVertex((float)(x+r*Math.cos(i*phi)), (float)(y+r*Math.sin(i*phi)), 0);
        }
        renderer.copyBuffer(gl, resolution + 2);
        gl.glDrawArrays(GL3.GL_TRIANGLE_FAN, 0, resolution + 2);
    }
}
