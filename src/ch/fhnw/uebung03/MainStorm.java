package ch.fhnw.uebung03;//  -------------   JOGL 3D-Programm  -------------------

import ch.fhnw.glbase.GLBase1;
import ch.fhnw.util.Timer;
import ch.fhnw.util.bodys.FlyingCuboid;
import ch.fhnw.util.properties.IAnimatable;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class MainStorm extends GLBase1 {

    //  ---------  globale Daten  ---------------------------

    float left = -5, right = 5;
    float bottom, top;
    float near = -10, far = 1000;

    float dCam = 10;                 // Abstand vom absoluten Nullpunkt
    float elevation = 10;            // Orientierung
    float azimut = 20;

    Timer timer;
    FPSAnimator anim;
    List<IAnimatable> objects = new ArrayList<>();
    FlyingCuboid cub;

    //  ---------  Methoden  ----------------------------------

    public MainStorm() {
        super();
        objects.add(new FlyingCuboid(this, 0.3f, 0.3f, 0.3f, 1, 1, 0,
                (float)Math.random(), (float)Math.random(), (float)Math.random(), 1));
        objects.add(new FlyingCuboid(this, 0.3f, 0.3f, 0.3f, 1, 0, 0,
                (float)Math.random(), (float)Math.random(), (float)Math.random(), 2));
        objects.add(new FlyingCuboid(this, 0.3f, 0.3f, 0.3f, 1, -1, 0,
                (float)Math.random(), (float)Math.random(), (float)Math.random(), 3));
        timer = new Timer(objects);
        new Thread(timer).start();
    }
    //  ----------  OpenGL-Events   ---------------------------

    @Override
    public void init(GLAutoDrawable drawable) {
        super.init(drawable);
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
        setColor(0.8f, 0.8f, 0f);
        setLightPosition(gl, 0, 6, 10);
        drawAxis(gl, 8, 8, 8);             //  Koordinatenachsen
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
        setOrthogonalProjection(gl, left, right, bottom, top, near, far);
    }


    //  -----------  main-Methode  ---------------------------

    public static void main(String[] args) {
        new MainStorm();
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