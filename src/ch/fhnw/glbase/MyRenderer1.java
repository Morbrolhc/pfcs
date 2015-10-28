package ch.fhnw.glbase;//  --------  Interface to OpenGL 3.0  --------------
//                                        E. Gutknecht Juli 2015

import ch.fhnw.util.math.Mat4;
import ch.fhnw.util.math.Vec3;

import javax.media.opengl.GL;
import javax.media.opengl.GL3;
import javax.media.opengl.awt.GLCanvas;


public interface MyRenderer1
{

    public GLCanvas getGLCanvas();                         // OpenGL-Window

    //  --------  Vertex-Methoden  -----------

    public void setColor(float r, float g, float b);       // aktuelle Vertexfarbe setzen

    public void setNormal(float x, float y, float z);

    public void setShadingLevel(GL3 gl, int level);

    public void setLightPosition(GL3 gl, float x, float y, float z);

    void putVertex(float x, float y, float z);             // Vertex-Daten in Buffer speichern

    public void copyBuffer(GL gl, int nVertices);           // Vertex-Array in OpenGL-Buffer kopieren

    public void rewindBuffer(GL gl);                       // Bufferposition zuruecksetzen


    //  --------  ModelView-Transformation  -----------

    //  ------------  Kamera-System  --------------------

    public void setCameraSystem(GL3 gl, float r,                                     // Abstand der Kamera von O
                                float elevation,                                     // Elevationswinkel in Grad
                                float azimut);                                       // Azimutwinkel in Grad


    public void setCameraSystem(GL3 gl, Vec3 A, Vec3 B, Vec3 up);                    // LookAt-Positionierung


    //  ---------  Operationen fuer ModelView-Matrix  --------------------

    public void rotate(GL3 gl, float phi, float x, float y, float z);                 // Objekt-System drehen, phi in Grad

    public void translate(GL3 gl, float x, float y, float z);                         // Objekt-System verschieben

    public void scale(GL3 gl, float scale);                                           // Skalierung Objekt-System

    public void rotateCam(GL3 gl, float phi, float x, float y, float z);              // Kamera-System drehen, phi in Grad

    public void translateCam(GL3 gl, float x, float y, float z);                      // Kamera-System verschieben

    public void pushMatrix(GL3 gl);                                                   // ModelView-Matrix speichern

    public void popMatrix(GL3 gl);                                                    // ModelView-Matrix vom Stack holen

    public Mat4 getModelViewMatrix(GL3 gl);

    public void setModelViewMatrix(GL3 gl, Mat4 M);

    public void loadIdentity(GL3 gl);                                                 // Rueckstellung auf Einheitsmatrix


    //  ---------  Projektion auf Bildebene -------------------

    public void setOrthogonalProjection(GL3 gl, float left, float right,           // Grenzen des ViewingVolumes
                                        float bottom, float top,
                                        float near, float far);


    public void setPerspectiveProjection(GL3 gl, float left, float right,          // Grenzen des ViewingVolumes
                                         float bottom, float top,
                                         float near, float far);


}
