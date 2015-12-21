package ch.fhnw.util.bodys;

import ch.fhnw.glbase.MyRenderer1;
import ch.fhnw.util.math.Mat3;
import ch.fhnw.util.math.Mat4;
import ch.fhnw.util.properties.IAnimatable;

import javax.media.opengl.GL3;

/**
 * Created by joel on 28.10.15.
 */
public class FlyingCuboid implements IAnimatable {

    private static final float START = -20;

    public static float SPEED = 1;

    float x, y, z;
    float vx, vy, vz;
    float i1, i2, i3;
    float phi;
    Mat4 R = new Mat4(new float[]{1, 0, 0, 0,
                                  0, 1, 0, 0,
                                  0, 0, 1, 0,
                                  0, 0, 0, 1});
    float w1, w2, w3;
    Cuboid cub;
    MyRenderer1 renderer;

    public FlyingCuboid(Cuboid cub, float v) {
        float x = 0; y = 0; z = START;
        vx = 0; vy = 0; vz = v;
        w1 = 20; w2 = 0; w3 = 0;
//        i1 =
        this.cub = cub;
        renderer = cub.renderer;


    }

    @Override
    public void draw(GL3 gl) {
        renderer.pushMatrix(gl);
        renderer.translate(gl, x, y, z);
        renderer.rotate(gl, phi, w1, w2, w3);
        Mat4 M = renderer.getModelViewMatrix(gl);
        M = M.postMultiply(R);
        renderer.setModelViewMatrix(gl, M);
        cub.draw(gl);
        renderer.popMatrix(gl);

    }

    @Override
    public void update(double dTime) {
        z += vz * dTime * SPEED;
        if(z > 0.2) {
            z = START;
        }
        // calculate wx

        float ww = (float)Math.sqrt(w1*w1 + w2*w2 + w3*w3);
        phi = ww*(float)dTime;
        R = R.postMultiply(Mat4.rotate(phi, w1, w2, w3));
    }
}
