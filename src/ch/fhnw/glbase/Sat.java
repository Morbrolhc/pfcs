package ch.fhnw.glbase;//  -------------   JOGL 2D-Programm  -------------------

import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import java.awt.event.KeyEvent;

public class Sat extends GLBase1 {

    //  ---------  globale Daten  ---------------------------

    float left = -45, right = 45;             // ViewingVolume
    float bottom, top;
    float near = -10, far = 1000;

    float dCam = 60;
    float elevation = 10;
    float azimut = 40;

    final double g = 9.81e-6;
    final double rE = 6.371;
    final double GM = g*rE*rE;


    double dt = 0.017;
    double r1 = 42.05;
    double v1 = Math.sqrt(GM/r1);
    Satellite sat = new Satellite(r1, 0, 0, v1, 1);

    double r2 = 26.56;
    double v2 = Math.sqrt(GM/r2);
    Satellite sat2 = new Satellite(r2, 0, 0,v2, 0.5f);

    RotKoerper rotk = new RotKoerper(this);

    //  ---------  Methoden  ----------------------------------



    public void drawEarth(GL3 gl) {
        rotk.zeichneKugel(gl, (float)rE, 20, 20);
    }


    //  ----------  OpenGL-Events   ---------------------------

    @Override
    public void init(GLAutoDrawable drawable) {
        super.init(drawable);
        f.addKeyListener(this);
        GL3 gl = drawable.getGL().getGL3();
        gl.glClearColor(0, 0, 0, 1);                         // Hintergrundfarbe (RGBA)
        FPSAnimator anim = new FPSAnimator(canvas, 60, true);
        anim.start();
    }


    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
        loadIdentity(gl);
        setCameraSystem(gl, dCam, elevation, azimut);
        setShadingLevel(gl, 1);
        setLightPosition(gl, -20, 10, 0);
        setColor(0, 1, 0, 1);
        drawEarth(gl);
        setColor(0, 0, 0, 1);
        rotate(gl, -90, 1, 0, 0);
        sat.draw(gl);
        rotate(gl, 55, 0, 1, 0);
        sat2.draw(gl);
        for(int i = 0; i < 4000; i++) {
            sat.move();
            sat2.move();
        }
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
        new Sat();
    }

    // ---------------  Classes -------------------------------
    class Satellite {
        public Satellite(double x, double y,
                         double vx, double vy,
                         double r) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.r = r;
        }

        double x, y;
        double vx, vy;
        double r;

        public void move() {
            double r = Math.sqrt(x*x+y*y);
            double r3 = r*r*r;
            double ax = (GM/r3 * x)*-1, ay = (GM/r3 * y)*-1;
            x += vx * dt;
            y += vy * dt;
            vx += ax * dt;
            vy += ay * dt;
        }

        public void draw(GL3 gl) {
            pushMatrix(gl);
            translate(gl, (float)x, (float)y, 0);
            setColor(1, 0, 0, 0);
            rotk.zeichneKugel(gl, (float)r, 20, 20);
            popMatrix(gl);
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