package ch.fhnw.util;

import ch.fhnw.glbase.MyRenderer1;
import ch.fhnw.util.math.Vec3;
import ch.fhnw.util.properties.IAnimatable;

import javax.media.opengl.GL;
import javax.media.opengl.GL3;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by joel on 09.12.15.
 */
public class Trail {
    Deque<Vec3> deque = new LinkedList<>();
    MyRenderer1 renderer;

    public Trail(MyRenderer1 _renderer) {
        renderer = _renderer;
    }

    public void add(double x, double y, double z) {
        deque.add(new Vec3(x, y, z));
        if(deque.size() == 30) {
            deque.removeFirst();
        }
    }

    public void draw(GL3 gl) {
        renderer.rewindBuffer(gl);
        deque.forEach((vec) -> {
            renderer.putVertex(vec.x, vec.y, vec.z);
        });
        renderer.copyBuffer(gl, deque.size());
        gl.glDrawArrays(GL.GL_LINE_STRIP, 0, deque.size());
    }

}
