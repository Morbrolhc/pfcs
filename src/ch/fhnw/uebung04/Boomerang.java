package ch.fhnw.uebung04;

import ch.fhnw.glbase.MyRenderer1;
import ch.fhnw.util.Mesh;
import ch.fhnw.util.properties.IAnimatable;

import javax.media.opengl.GL3;

/**
 * Created by joel on 11.11.15.
 */
public class Boomerang implements IAnimatable {

    private float alpha;
    private float beta = (float)Math.random()*360;
    private float incl;
    private float r;


    private Mesh mesh;

    public Boomerang(MyRenderer1 _renderer, String model, float _incl, float _r) {
        r = _r;
        incl = _incl;
        mesh = Mesh.factory(model, _renderer);
    }

    @Override
    public void update(double dTime) {
        alpha += dTime * 1200;
        beta += dTime *80;

        if(alpha >= 360) alpha = 0;
        if(beta >= 360) beta = 0;
    }

    @Override
    public void draw(GL3 gl) {
        MyRenderer1 renderer = mesh.getRenderer();
        renderer.pushMatrix(gl);
        renderer.translate(gl, 0, (float)(Math.cos(Math.toRadians(90-incl))*r), 0);  //Bahnhöhe anpassen 
        renderer.rotate(gl, incl, 0, 0, 1);  //Bahn anwinkeln
        renderer.rotate(gl, beta, 0, 1, 0);   //Körper auf Bahne rotieren
        renderer.translate(gl,r, 0, 0);    //Körper auf die Bahn verschieben
        renderer.rotate(gl, 55, 0, 0, 1);  //Körper anstellen
        renderer.rotate(gl, alpha, 0, 1, 0); //Körper rotieren
        mesh.draw(gl);
        renderer.popMatrix(gl);
    }
}
