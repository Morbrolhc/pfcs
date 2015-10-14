package ch.fhnw.uebung02;//  -------------   JOGL 2D-Programm  -------------------

import ch.fhnw.glbase.GLBase1;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import java.awt.event.KeyEvent;

public class Application extends GLBase1 {

    //  ---------  globale Daten  ---------------------------

    float left = -20, right = 20;             // ViewingVolume
    float bottom, top;
    float near = -10, far = 1000;
    FPSAnimator anim;
    GL3 gl;

    Car actor = new Car(2f, 4f, 0f);
    //  ---------  Methoden  ----------------------------------

    public void drawCar(GL3 gl, Car car) {
        rewindBuffer(gl);
        putVertex(0, 0 - car.h / 2, 0);
        putVertex(car.w, 0 - car.h / 2, 0);
        putVertex(car.w, 0 + car.h / 2, 0);
        putVertex(0, 0 + car.h / 2, 0);
        int nVertices = 4;
        copyBuffer(gl, 4);
        gl.glDrawArrays(GL3.GL_LINE_LOOP, 0, nVertices);

        rewindBuffer(gl);
        float y1 = car.h / 2 + car.h * 0.1f;
        float y2 = car.h / 2 + car.h * 0.1f + car.tyreH;
        putVertex(0 - car.tyreW/2, y1, 0);
        putVertex(0 + car.tyreW/2, y1, 0);
        putVertex(0 + car.tyreW/2, y2, 0);
        putVertex(0 - car.tyreW/2, y2, 0);
        copyBuffer(gl, 4);
        gl.glDrawArrays(GL3.GL_LINE_LOOP, 0, nVertices);

        rewindBuffer(gl);
        putVertex(0 - car.tyreW/2, -y1, 0);
        putVertex(0 + car.tyreW/2, -y1, 0);
        putVertex(0 + car.tyreW/2, -y2, 0);
        putVertex(0 - car.tyreW/2, -y2, 0);
        copyBuffer(gl, 4);
        gl.glDrawArrays(GL3.GL_LINE_LOOP, 0, nVertices);

        drawFrontAxis(gl, car);
    }

    public void drawTyre(GL3 gl, Car car) {
        rewindBuffer(gl);
        putVertex(-car.tyreW / 2, -car.tyreH / 2, 0);
        putVertex(-car.tyreW / 2, car.tyreH / 2, 0);
        putVertex(car.tyreW / 2, car.tyreH / 2, 0);
        putVertex(car.tyreW/2, -car.tyreH/2, 0);
        copyBuffer(gl, 4);
        gl.glDrawArrays(GL3.GL_LINE_LOOP, 0, 4);
    }

    public void drawFrontAxis(GL3 gl, Car car) {
        pushMatrix(gl);
        translate(gl, car.w, car.h / 2 + car.h * 0.1f + car.tyreH / 2, 0);
        rotate(gl, (float) Math.toDegrees(car.alpha), 0, 0, 1);
        drawTyre(gl, car);
        popMatrix(gl);
        pushMatrix(gl);
        translate(gl, car.w, -(car.h / 2 + car.h * 0.1f + car.tyreH / 2), 0);
        rotate(gl, (float) Math.toDegrees(car.beta), 0, 0, 1);
        drawTyre(gl, car);
        popMatrix(gl);
    }

    public void moveCar(GL3 gl, Car car) {
        if(car.alpha <= 0.05 && car.alpha >= -0.05) {
            translate(gl, car.v*0.017f, 0, 0);
        }
        else {
            translate(gl, 0, car.ym, 0);
            rotate(gl, (car.v*0.017f*180f) / (car.ym*(float)Math.PI), 0, 0, 1);
            translate(gl, 0, -car.ym, 0);
        }
    }

    //  ----------  OpenGL-Events   ---------------------------

    @Override
    public void init(GLAutoDrawable drawable) {
        super.init(drawable);
        gl = drawable.getGL().getGL3();
        gl.glClearColor(0, 0, 1, 1);                         // Hintergrundfarbe (RGBA)
        gl.glDisable(GL3.GL_DEPTH_TEST);                  // ohne Sichtbarkeitstest
        anim = new FPSAnimator(canvas, 60, true);
        anim.start();
    }


    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
        pushMatrix(gl);
        loadIdentity(gl);
        setColor(1, 1, 1);
        drawAxis(gl, 8, 8, 8);
        popMatrix(gl);//  Koordinatenachsen
        setColor(1, 0, 0);
        drawCar(gl, actor);
        moveCar(gl, actor);
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
        new Application();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch(code) {
            case KeyEvent.VK_UP:
                actor.v+=1f;
                break;
            case KeyEvent.VK_LEFT:
                actor.setAlpha(actor.alpha+0.06f);
                break;
            case KeyEvent.VK_RIGHT:
                actor.setAlpha(actor.alpha-0.06f);
                break;
            case KeyEvent.VK_DOWN:
                actor.v-=1f;
                break;
            case KeyEvent.VK_V:
                actor.v *=(-1);
                break;
            case KeyEvent.VK_R:
                actor.setAlpha(0);
                loadIdentity(gl);
                actor.v = 0;
                break;
            case KeyEvent.VK_S:
                if(actor.v == 0) actor.v = 2;
                else actor.v = 0;
        }
    }
}