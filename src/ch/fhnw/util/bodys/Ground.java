package ch.fhnw.util.bodys;

import ch.fhnw.glbase.MyRenderer1;
import ch.fhnw.util.Mesh;

import javax.media.opengl.GL3;
import java.util.ArrayList;

/**
 * Created by joel on 13/11/2015.
 */
public class Ground {

    public static final float[] GROUNDVERTEX = new float[]{-100, 0, 100, 100, 0, -100, -100, 0, -100, -100, 0, 100, 100, 0, -100, 100, 0, 100};
    public static final float[] GROUNDNORMAL = new float[]{0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0};


    private Mesh mesh;
    private float y;

    public Ground(MyRenderer1 _renderer, float _y) {
        y = _y;
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
        renderer.translate(gl, 0, 0, y);
        mesh.draw(gl);
        renderer.popMatrix(gl);
    }

}
