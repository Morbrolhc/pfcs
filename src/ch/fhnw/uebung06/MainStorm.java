package ch.fhnw.uebung06;//  -------------   JOGL 3D-Programm  -------------------

import ch.fhnw.glbase.GLBase1;
import ch.fhnw.util.Timer;
import ch.fhnw.util.bodys.Cuboid;
import ch.fhnw.util.bodys.FlyingCube;
import ch.fhnw.util.bodys.FlyingCuboid;
import ch.fhnw.util.properties.IAnimatable;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class MainStorm extends GLBase1 {

    //  ---------  globale Daten  ---------------------------

    float left = -0.06f, right = 0.06f;
    float bottom, top;
    float near = 0.4f, far = 2000;

    float dCam = 0;                 // Abstand vom absoluten Nullpunkt
    float elevation = 0;            // Orientierung
    float azimut = 0;

    boolean fullscreen = false;
    Timer timer;
    FPSAnimator anim = new FPSAnimator(canvas, 60, true);
    List<IAnimatable> objects = new ArrayList<>();
    Menu menu;
    Cuboid cub = new Cuboid(this, 0.02f, 0.02f, 0.02f, 1, 1, 1);

    //  ---------  Methoden  ----------------------------------

    public MainStorm() {
        super();
        menu = new Menu(this);
        objects.add(new FlyingCuboid(cub, 1));
        timer = new Timer(objects);
        new Thread(timer).start();
    }


    public Cuboid getCuboid() {
        return cub;
    }

    //  ----------  OpenGL-Events   ---------------------------

    @Override
    public void init(GLAutoDrawable drawable) {
        super.init(drawable);
        GL3 gl = drawable.getGL().getGL3();
        setShadingLevel(gl, 1);
        setLightPosition(gl, 5, 5, 0);
        gl.glClearColor(0, 0, 0, 1);
        anim.start();
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
        rewindBuffer(gl);
        for(IAnimatable a : objects) a.draw(gl);
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
        setPerspectiveProjection(gl, left, right, bottom, top, near, far);
    }


    //  -----------  main-Methode  ---------------------------

    public static void main(String[] args) {
        new MainStorm();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}