package ch.fhnw.uebung04;//  -------------   JOGL 3D-Programm  -------------------

import ch.fhnw.glbase.GLBase1;
import ch.fhnw.util.Mesh;
import ch.fhnw.util.OBJReader;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import java.awt.event.KeyEvent;

@SuppressWarnings("Duplicates")
public class MainBoomerang extends GLBase1 {

    //  ---------  globale Daten  ---------------------------

    float left = -20, right = 20;
    float bottom, top;
    float near = -10, far = 1000;

    float dCam = 10;                 // Abstand vom absoluten Nullpunkt
    float elevation = 30;            // Orientierung
    float azimut = 20;

    Boomerang boomerang;
    Boomerang boomerang2;
    FPSAnimator anim;

    //  ---------  Methoden  ----------------------------------

    public MainBoomerang() {

    }

    //  ----------  OpenGL-Events   ---------------------------

    @Override
    public void init(GLAutoDrawable drawable) {
        super.init(drawable);
        boomerang = new Boomerang(this);
        boomerang2 = new Boomerang(this);
        GL3 gl = drawable.getGL().getGL3();
        setShadingLevel(gl, 1);
        setLightPosition(gl, 5, 5, 3);
        anim = new FPSAnimator(canvas, 60, true);
        anim.start();
    }


    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);

        // ------  Kamera-System  -------

        setCameraSystem(gl, dCam, elevation, azimut);
        setColor(0.8f, 0.8f, 0.8f, 1);
        setLightPosition(gl, 0, 6, 10);
        drawAxis(gl, 8, 8, 8);             //  Koordinatenachsen
        boomerang.draw(gl);
    }


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y,
                        int width, int height) {
        GL3 gl = drawable.getGL().getGL3();
        // Set the viewport to be the entire window
        gl.glViewport(0, 0, width, height);

        // -----  Projektionsmatrix festlegen  -----
        float aspect = (float) height / width;
        bottom = aspect * left;
        top = aspect * right;
        setOrthogonalProjection(gl, left, right, bottom, top, near, far);
    }


    //  -----------  main-Methode  ---------------------------

    public static void main(String[] args) {
        new MainBoomerang();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }
}