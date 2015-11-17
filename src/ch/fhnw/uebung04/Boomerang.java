package ch.fhnw.uebung04;

import ch.fhnw.glbase.MyRenderer1;
import ch.fhnw.util.Mesh;
import ch.fhnw.util.properties.IAnimatable;

import javax.media.opengl.GL3;

/**
 * Created by joel on 11.11.15.
 */
public class Boomerang implements IAnimatable {

    private float alpha;
    private float beta = (float)Math.random()*360;
    private float incl;
    private float r;


    private Mesh mesh;

    public Boomerang(MyRenderer1 _renderer, String model, float _incl, float _r) {
        r = _r;
        incl = _incl;
        mesh = Mesh.factory(model, _renderer);
    }

    @Override
    public void update(double dTime) {
        alpha += dTime * 1500;
        beta += dTime *30;

        if(alpha >= 360) alpha = 0;
        if(beta >= 360) beta = 0;
    }

    @Override
    public void draw(GL3 gl) {
        MyRenderer1 renderer = mesh.getRenderer();
        renderer.pushMatrix(gl);
        renderer.rotate(gl, incl, 0, 0, 1);
        renderer.rotate(gl, beta, 0, 1, 0);
        renderer.translate(gl,r, 0, 0);
        renderer.rotate(gl, 55, 0, 0, 1);
        renderer.rotate(gl, alpha, 0, 1, 0);
        mesh.draw(gl);
        renderer.popMatrix(gl);
    }
}
