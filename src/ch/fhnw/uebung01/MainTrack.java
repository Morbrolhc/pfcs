package ch.fhnw.uebung01;

import ch.fhnw.glbase.GLBase1;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import java.awt.*;

public class MainTrack extends GLBase1 {

    //  ---------  globale Daten  ---------------------------

    float left = -2.5f, right = 2.5f;             // ViewingVolume
    float bottom, top;
    float near = -10, far = 1000;

    float CART_BODY_LENGTH = 0.25f;
    float CART_LENGTH = 0.33f;

    boolean crashed = false;

    Thread timer;
    // -------- Simulation Data ----------------
    public double k = 1;

    Cart a = new Cart(-1.0, 0, 1, 0.75f, 0f, 0.75f);
    Cart b = new Cart(1.0, 0, 1, 0f, 1f, 0f);

    public MainTrack() {
        super();
        f.setSize(900, 280);
        f.add(new Menu(this), BorderLayout.WEST);
        f.revalidate();
        f.repaint();
    }

    //  ---------  Methoden  ----------------------------------

    public void drawRectangle(GL3 gl, float x1, float y1, float x2, float y2,
                              float r, float g, float b) {
        rewindBuffer(gl);
        setColor(r, g, b, 1);
        putVertex(x1, y1, 0);
        putVertex(x2, y1, 0);
        putVertex(x2, y2, 0);
        putVertex(x1, y2, 0);
        int nVertices = 4;
        copyBuffer(gl, nVertices);
        gl.glDrawArrays(GL3.GL_TRIANGLE_FAN, 0, nVertices);
    }

    public void drawStrip(GL3 gl, float[] vertices, float r, float g, float b) {
        rewindBuffer(gl);
        setColor(r, g, b, 1);
        for (int i = 0; i < vertices.length; i += 2) {
            putVertex(vertices[i], vertices[i + 1], 0);
        }
        int nVertices = vertices.length / 2;
        copyBuffer(gl, nVertices);
        gl.glDrawArrays(GL3.GL_LINE_STRIP, 0, nVertices);
    }

    public void drawSetup(GL3 gl) {
        drawRectangle(gl, -2.5f, -0.3f, -2f, 0.3f, 0f, 0f, 0f);
        drawRectangle(gl, 2.5f, -0.3f, 2f, 0.3f, 0f, 0f, 0f);
        drawStrip(gl, new float[]{-2.5f, 0f, 2.5f, 0f}, 0f, 0f, 0f);
        drawStrip(gl, new float[]{-2.5f, -0.25f, 2.5f, -0.25f}, 0f, 0f, 0f);

    }

    public void drawCart(GL3 gl, float x, float r, float g, float b) {
        drawRectangle(gl, x - CART_BODY_LENGTH, -0.125f, x + CART_BODY_LENGTH, 0.125f, r, g, b);
        drawStrip(gl, new float[]{x - CART_BODY_LENGTH, 0.09f, x - CART_LENGTH, 0.09f}, r, g, b);
        drawStrip(gl, new float[]{x + CART_BODY_LENGTH, 0.09f, x + CART_LENGTH, 0.09f}, r, g, b);
        drawStrip(gl, new float[]{x - CART_LENGTH, 0.065f, x - CART_LENGTH, 0.115f}, r, g, b);
        drawStrip(gl, new float[]{x + CART_LENGTH, 0.065f, x + CART_LENGTH, 0.115f}, r, g, b);
    }

    public void checkWallCollision(Cart a) {
        if (a.x - CART_LENGTH >= -2.0 ^ a.x + CART_LENGTH <= 2.0) a.v *= (-1);
    }

    public void checkCartCollision(Cart a, Cart b) {
        if (crashed) {
            crashed = false;
            return;
        }
        if ((a.x + CART_LENGTH) >= (b.x - CART_LENGTH)) {
            double va = (a.m * a.v + b.m * b.v - (a.v - b.v) * b.m * k) / (a.m + b.m);
            double vb = (b.m * b.v + a.m * a.v - (b.v - a.v) * a.m * k) / (a.m + b.m);
            a.v = va;
            b.v = vb;
            a.randomColor();
            b.randomColor();
            crashed = true;
        }
    }

    public void update(double dTime) {
        checkWallCollision(a);
        checkWallCollision(b);
        checkCartCollision(a, b);
        b.x += b.v * dTime;
        a.x += a.v * dTime;
    }

    //  ----------  OpenGL-Events   ---------------------------

    @Override
    public void init(GLAutoDrawable drawable) {
        super.init(drawable);
        GL3 gl = drawable.getGL().getGL3();
        gl.glClearColor(1f, 1f, 1f, 0f);                         // Hintergrundfarbe (RGBA)
        gl.glDisable(GL3.GL_DEPTH_TEST);                  // ohne Sichtbarkeitstest
        FPSAnimator anim = new FPSAnimator(canvas, 60, true);
        timer = new Thread(new Timer(this));
        timer.start();
        anim.start();
    }


    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
        setColor(1, 1, 1, 1);
        drawSetup(gl);
        drawCart(gl, (float) a.x, a.r, a.g, a.b);
        drawCart(gl, (float) b.x, b.r, b.g, b.b);
    }


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y,
                        int width, int height) {
        GL3 gl = drawable.getGL().getGL3();
        // Set the viewport to be the entire window
        gl.glViewport(0, 0, width, height);

        // -----  Projektionsmatrix festlegen  -----
        bottom = left * height / width;
        top = right * height / width;
        setOrthogonalProjection(gl, left, right, bottom, top, near, far);
    }


    //  -----------  main-Methode  ---------------------------

    public static void main(String[] args) {
        new MainTrack();
    }
}