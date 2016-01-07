package ch.fhnw.uebung06;//  -------------   JOGL 3D-Programm  -------------------

import ch.fhnw.glbase.GLBase1;
import ch.fhnw.util.Timer;
import ch.fhnw.util.bodys.Cuboid;
import ch.fhnw.util.bodys.FlyingCuboid;
import ch.fhnw.util.properties.IAnimatable;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class MainCuboid extends GLBase1 {

    //  ---------  globale Daten  ---------------------------

    float left = -0.06f, right = 0.06f;
    float bottom, top;
    float near = 0.4f, far = 2000;

    float dCam = 0;                 // Abstand vom absoluten Nullpunkt
    float elevation = 0;            // Orientierung
    float azimut = 0;

    Timer timer = new Timer();
    FPSAnimator anim = new FPSAnimator(canvas, 60, true);
    List<FlyingCuboid> objects = new ArrayList<>();
    Menu menu;

    //  ---------  Methoden  ----------------------------------

    public MainCuboid() {
        super();
        menu = new Menu(this);
        generateCuboids();
        process();
        new Thread(timer).start();
    }

    private void generateCuboids() {
        for(int i = 0; i < 100; i++) {
            FlyingCuboid obj = new FlyingCuboid(this)   ;
            objects.add(obj);
            timer.addObject(obj);
        }
    }

    private void process() {
        for (int i = 0; i < 1000; i++){
            objects.forEach(o -> o.update(0.1));
        }
    }

    public void setColor(float r, float g, float b) {
        objects.forEach(o -> o.setColor(r, g, b));
    }

    //  ----------  OpenGL-Events   ---------------------------

    @Override
    public void init(GLAutoDrawable drawable) {
        super.init(drawable);
        GL3 gl = drawable.getGL().getGL3();
        setShadingLevel(gl, 1);
        setLightPosition(gl, 0, 0, 0);
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
        new MainCuboid();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}