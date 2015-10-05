package ch.fhnw.uebung02;//  -------------   JOGL 2D-Programm  -------------------

import ch.fhnw.glbase.GLBase1;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;

public class Application extends GLBase1 {

    //  ---------  globale Daten  ---------------------------

    float left = -4, right = 4;             // ViewingVolume
    float bottom, top;
    float near = -10, far = 1000;
    FPSAnimator anim;

    Car actor = new Car(0.5f, 1f, 0.5f);
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

    public void drawFrontAxis(GL3 gl, Car car, float ym) {
        pushMatrix(gl);
        translate(gl, car.w, car.h / 2 + car.h * 0.1f + car.tyreH / 2, 0);
        rotate(gl, (float) Math.toDegrees(car.alpha), 0, 0, 1);
        drawTyre(gl, car);
        popMatrix(gl);
        pushMatrix(gl);
        translate(gl, car.w, -(car.h / 2 + car.h * 0.1f + car.tyreH / 2), 0);
        float beta = (float)Math.atan(car.w/(ym + car.h/2));
        rotate(gl, (float) Math.toDegrees(beta), 0, 0, 1);
        drawTyre(gl, car);
    }

    //  ----------  OpenGL-Events   ---------------------------

    @Override
    public void init(GLAutoDrawable drawable) {
        super.init(drawable);
        GL3 gl = drawable.getGL().getGL3();
        gl.glClearColor(0, 0, 1, 1);                         // Hintergrundfarbe (RGBA)
        gl.glDisable(GL3.GL_DEPTH_TEST);                  // ohne Sichtbarkeitstest
        anim = new FPSAnimator(canvas, 60, true);
        anim.start();
    }


    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
        loadIdentity(gl);
        setColor(1, 1, 1);
        drawAxis(gl, 8, 8, 8);             //  Koordinatenachsen
        rotate(gl, actor.delta++, 0, 0, 1);
        float ym = actor.h/2 + (float)(actor.w/Math.tan(actor.alpha));
        translate(gl, 0, -ym, 0);
        setColor(1, 0, 0);
        drawCar(gl, actor);
        drawFrontAxis(gl, actor, ym);
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

}