package ch.fhnw.util.bodys;

import ch.fhnw.glbase.MyRenderer1;
import ch.fhnw.util.math.Dynamic;
import ch.fhnw.util.math.Mat4;
import ch.fhnw.util.properties.IAnimatable;

import javax.media.opengl.GL3;
import java.util.Random;

/**
 * Created by joel on 28.10.15.
 */
public class FlyingCuboid extends Dynamic implements IAnimatable {

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
    double[] w = new double[3];
    Cuboid cub;
    MyRenderer1 renderer;
    Random rand = new Random();

    public FlyingCuboid(MyRenderer1 renderer) {
        this.renderer = renderer;
        x = rand.nextFloat()-0.5f; y = rand.nextFloat()-0.5f; z = START;
        vx = 0; vy = 0; vz = rand.nextFloat()+0.2f;
        w[0] = rand.nextInt(100); w[1] = rand.nextInt(100); w[2] = rand.nextInt(100);
        cub = new Cuboid(renderer, rand.nextFloat()*0.03f+0.005f, rand.nextFloat()*0.03f+0.005f,
                rand.nextFloat()*0.03f+0.005f, 1, 1, 0);
        float a = cub.getA()*cub.getA(), b = cub.getB()*cub.getB(), c = cub.getC()*cub.getC();
        i1 = b+c; i2 = a+c; i3 = a+b;


    }

    @Override
    public void draw(GL3 gl) {
        renderer.pushMatrix(gl);
        renderer.translate(gl, x, y, z);
        renderer.rotate(gl, phi, (float)w[0], (float)w[1], (float)w[2]);
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

        w = runge(w, dTime);

        float ww = (float)Math.sqrt(w[0]*w[0] + w[1]*w[1] + w[2]*w[2]);
        phi = ww*(float)dTime;
        R = R.postMultiply(Mat4.rotate(phi, (float)w[0], (float)w[1], (float)w[2]));
    }

    @Override
    public double[] f(double[] x) {
        double[] res = new double[3];
        res[0] = (i2-i3)*x[1]*x[2];
        res[1] = (i3-i1)*x[2]*x[1];
        res[2] = (i1-i2)*x[0]*x[1];
        return res;
    }

    public void setColor(float r, float g, float b) {
        cub.setColor(r, g, b);
    }
}
