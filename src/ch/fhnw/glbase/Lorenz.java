package ch.fhnw.glbase;//  -------------   JOGL 2D-Programm  -------------------

import ch.fhnw.util.Trail;
import ch.fhnw.util.math.Dynamic;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import java.awt.event.KeyEvent;
import java.lang.annotation.Repeatable;
import java.util.Arrays;

public class Lorenz extends GLBase1 {

    //  ---------  globale Daten  ---------------------------

    float left = -50, right = 50;             // ViewingVolume
    float bottom, top;
    float near = -10, far = 1000;

    float dCam = 60;
    float elevation = 10;
    float azimut = 40;

    double dt = 0.01;
    double[] x0 = {10, 10, 10};
    Satellite sat = new Satellite(x0, 0.5);
    Trail trail = new Trail(this);
    RotKoerper rotk = new RotKoerper(this);

    //  ---------  Methoden  ----------------------------------



    //  ----------  OpenGL-Events   ---------------------------

    @Override
    public void init(GLAutoDrawable drawable) {
        super.init(drawable);
        f.addKeyListener(this);
        GL3 gl = drawable.getGL().getGL3();
        gl.glClearColor(0.8f, 0.8f, 0.8f, 1);                         // Hintergrundfarbe (RGBA)
        FPSAnimator anim = new FPSAnimator(canvas, 60, true);
        anim.start();
    }


    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
        loadIdentity(gl);
        setCameraSystem(gl, dCam, elevation, azimut);
        setLightPosition(gl, -20, 10, 0);
        setColor(1, 1, 1, 1);
        translate(gl, 0, 0, -10);
        trail.add(sat.x[0], sat.x[1], sat.x[2]);
        trail.draw(gl);
        setShadingLevel(gl, 1);
        sat.draw(gl);
        setShadingLevel(gl, 0);
        sat.move(dt);
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

    //  -----------  main-Methode  ---------------------------

    public static void main(String[] args) {
        new Lorenz();
    }

    // ---------------  Classes -------------------------------
    class Satellite extends Dynamic{
        public Satellite(double[] x, double r) {
            this.x = Arrays.copyOf(x, x.length);
            this.r = r;
        }

        double[] x;
        double r;


        public void draw(GL3 gl) {
            pushMatrix(gl);
            translate(gl, (float)x[0], (float)x[1], (float)x[2]);
            setColor(1, 0, 0, 0);
            rotk.zeichneKugel(gl, (float)r, 20, 20);
            popMatrix(gl);
        }

        @Override
        public double[] f(double[] x) {
            return new double[]{
                    10*x[1]-10*x[0],
                    28*x[0]-x[1]-x[0]*x[2],
                    x[0]*x[1]-(8/3)*x[2]
            };
        }

        public void move(double dt) {
//            x = euler(x, dt);
            x = runge(x, dt);
        }
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