package ch.fhnw.uebung05;//  -------------   JOGL 3D-Programm  -------------------

import ch.fhnw.glbase.GLBase1;
import ch.fhnw.uebung04.Boomerang;
import ch.fhnw.util.Timer;
import ch.fhnw.util.bodys.Circle;
import ch.fhnw.util.bodys.Ground;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import java.awt.event.KeyEvent;

@SuppressWarnings("Duplicates")
public class MainFlow extends GLBase1 {

    public static final double R = 0.2;

    //  ---------  globale Daten  ---------------------------

    float left = -1, right = 1;
    float bottom, top;
    float near = -20, far = 100;

    Circle circle = new Circle(this, 0.2f, 40, 0, 0);
    FlowLine lines = new FlowLine(this, R);
    Timer timer = new Timer();
    FPSAnimator animator;

    //  ---------  Methoden  ----------------------------------

    public MainFlow() {
        super();
    }

    //  ----------  OpenGL-Events   ---------------------------

    @Override
    public void init(GLAutoDrawable drawable) {
        super.init(drawable);
        GL3 gl = drawable.getGL().getGL3();
        gl.glClearColor(0.7f, 0.7f, 0.7f, 1);

        animator = new FPSAnimator(canvas, 60, true);
        animator.start();

            timer.addObject(lines);
        new Thread(timer).start();

    }


    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);

        // ------  Kamera-System  -------

        setColor(0.15f, 0.15f, 0.15f, 1);
        circle.draw(gl);

        lines.draw(gl);
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


    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch(code) {
            case KeyEvent.VK_1:
                lines.setCirculation(false);
                lines.setIdeal(true);
                break;
            case KeyEvent.VK_2:
                lines.setCirculation(true);
                lines.setIdeal(false);
                break;
            case KeyEvent.VK_3:
                lines.setCirculation(true);
                lines.setIdeal(true);
                break;
            case KeyEvent.VK_4:
                lines.setRandom();
                break;
        }
    }


    //  -----------  main-Methode  ---------------------------

    public static void main(String[] args) {
        new MainFlow();
    }

}