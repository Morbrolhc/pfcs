package ch.fhnw.uebung02;//  -------------   JOGL 2D-Programm  -------------------

import ch.fhnw.glbase.GLBase1;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;

public class Application extends GLBase1
{

    //  ---------  globale Daten  ---------------------------

    float left=-4, right=4;             // ViewingVolume
    float bottom, top;
    float near=-10, far=1000;
    double R = 2, phi = 0;
    FPSAnimator anim;

   //  ---------  Methoden  ----------------------------------

    public void drawCar (GL3 gl)
    {}

    //  ----------  OpenGL-Events   ---------------------------

    @Override
    public void init(GLAutoDrawable drawable)
    {  super.init(drawable);
       GL3 gl = drawable.getGL().getGL3();
       gl.glClearColor(0, 0, 1, 1);                         // Hintergrundfarbe (RGBA)
       gl.glDisable(GL3.GL_DEPTH_TEST);                  // ohne Sichtbarkeitstest
       anim = new FPSAnimator(canvas, 60, true);
       anim.start();
    }


    @Override
    public void display(GLAutoDrawable drawable)
    { GL3 gl = drawable.getGL().getGL3();
      gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
      setColor(1, 1, 1);
      drawAxis(gl, 8, 8, 8);             //  Koordinatenachsen
      setColor(1, 0, 0);
      phi +=0.04;
//      drawCircle(gl, 0, (float) (R * Math.sin(phi)), 0.5f, 50);
//      drawCircle(gl, (float) (R * Math.cos(phi)), 0, 0.5f, 50);
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
       setOrthogonalProjection(gl,left,right,bottom,top,near,far);
    }


    //  -----------  main-Methode  ---------------------------

    public static void main(String[] args)
    { new Application();
    }

}