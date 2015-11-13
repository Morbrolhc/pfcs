package ch.fhnw.uebung04;

import ch.fhnw.glbase.MyRenderer1;
import ch.fhnw.util.Mesh;
import ch.fhnw.util.properties.IAnimatable;

import javax.media.opengl.GL3;

/**
 * Created by joel on 11.11.15.
 */
public class Boomerang implements IAnimatable {

    private float phi;

    private Mesh mesh;

    public Boomerang(MyRenderer1 _renderer) {
        mesh = Mesh.factory("daywalker.obj", _renderer);
    }

    @Override
    public void update(double dTime) {

    }

    @Override
    public void draw(GL3 gl) {
        MyRenderer1 renderer = mesh.getRenderer();
        renderer.rotate(gl, 15, 1, 0, 0);
        renderer.rotate(gl, phi+=5, 0, 1, 0);
        renderer.scale(gl, 2f);
        mesh.draw(gl);
    }
}
