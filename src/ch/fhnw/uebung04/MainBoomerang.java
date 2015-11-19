package ch.fhnw.uebung04;//  -------------   JOGL 3D-Programm  -------------------

import ch.fhnw.glbase.GLBase1;
import ch.fhnw.util.Timer;
import ch.fhnw.util.bodys.Ground;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import java.awt.event.KeyEvent;

@SuppressWarnings("Duplicates")
public class MainBoomerang extends GLBase1 {

    //  ---------  globale Daten  ---------------------------

    float left = -32, right = 32;
    float bottom, top;
    float near = -20, far = 1000;

    float dCam = 20;                 // Abstand vom absoluten Nullpunkt
    float elevation = 15;            // Orientierung
    float azimut = 20;

    Timer timer = new Timer();
    Boomerang boomerang;
    Boomerang boomerang2;
    Ground ground;
    FPSAnimator anim;

    //  ---------  Methoden  ----------------------------------

    public MainBoomerang() {
        super();
    }

    //  ----------  OpenGL-Events   ---------------------------

    @Override
    public void init(GLAutoDrawable drawable) {
        super.init(drawable);
        boomerang = new Boomerang(this, "daywalker_scaled.obj", 0, 25);
        boomerang2 = new Boomerang(this, "boomerang_scaled.obj", 10, 30);
        ground = new Ground(this, 30, -2);
        timer.addObject(boomerang);
        timer.addObject(boomerang2);
        new Thread(timer).start();
        GL3 gl = drawable.getGL().getGL3();
        gl.glClearColor(0.2f, 0.2f, 1, 1);
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
        setColor(1f,0f, 0f, 1);
        setLightPosition(gl, 0, 6, 10);
        //drawAxis(gl, 8, 8, 8);             //  Koordinatenachsen
        boomerang.draw(gl);
        boomerang2.draw(gl);
        setColor(0.1f, 1, 0.1f, 1);
        ground.draw(gl);
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
        int code = e.getKeyCode();
        switch(code) {
            case KeyEvent.VK_D:
                azimut++;
                break;
            case KeyEvent.VK_W:
                elevation++;
                break;
            case KeyEvent.VK_S:
                elevation--;
                break;
            case KeyEvent.VK_A:
                azimut--;
                break;
        }
        canvas.display();
    }
}