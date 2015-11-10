package ch.fhnw.util;

import ch.fhnw.glbase.MyRenderer1;

import javax.media.opengl.GL;
import javax.media.opengl.GL3;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joel on 05.11.15.
 */
public class Mesh {

    MyRenderer1 renderer = null;
    ArrayList<Float> vertexList, normalList;

    public Mesh(ArrayList<Float> _vertexList, ArrayList<Float> _normalList) {
        vertexList = _vertexList;
        normalList = _normalList;
    }

    public void draw(GL3 gl) {
        if(renderer == null) {
            return;
        }
        renderer.pushMatrix(gl);
        for(int i = 0; i < vertexList.size(); i+=3) {
            renderer.setNormal(normalList.get(i), normalList.get(i+1), normalList.get(i+2));
            renderer.putVertex(vertexList.get(i), vertexList.get(i+1), vertexList.get(i+2));
        }
        renderer.copyBuffer(gl, vertexList.size());
        gl.glDrawArrays(GL.GL_TRIANGLES, 0, vertexList.size());
    }

    public void setRenderer(MyRenderer1 _renderer) {
        renderer = _renderer;
    }
}
