package ch.fhnw.util.bodys;

import ch.fhnw.glbase.MyRenderer1;
import ch.fhnw.util.math.Vec3;

import javax.media.opengl.GL3;

/**
 * Created by joel on 14.10.15.
 */
public class Cuboid {

    private float a;
    private float b;
    private float c;
    private float cr, cg, cb;

    MyRenderer1 renderer;

    public Cuboid(MyRenderer1 renderer, float a, float b, float c, float cr, float cg, float cb){
        this.renderer = renderer;
        this.a = a;
        this.b = b;
        this.c = c;
        this.cr = cr;
        this.cg = cg;
        this.cb = cb;
    }

    public void draw(GL3 gl){
        renderer.setColor(cr, cg, cb, 1);
        float a2 = a*0.5f, b2 = b*0.5f, c2 = c*0.5f;
        Vec3 A = new Vec3(a2, -b2, c2);
        Vec3 B = new Vec3(a2, -b2, -c2);
        Vec3 C = new Vec3(-a2, -b2, -c2);
        Vec3 D = new Vec3(-a2, -b2, c2);
        Vec3 E = new Vec3(a2, b2, c2);
        Vec3 F = new Vec3(a2, b2, -c2);
        Vec3 G = new Vec3(-a2, b2, -c2);
        Vec3 H = new Vec3(-a2, b2, c2);
        renderer.setNormal(0 , -1, 0);
        putRectangle(D, C, B, A);
        renderer.setNormal(0, 1, 0);
        putRectangle(E, F, G, H);
        renderer.setNormal(1, 0, 0);
        putRectangle(A, B, F, E);
        renderer.setNormal(0, 0, 1);
        putRectangle(D, A, E, H);
        renderer.setNormal(-1, 0, 0);
        putRectangle(D, H, G, C);
        renderer.setNormal(0, 0, -1);
        putRectangle(B, C, G, F);
        renderer.copyBuffer(gl, 36);
        gl.glDrawArrays(GL3.GL_TRIANGLES, 0, 36);
    }

    private void putRectangle(Vec3 A, Vec3 B, Vec3 C, Vec3 D){
        renderer.putVertex(A.x, A.y, A.z);
        renderer.putVertex(B.x, B.y, B.z);
        renderer.putVertex(C.x, C.y, C.z);
        renderer.putVertex(A.x, A.y, A.z);
        renderer.putVertex(C.x, C.y, C.z);
        renderer.putVertex(D.x, D.y, D.z);
    }



//------ Getter & Setter ----------

    public void setA(float a) {
        this.a = a;
    }

    public void setB(float b) {
        this.b = b;
    }

    public void setC(float c) {
        this.c = c;
    }

    public float getA() {
        return a;
    }

    public float getB() {
        return b;
    }

    public float getC() {
        return c;
    }

    public void setColor(float cr, float cg, float cb) {
        this.cr = cr;
        this.cg = cg;
        this.cb = cb;
    }
}
