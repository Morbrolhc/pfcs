package ch.fhnw.glbase;//  -------------   JOGL 2D-Programm  -------------------
import javax.media.opengl.*;

public class MyFirst2D extends GLBase1
{

    //  ---------  globale Daten  ---------------------------

    float left=-1, right=1;             // ViewingVolume
    float bottom=-1, top=1;
    float near=-10, far=1000;

   //  ---------  Methoden  ----------------------------------

    public void zeichneDreieck(GL3 gl,
                        float x1, float y1, float z1,
                        float x2, float y2, float z2,
                        float x3, float y3, float z3)
    {  rewindBuffer(gl);
       putVertex(x1,y1,z1);           // Eckpunkte in VertexArray speichern
       putVertex(x2,y2,z2);
       putVertex(x3,y3,z3);
       int nVertices = 3;
       copyBuffer(gl, nVertices);
       gl.glDrawArrays(GL3.GL_TRIANGLES, 0, nVertices);
    }

    //  ----------  OpenGL-Events   ---------------------------

    @Override
    public void init(GLAutoDrawable drawable)
    {  super.init(drawable);
       GL3 gl = drawable.getGL().getGL3();
       gl.glClearColor(0,0,1,1);                         // Hintergrundfarbe (RGBA)
       gl.glDisable(GL3.GL_DEPTH_TEST);                  // ohne Sichtbarkeitstest
    }


    @Override
    public void display(GLAutoDrawable drawable)
    { GL3 gl = drawable.getGL().getGL3();
      gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
      setColor(1,1,1);
      drawAxis(gl, 8,8,8);             //  Koordinatenachsen
      setColor(1,0,0);
      zeichneDreieck(gl,-0.5f,-0.5f,0, 0.5f,-0.5f,0, 0,0.5f,0);
    }


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y,
                        int width, int height)
    {  GL3 gl = drawable.getGL().getGL3();
       // Set the viewport to be the entire window
       gl.glViewport(0, 0, width, height);

       // -----  Projektionsmatrix festlegen  -----
       setOrthogonalProjection(gl,left,right,bottom,top,near,far);
    }


    //  -----------  main-Methode  ---------------------------

    public static void main(String[] args)
    { new MyFirst2D();
    }

}