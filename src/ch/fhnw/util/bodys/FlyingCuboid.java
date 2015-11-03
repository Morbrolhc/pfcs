package ch.fhnw.util.bodys;

import ch.fhnw.glbase.MyRenderer1;
import ch.fhnw.util.properties.IAnimatable;

import javax.media.opengl.GL3;

/**
 * Created by joel on 28.10.15.
 */
public class FlyingCuboid implements IAnimatable {

    float v;
    float px, py, pz;
    float rx, ry, rz, rv;
    float phi;
    public static float SPEED = 1;
    Cuboid cub;
    MyRenderer1 renderer;

    public FlyingCuboid(Cuboid cub,
                        float v, float px, float py, float pz,
                        float rx, float ry, float rz, float rv) {
        this.v = v;
        this.px = px;
        this.py = py;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.rv = rv;
        this.pz = pz;
        this.cub = cub;
        renderer = cub.renderer;

        phi = 0;
    }

    @Override
    public void draw(GL3 gl) {
        renderer.pushMatrix(gl);
        renderer.translate(gl, px, py, pz);
        renderer.rotate(gl, phi, rx, ry, rz);
        cub.draw(gl);
        renderer.popMatrix(gl);
    }

    @Override
    public void update(double dTime) {
        phi+= dTime*rv*50;
        if(phi > 360) phi = 0;
        pz += dTime*v*SPEED;
        if(pz > 1.0) pz = -100;
    }
}
