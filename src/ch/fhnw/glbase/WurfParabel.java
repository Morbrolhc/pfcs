package ch.fhnw.glbase;//  -------------   JOGL 2D-Programm  -------------------

import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import java.awt.event.KeyEvent;

public class WurfParabel extends GLBase1 {

    //  ---------  globale Daten  ---------------------------

    float left=-10, right=60;             // ViewingVolume
    float bottom, top;
    float near=-10, far=1000;
    double R = 2, phi = 0;

    final double g = 9.81;

    double x = 0,y = 10;
    double vx = 10,vy = 20;
    double ax=0, ay=-g;

    double dt = 0.017;

   //  ---------  Methoden  ----------------------------------

    public void drawCircle(GL3 gl,float xm, float ym,
                           float r, int nPoints) {
        rewindBuffer(gl);
        double phi = 2*Math.PI/nPoints;
        setColor(1f, 0.3f, 0f, 1);
        putVertex(xm, ym, 0);
        setColor(1f, 0f, 0f, 1);
        for (int i = 0; i <= nPoints; i++) {
            putVertex((float)(xm+r*Math.cos(i*phi)), (float)(ym+r*Math.sin(i*phi)), 0);
        }
        copyBuffer(gl, nPoints + 2);
        gl.glDrawArrays(GL3.GL_TRIANGLE_FAN, 0, nPoints + 2);
    }

    public void drawLine(GL3 gl, double x0, double y0,
                         double vx0, double vy0,
                         double dt, int nPunkte) {
        double x1,y1,t;
        for(int i=0; i<nPunkte;i++) {
            t = i*dt;
            x1 = vx0 * t + x0;
            y1 = -0.5 * g * t * t + vy0 * t + y0;
            putVertex((float)x1, (float)y1, 0);
        }
        int nVerteces = nPunkte;
        copyBuffer(gl, nVerteces);
        gl.glDrawArrays(GL3.GL_LINE_STRIP, 0, nVerteces);
    }

    public void drawSpeer(GL3 gl, float w, float h){
        w *= 0.5f;
        h *= 0.5f;
        float w2 = 0.8f*w;
        rewindBuffer(gl);
        putVertex(-w, 0, 0);
        putVertex(-w2, -h, 0);
        putVertex(w2, -h, 0);
        putVertex(w, 0, 0);
        putVertex(w2, h, 0);
        putVertex(-w2, h, 0);
        int nVerteces = 6;
        copyBuffer(gl, nVerteces);
        gl.glDrawArrays(GL3.GL_TRIANGLE_FAN, 0, nVerteces);
    }

    //  ----------  OpenGL-Events   ---------------------------

    @Override
    public void init(GLAutoDrawable drawable)
    {  super.init(drawable);
       f.addKeyListener(this);
       GL3 gl = drawable.getGL().getGL3();
       gl.glClearColor(1, 1, 1, 1);                         // Hintergrundfarbe (RGBA)
       gl.glDisable(GL3.GL_DEPTH_TEST);                  // ohne Sichtbarkeitstest
       FPSAnimator anim = new FPSAnimator(canvas, 60, true);
       anim.start();
    }


    @Override
    public void display(GLAutoDrawable drawable)
    { GL3 gl = drawable.getGL().getGL3();
      gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
      loadIdentity(gl);
      setColor(0, 0, 0, 1);
      drawLine(gl, 0, 10, 10, 20, 0.1, 100);
      setColor(1, 0, 0, 1);
      phi +=0.04;
      translate(gl, (float) x, (float) y, 0);
      double alpha = Math.toDegrees(Math.atan(vy/vx));
      rotate(gl, (float)alpha, 0, 0, 1);
      drawSpeer(gl, 10, 0.5f);
      x += vx*dt;
      y += vy*dt;
      vy += ay*dt;
      if(y < 0) reset();
    }


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y,
                        int width, int height)
    {  GL3 gl = drawable.getGL().getGL3();
       // Set the viewport to be the entire window
       gl.glViewport(0, 0, width, height);

       // -----  Projektionsmatrix festlegen  -----
       bottom = (float)(left * height/width);
       top = (float)(right * height/width);
       setOrthogonalProjection(gl, left, right, bottom, top, near, far);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        System.out.println();
        if(e.getKeyCode() == (KeyEvent.VK_R)) {
            reset();
        }
    }

    private void reset() {
        x = 0;
        y = 10;
        vy = 20;
    }

    //  -----------  main-Methode  ---------------------------

    public static void main(String[] args)
    { new WurfParabel();
    }

}