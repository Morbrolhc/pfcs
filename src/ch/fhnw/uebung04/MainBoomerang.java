package ch.fhnw.uebung04;//  -------------   JOGL 3D-Programm  -------------------

import ch.fhnw.glbase.GLBase1;
import ch.fhnw.util.Mesh;
import ch.fhnw.util.OBJReader;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import java.awt.event.KeyEvent;

@SuppressWarnings("Duplicates")
public class MainBoomerang extends GLBase1 {

    //  ---------  globale Daten  ---------------------------

    float left = -5, right = 5;
    float bottom, top;
    float near = -10, far = 1000;

    float dCam = 10;                 // Abstand vom absoluten Nullpunkt
    float elevation = 10;            // Orientierung
    float azimut = 20;

    Mesh boomerang;

    //  ---------  Methoden  ----------------------------------

    public MainBoomerang() {

    }

    //  ----------  OpenGL-Events   ---------------------------

    @Override
    public void init(GLAutoDrawable drawable) {
        super.init(drawable);
        boomerang = (new OBJReader()).readMesh("boomerang.obj");
        boomerang.setRenderer(this);
        GL3 gl = drawable.getGL().getGL3();
        setShadingLevel(gl, 1);
        setLightPosition(gl, 5, 5, 3);
    }


    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);

        // ------  Kamera-System  -------

        setCameraSystem(gl, dCam, elevation, azimut);
        setColor(0.8f, 0.8f, 0f, 1);
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