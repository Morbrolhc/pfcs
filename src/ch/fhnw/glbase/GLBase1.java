package ch.fhnw.glbase;//  -------------   JOGL Basis-Programm (fuer Erweiterungenh mittels 'extends')  -------------------
//
//                                                            E.Gutknecht, Juli 2015
//  adaptiert von:
//  http://www.lighthouse3d.com/cg-topics/code-samples/opengl-3-3-glsl-1-5-sample/
//

import ch.fhnw.util.math.Mat4;
import ch.fhnw.util.math.Vec3;
import ch.fhnw.util.math.Vec4;
import com.jogamp.common.nio.Buffers;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.FloatBuffer;
import java.util.Stack;

public class GLBase1
        implements WindowListener, GLEventListener, KeyListener, MouseListener, MyRenderer1 {

    //  --------------  Default-Werte ----------------------------------------

    String windowTitle = "JOGL";
    protected int windowWidth = 800;
    protected int windowHeight = 600;
    String vShader = "vShader1.glsl";               // Filename Vertex-Shader
    String fShader = "fShader1.glsl";               // Filename Fragment-Shader
    int maxVerts = 2048;                            // max. Anzahl Vertices im Vertex-Array


    //  --------------  globale Daten  -------------------------------------

    float[] clearColor = {0, 0, 1, 1};                 // Fensterhintergrund (Blau)
    public GLCanvas canvas;                                // OpenGL Window
    protected JFrame f;
    int shadingLevel;
    Vec4 lightPosition = new Vec4(0,0,10,1);


    //  --------  Vertex-Array (fuer die Attribute Position, Color, Normal, Texture-Coord)  ------------

    FloatBuffer vertexBuf;                              // Vertex-Array
    final int vPositionSize = 4 * Float.SIZE / 8;           // Anz. Bytes der x,y,z,w (homogene Koordinaten)
    final int vColorSize = 4 * Float.SIZE / 8;              // Anz. Bytes der rgba Werte
    final int vNormalSize = 4 * Float.SIZE / 8;
    final int vertexSize = vPositionSize + vColorSize + vNormalSize;  // Anz. Bytes eines Vertex
    int bufSize;                                        // Anzahl Bytes des VertexArrays = maxVerts * vertexSize

    float[] currentColor = {1, 1, 1, 1};                  // aktuelle Farbe fuer Vertices
    float[] currentNormal = {1, 0, 0, 0};


    //  --------  ModelView-Transformation  ---------

    Mat4 viewMatrix = Mat4.ID;                          // ModelView-Matrix
    Stack<Mat4> matrixStack = new Stack<Mat4>();


    // ------ Identifiers fuer OpenGL-Objekte und Shader-Variablen  ------

    int program;                                        // Program-Object
    int vaoId;                                          // Identifier fuer OpenGL VertexArray Object
    int vertexBufId;                                    // Identifier fuer OpenGL Vertex Buffer

    int projMatrixLoc, viewMatrixLoc;                   // Uniform Shader Variables
    int shadingLevelLoc, lightPositionLoc;

    int vPositionLocation, vColorLocation, vNormalLocation;              // Vertex Attribute Shader Variables


    //  ------------- Methoden  ---------------------------

    public GLBase1()                                   // Konstruktor ohne Parameter
    {
        createFrame();
    }


    public GLBase1(String windowTitle,                 // Konstruktor
                   int windowWidth, int windowHeight,
                   String vShader, String fShader,      // Filenamen Vertex-/Fragment-Shader
                   int maxVerts)                        // max. Anzahl Vertices im Vertex-Array
    {
        this.windowTitle = windowTitle;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.vShader = vShader;
        this.fShader = fShader;
        this.maxVerts = maxVerts;
        createFrame();
    }

    ;


    private void createFrame()                         // Fenster erzeugen
    {
        f = new JFrame(windowTitle);
        f.setSize(windowWidth, windowHeight);
        f.addWindowListener(this);
        GLProfile glp = GLProfile.get(GLProfile.GL3);
        GLCapabilities glCaps = new GLCapabilities(glp);
        canvas = new GLCanvas(glCaps);
        canvas.addGLEventListener(this);
        f.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("logo.png")).getImage());
        f.setLayout(new BorderLayout());
        f.add(canvas, BorderLayout.CENTER);
        f.setVisible(true);
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
    }

    ;


    private void setupVertexBuffer(int pgm, GL3 gl, int maxVerts) {
        bufSize = maxVerts * vertexSize;
        vertexBuf = Buffers.newDirectFloatBuffer(bufSize);
        // ------  OpenGl-Objekte -----------
        int[] tmp = new int[1];
        gl.glGenVertexArrays(1, tmp, 0);                 // VertexArrayObject
        vaoId = tmp[0];
        gl.glBindVertexArray(vaoId);
        gl.glGenBuffers(1, tmp, 0);                      // VertexBuffer
        vertexBufId = tmp[0];
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vertexBufId);
        gl.glBufferData(GL3.GL_ARRAY_BUFFER, bufSize,            // Speicher allozieren
                null, GL3.GL_STATIC_DRAW);

        // ----- get shader variable identifiers  -------------
        vPositionLocation = gl.glGetAttribLocation(pgm, "vertexPosition");
        vColorLocation = gl.glGetAttribLocation(pgm, "vertexColor");
        vNormalLocation = gl.glGetAttribLocation(pgm, "vertexNormal");

        //  ------  enable vertex attributes ---------------
        gl.glEnableVertexAttribArray(vPositionLocation);
        gl.glEnableVertexAttribArray(vColorLocation);
        gl.glEnableVertexAttribArray(vNormalLocation);
        gl.glVertexAttribPointer(vPositionLocation, 4, GL3.GL_FLOAT, false, vertexSize, 0);
        gl.glVertexAttribPointer(vColorLocation, 4, GL3.GL_FLOAT, false, vertexSize, vPositionSize);
        gl.glVertexAttribPointer(vNormalLocation, 4, GL3.GL_FLOAT, false, vertexSize, vPositionSize + vColorSize);

    }

    ;


    private void setupMatrices(int pgm, GL3 gl) {
        // ----- get shader variable identifiers  -------------
        projMatrixLoc = gl.glGetUniformLocation(pgm, "projMatrix");
        viewMatrixLoc = gl.glGetUniformLocation(pgm, "viewMatrix");

        // -----  set uniform variables  -----------------------
        float[] identityMatrix = {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
        gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, identityMatrix, 0);
        gl.glUniformMatrix4fv(projMatrixLoc, 1, false, identityMatrix, 0);
    }

    private void setupLighting(int pgm, GL3 gl) {
        // ----- get shader variable identifiers  -------------
        shadingLevelLoc = gl.glGetUniformLocation(pgm, "ShadingLevel");
        lightPositionLoc = gl.glGetUniformLocation(pgm, "LightPosition");
        gl.glUniform1i(shadingLevelLoc, shadingLevel);
        gl.glUniform4fv(lightPositionLoc, 1, lightPosition.toArray(), 0);
    }


    //  ----------  oeffentliche Methoden (fuer Verwendung in Erweiterungsklassen)  -------------

    public GLCanvas getGLCanvas()                              // OpenGL Window-Handle
    {
        return canvas;
    }


    // --------  Vertex-Methoden  --------

    public void setColor(float r, float g, float b, float a)             // aktuelle Vertexfarbe setzen
    {
        currentColor[0] = r;
        currentColor[1] = g;
        currentColor[2] = b;
        currentColor[3] = a;
    }

    public void setNormal(float x, float y, float z) {
        currentNormal[0] = x;
        currentNormal[1] = y;
        currentNormal[2] = z;
        currentNormal[3] = 0;
    }

    public void setShadingLevel(GL3 gl, int level){
        shadingLevel = level;
        gl.glUniform1i(shadingLevelLoc, shadingLevel);
    }

    public void setLightPosition(GL3 gl, float x, float y, float z){
        Vec4 tmp = new Vec4(x, y, z, 1);
        lightPosition = viewMatrix.transform(tmp);
        gl.glUniform4fv(lightPositionLoc, 1, lightPosition.toArray(), 0);
    }


    public void putVertex(float x, float y, float z)            // Vertex-Daten in Buffer speichern
    {
        vertexBuf.put(x);
        vertexBuf.put(y);
        vertexBuf.put(z);
        vertexBuf.put(1);
        vertexBuf.put(currentColor[0]);                          // Farbe
        vertexBuf.put(currentColor[1]);
        vertexBuf.put(currentColor[2]);
        vertexBuf.put(currentColor[3]);
        vertexBuf.put(currentNormal[0]);                          // Farbe
        vertexBuf.put(currentNormal[1]);
        vertexBuf.put(currentNormal[2]);
        vertexBuf.put(currentNormal[3]);
    }


    public void copyBuffer(GL gl, int nVertices)                // Vertex-Array in OpenGL-Buffer kopieren
    {
        vertexBuf.rewind();
        if (nVertices > maxVerts)
            throw new IndexOutOfBoundsException();
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vertexBufId);
        gl.glBufferSubData(GL3.GL_ARRAY_BUFFER, 0, nVertices * vertexSize, vertexBuf);
    }


    public void rewindBuffer(GL gl)                            // Bufferposition zuruecksetzen
    {
        vertexBuf.rewind();
    }


    //  --------  Methoden fuer ModelView-Transformation  -----------


    // ---------  Kamera-System festlegen  ----------

    public void setCameraSystem(GL3 gl, float r,                // Abstand der Kamera von O
                                float elevation,                // Elevationswinkel in Grad
                                float azimut)                   // Azimutwinkel in Grad
    {
        float toRad = (float) (Math.PI / 180);
        float c = (float) Math.cos(toRad * elevation);
        float s = (float) Math.sin(toRad * elevation);
        float cc = (float) Math.cos(toRad * azimut);
        float ss = (float) Math.sin(toRad * azimut);
        viewMatrix = new Mat4(cc, -s * ss, c * ss, 0, 0, c, s, 0, -ss, -s * cc, c * cc, 0, 0, 0, -r, 1);
        gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, viewMatrix.toArray(), 0);
    }


    public void setCameraSystem(GL3 gl, Vec3 A, Vec3 B, Vec3 up)       // LookAt-Psotionierung
    {
        viewMatrix = Mat4.lookAt(A, B, up);
        gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, viewMatrix.toArray(), 0);
    }


    //  ---------  Operationen fuer ModelView-Matrix  --------------------

    public void rotate(GL3 gl, float phi,                                           // Objekt-System drehen, phi in Grad
                       float x, float y, float z)                                   // Drehachse
    {
        viewMatrix = viewMatrix.postMultiply(Mat4.rotate(phi, x, y, z));
        gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, viewMatrix.toArray(), 0);
    }

    public void translate(GL3 gl,                                                   // Objekt-System verschieben
                          float x, float y, float z) {
        viewMatrix = viewMatrix.postMultiply(Mat4.translate(x, y, z));
        gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, viewMatrix.toArray(), 0);
    }


    public void scale(GL3 gl, float scale)                                          // Skalierung Objekt-System
    //  nur ein xyz-Faktor wegen Normalentransformation
    {
        viewMatrix = viewMatrix.postMultiply(Mat4.scale(scale, scale, scale));
        gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, viewMatrix.toArray(), 0);
    }


    public void rotateCam(GL3 gl, float phi,                                        // Kamera-System drehen, phi in Grad
                          float x, float y, float z) {
        viewMatrix = viewMatrix.preMultiply(Mat4.rotate(-phi, x, y, z));
        gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, viewMatrix.toArray(), 0);
    }

    public void translateCam(GL3 gl,                                                 // Kamera-System verschieben
                             float x, float y, float z) {
        viewMatrix = viewMatrix.preMultiply(Mat4.translate(-x, -y, -z));
        gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, viewMatrix.toArray(), 0);
    }


    public void pushMatrix(GL3 gl)                                                   // ModelView-Matrix speichern
    {
        matrixStack.push(viewMatrix);
    }

    public void popMatrix(GL3 gl)                                                    // ModelView-Matrix vom Stack holen
    {
        viewMatrix = matrixStack.pop();
        gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, viewMatrix.toArray(), 0);
    }

    public Mat4 getModelViewMatrix(GL3 gl) {
        return viewMatrix;
    }

    public void setModelViewMatrix(GL3 gl, Mat4 M) {
        viewMatrix = M;
        gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, viewMatrix.toArray(), 0);
    }

    public void loadIdentity(GL3 gl)                                                 // Rueckstellung auf Einheitsmatrix
    {
        viewMatrix = Mat4.ID;
        gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, viewMatrix.toArray(), 0);
    }


    //  ---------  Projektion auf Bildebene -------------------

    public void setOrthogonalProjection(GL3 gl, float left, float right,      // Orthogonal-Projektion auf Bildebene
                                        float bottom, float top,
                                        float near, float far) {
        float m00 = 2.0f / (right - left);
        ;
        float m11 = 2.0f / (top - bottom);
        float m22 = -2.0f / (far - near);
        float m03 = -(right + left) / (right - left);
        float m13 = -(top + bottom) / (top - bottom);
        float m23 = -(far + near) / (far - near);
        float m33 = 1;
        float[] projMatrix = {m00, 0, 0, 0, 0, m11, 0, 0, 0, 0, m22, 0, m03, m13, m23, m33};
        gl.glUniformMatrix4fv(projMatrixLoc, 1, false, projMatrix, 0);
    }


    public void setPerspectiveProjection(GL3 gl, float left, float right,     // Zentralprojektion auf Bildebene
                                         float bottom, float top,
                                         float near, float far) {
        Mat4 projMatrix = Mat4.perspective(left, right, bottom, top, near, far);
        gl.glUniformMatrix4fv(projMatrixLoc, 1, false, projMatrix.toArray(), 0);
    }


    //  ---------  Zeichenmethoden  ------------------------------

    public void drawAxis(GL3 gl, float a, float b, float c)                   // Koordinatenachsen zeichnen
    {
        rewindBuffer(gl);
        putVertex(0, 0, 0);           // Eckpunkte in VertexArray speichern
        putVertex(a, 0, 0);
        putVertex(0, 0, 0);
        putVertex(0, b, 0);
        putVertex(0, 0, 0);
        putVertex(0, 0, c);
        int nVertices = 6;
        copyBuffer(gl, nVertices);
        gl.glDrawArrays(GL3.GL_LINES, 0, nVertices);
    }


    //  --------  OpenGL Event-Methoden fuer Ueberschreibung in Erweiterungsklassen   ----------------

    @Override
    public void init(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        System.out.println("OpenGl Version: " + gl.glGetString(gl.GL_VERSION));
        System.out.println("Shading Language: " + gl.glGetString(gl.GL_SHADING_LANGUAGE_VERSION));
        program = GLShaders.loadShaders(gl, vShader, fShader);
        setupVertexBuffer(program, gl, maxVerts);
        setupLighting(program, gl);
        setupMatrices(program, gl);
        gl.glClearColor(clearColor[0], clearColor[1], clearColor[2], clearColor[3]);
        gl.glEnable(GL3.GL_DEPTH_TEST);
    }


    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
        setColor(0.8f, 0.8f, 0.8f, 1);
        drawAxis(gl, 8, 8, 8);             //  Koordinatenachsen
    }


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y,
                        int width, int height) {
        GL3 gl = drawable.getGL().getGL3();
        // Set the viewport to be the entire window
        gl.glViewport(0, 0, width, height);

        // -----  Projektionsmatrix festlegen  -----
        float left = -1, right = 1;                       // ViewingVolume im Kamera-System
        float bottom = -1, top = 1;
        float near = -10, far = 1000;
        setPerspectiveProjection(gl, left, right, bottom, top, near, far);
    }


    @Override
    public void dispose(GLAutoDrawable drawable) {
    }


    //  ---------  Window-Events  --------------------

    public void windowClosing(WindowEvent e) {
        f.dispose();
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }


    //  ---------  Keuboard-Events ---------------------
    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }


    //  ---------  Mouse-Events ---------------------
    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }


    //  -----------  main-Methode  ---------------------------

    public static void main(String[] args) {
        new GLBase1();
    }

}