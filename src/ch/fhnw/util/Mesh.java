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

    private Mesh(MyRenderer1 _renderer, ArrayList<Float> _vertexList, ArrayList<Float> _normalList) {
        renderer = _renderer;
        vertexList = _vertexList;
        normalList = _normalList;
    }

    public static Mesh factory(String _fileName, MyRenderer1 _renderer) {
        ArrayList<Float> vlist = new ArrayList<>(20000);
        ArrayList<Float> nlist = new ArrayList<>(20000);
        OBJReader obj= new OBJReader();
        obj.readMesh(_fileName, vlist, nlist);
        return new Mesh(_renderer, vlist, nlist);
    }

    public static Mesh factory(MyRenderer1 _renderer, ArrayList<Float> _vList, ArrayList<Float> _nList) {
        return new Mesh(_renderer, _vList, _nList);
    }

    public void draw(GL3 gl) {
        if(renderer == null) {
            return;
        }
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
    public MyRenderer1 getRenderer() {
        return renderer;
    }
}
