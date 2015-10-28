package ch.fhnw.util.bodys;

import ch.fhnw.glbase.MyRenderer1;
import ch.fhnw.util.properties.IAnimatable;

import javax.media.opengl.GL3;

/**
 * Created by joel on 28.10.15.
 */
public class FlyingCuboid extends Cuboid implements IAnimatable {

    float v;
    float px, py, pz = -20;
    float rx, ry, rz, rv;
    float phi;

    public FlyingCuboid(MyRenderer1 renderer, float a, float b, float c,
                        float v, float px, float py,
                        float rx, float ry, float rz, float rv) {
        super(renderer, a, b, c);
        this.v = v;
        this.px = px;
        this.py = py;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.rv = rv;
        phi = 0;
    }

    @Override
    public void draw(GL3 gl) {
        renderer.pushMatrix(gl);
        renderer.translate(gl, px, py, pz);
        renderer.rotate(gl, phi, rx, ry, rz);
        super.draw(gl);
        renderer.popMatrix(gl);
    }

    @Override
    public void update(double dTime) {
        phi+= dTime*rv*50;
        pz += dTime*v;
    }
}
