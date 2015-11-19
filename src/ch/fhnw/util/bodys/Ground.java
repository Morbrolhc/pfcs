package ch.fhnw.util.bodys;

import ch.fhnw.glbase.MyRenderer1;
import ch.fhnw.util.Mesh;

import javax.media.opengl.GL3;
import java.util.ArrayList;

/**
 * Created by joel on 13/11/x15.
 */
public class Ground {

    private static final float[] GROUNDNORMAL = new float[]{0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0};

    private float[]GROUNDVERTEX;
    private Mesh mesh;
    private float x, y;

    public Ground(MyRenderer1 _renderer, float _x, float _y) {
        x = _x;
        y = _y;
        GROUNDVERTEX = new float[]{-x, 0, x, x, 0, -x, -x, 0, -x, -x, 0, x, x, 0, -x, x, 0, x};
        ArrayList<Float> vList = new ArrayList<>();
        ArrayList<Float> nList = new ArrayList<>();
        for(float f : GROUNDVERTEX) {
            vList.add(f);
        }
        for(float f : GROUNDNORMAL) {
            nList.add(f);
        }
        mesh = Mesh.factory(_renderer, vList, nList);
    }

    public void draw (GL3 gl) {
        MyRenderer1 renderer = mesh.getRenderer();
        renderer.pushMatrix(gl);
        renderer.translate(gl, 0, y, 0);
        mesh.draw(gl);
        renderer.popMatrix(gl);
    }

}
