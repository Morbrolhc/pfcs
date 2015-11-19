package ch.fhnw.glbase;//  -------------   JOGL 2D-Programm  -------------------

import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import java.awt.event.KeyEvent;

public class Brett extends GLBase1 {

    //  ---------  globale Daten  ---------------------------

    float left = -1, right = 2;             // ViewingVolume
    float bottom, top;
    float near = -10, far = 1000;

    final double g = 9.81;




    double a = 1.1, b = 0.01, m = 1;
    double phi0 = Math.toRadians(36), omega0 = 0;
    double phi=phi0, omega=omega0;
    double I = m*(a*a+b*b)/12+m*a*a/4;

    double x = 1.5, y0 = a*Math.sin(phi0), y = y0;
    double vx = 0, vy0 = 0, vy = vy0;
    double ax = 0, ay = -g;

    double dt0 = 0.0005, dt=dt0;

    //  ---------  Methoden  ----------------------------------

    public void drawCircle(GL3 gl, float xm, float ym,
                           float r, int nPoints) {
        rewindBuffer(gl);
        double phi = 2 * Math.PI / nPoints;
        setColor(1f, 0.3f, 0f, 1);
        putVertex(xm, ym, 0);
        setColor(1f, 0f, 0f, 1);
        for (int i = 0; i <= nPoints; i++) {
            putVertex((float) (xm + r * Math.cos(i * phi)), (float) (ym + r * Math.sin(i * phi)), 0);
        }
        copyBuffer(gl, nPoints + 2);
        gl.glDrawArrays(GL3.GL_TRIANGLE_FAN, 0, nPoints + 2);
    }


    public void drawRectangle(GL3 gl, float w, float h) {
        rewindBuffer(gl);
        putVertex(0, -h/2, 0);
        putVertex(w, -h/2, 0);
        putVertex(w, h/2, 0);
        putVertex(0, h/2, 0);
        int nVerteces = 4;
        copyBuffer(gl, nVerteces);
        gl.glDrawArrays(GL3.GL_TRIANGLE_FAN, 0, nVerteces);
    }

    //  ----------  OpenGL-Events   ---------------------------

    @Override
    public void init(GLAutoDrawable drawable) {
        super.init(drawable);
        f.addKeyListener(this);
        GL3 gl = drawable.getGL().getGL3();
        gl.glClearColor(1, 1, 1, 1);                         // Hintergrundfarbe (RGBA)
        gl.glDisable(GL3.GL_DEPTH_TEST);                  // ohne Sichtbarkeitstest
        FPSAnimator anim = new FPSAnimator(canvas, 60, true);
        anim.start();
    }


    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
        loadIdentity(gl);
        setColor(1, 0, 0, 1);
        drawCircle(gl, (float) x, (float) y, 0.01f, 20);
        rotate(gl, (float)Math.toDegrees(phi), 0, 0, 1);
        drawRectangle(gl, (float)a, (float)b);
        x += vx * dt;
        y += vy * dt;
        vx += ax * dt;
        vy += ay * dt;

        phi += omega * dt;
        double r = a/2;
        double alpha = - r * m * g * Math.cos(phi) /I;
        omega += alpha *dt;
        if(phi < 0) dt = 0;


    }


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y,
                        int width, int height) {
        GL3 gl = drawable.getGL().getGL3();
        // Set the viewport to be the entire window
        gl.glViewport(0, 0, width, height);

        // -----  Projektionsmatrix festlegen  -----
        bottom = (float) (left * height / width);
        top = (float) (right * height / width);
        setOrthogonalProjection(gl, left, right, bottom, top, near, far);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        phi = phi0;
        vy = vy0;
        y = y0;
        omega = omega0;
        dt = dt0;
        System.out.println();
    }


    //  -----------  main-Methode  ---------------------------

    public static void main(String[] args) {
        new Brett();
    }

}