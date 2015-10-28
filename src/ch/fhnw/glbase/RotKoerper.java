package ch.fhnw.glbase;//  -------------   Rotations-Koerper  (Kugel, Torus, Zylinder)  ------------
//                                                            E.Gutknecht, Juli 2015
import javax.media.opengl.*;
import javax.media.opengl.awt.*;
import ch.fhnw.util.math.*;

public class RotKoerper
{
   //  --------------  globale Daten  -----------------
   MyRenderer1 rd;                                 // OpenGL Kontext fuer PPP


   //  ------------------  Methoden  ------------------

   public RotKoerper(MyRenderer1 renderer)
   {  rd = renderer;
   }


   public void zeichneRotFlaeche(GL3 gl,            // Rotationsflaeche (Rotation um y-Achse)
               float[] x, float[] y,                // Kurve in xy-Ebene
               float[] nx, float[] ny,              // Normalenvektoren
               int n2)                              // Anzahl Drehungen um y-Achse
   {
     float dtheta = (float)(2*Math.PI / n2);        // Drehwinkel
     float c = (float)Math.cos(dtheta);             // Koeff. der Drehmatrix
     float s = (float)Math.sin(dtheta);
     int n1 = x.length;                             // Anzahl Breitenlinien

     float[] xa = new float[n1];                    // Vertex-Koordinaten
     float[] ya = new float[n1];
     float[] za = new float[n1];
     float[] xb = new float[n1];
     float[] yb = new float[n1];
     float[] zb = new float[n1];

     float[] nxa = new float[n1];                   // Normalen
     float[] nya = new float[n1];
     float[] nza = new float[n1];
     float[] nxb = new float[n1];
     float[] nyb = new float[n1];
     float[] nzb = new float[n1];

     float[] tmp;

     for (int i=0; i < n1; i++)                     // erster Streifen
     {  xa[i] = x[i];
        ya[i] = y[i];
        nxa[i] = nx[i];
        nya[i] = ny[i];
     }
     // ------  alle Vertices der Rotationsflaeche in Vertex-Array eintragen  -----
     rd.rewindBuffer(gl);
     for (int j=0; j < n2; j++)                     // n2 Streifen von Norden nach Sueden
     {  for (int i=0; i < n1; i++)
        {  xb[i] = c*xa[i]+s*za[i];                 // Drehung um y-Achse
           yb[i] = ya[i];
           zb[i] = -s*xa[i]+c*za[i];
           nxb[i] = c*nxa[i]+s*nza[i];              // gedrehter Normalenvektor
           nyb[i] = nya[i];
           nzb[i] = -s*nxa[i]+c*nza[i];
        }
        for (int i=0; i < n1; i++)
        {  rd.setNormal(nxa[i],nya[i],nza[i]);
           rd.putVertex(xa[i],ya[i],za[i]);
           rd.setNormal(nxb[i],nyb[i],nzb[i]);
           rd.putVertex(xb[i],yb[i],zb[i]);         // gedrehter Vertex
        }
        tmp=xa; xa=xb; xb=tmp;
        tmp=ya; ya=yb; yb=tmp;
        tmp=za; za=zb; zb=tmp;
        tmp=nxa; nxa=nxb; nxb=tmp;
        tmp=nya; nya=nyb; nyb=tmp;
        tmp=nza; nza=nzb; nzb=tmp;
     }
     // ------  Streifen zeichnen   ------
     int nVertices = 2*n1*n2;
     rd.copyBuffer(gl,nVertices);
     int nVerticesStreifen = 2*n1;                  // Anzahl Vertices eines Streifens
     for (int j=0; j < n2; j++)                     // die Streifen muessen einzeln gezeichnet werden
       gl.glDrawArrays(GL3.GL_TRIANGLE_STRIP,j*nVerticesStreifen,nVerticesStreifen);  // Streifen von Norden nach Sueden
  }


  public void zeichneKugel(GL3 gl, float r, int n1, int n2)
  {  float[] x = new float[n1];                     // Halbkreis in xy-Ebene von Nord- zum Suedpol
     float[] y = new float[n1];
     float[] nx = new float[n1];                    // Normalenvektoren
     float[] ny = new float[n1];
     float dphi = (float)(Math.PI / (n1-1)), phi;
     for (int i = 0; i < n1; i++)
     {  phi  = (float)(0.5*Math.PI) - i*dphi;
        x[i] = r*(float)Math.cos(phi);
        y[i] = r*(float)Math.sin(phi);
        nx[i] = x[i];
        ny[i] = y[i];
     }
     zeichneRotFlaeche(gl,x,y,nx,ny,n2);
   }


   public void zeichneTorus(GL3 gl, float r, float R, int n1, int n2)
   {  int nn1 = n1+1;
      float[] x = new float[nn1];                    // Kreis in xy-Ebene
      float[] y = new float[nn1];
      float[] nx = new float[nn1];                   // Normalenvektoren
      float[] ny = new float[nn1];
      float dphi = 2*(float)(Math.PI / n1), phi;
      for (int i = 0; i <= n1; i++)
      {  phi  =  i*dphi;
         x[i] = r*(float)Math.cos(phi);
         y[i] = r*(float)Math.sin(phi);
         nx[i] = x[i];
         ny[i] = y[i];
         x[i] += R;
      }
      zeichneRotFlaeche(gl,x,y,nx,ny,n2);
   }


   public void zeichneZylinder(GL3 gl, float r, float s, int n1, int n2)
   {
      float[] x = new float[n1];                     // Mantellinie in xy-Ebene
      float[] y = new float[n1];
      float[] nx = new float[n1];                    // Normalenvektoren
      float[] ny = new float[n1];
      float dy = s / (n1-1);
      for (int i = 0; i < n1; i++)
      {  x[i] = r;
         y[i] = i*dy;
         nx[i] = 1;
         ny[i] = 0;
      }
      zeichneRotFlaeche(gl,x,y,nx,ny,n2);

      //  ------  Grund-Kreis (y=0) -------
      int nPkte = n2;
      float[] xx = new float[nPkte+1];
      float[] zz = new float[nPkte+1];
      float phi = 2*(float)Math.PI/(nPkte-1);;
      for (int i=0; i<=nPkte; i++)
      {  zz[i] =  r*(float)Math.cos(i*phi);
         xx[i] =  r*(float)Math.sin(i*phi);
      }
      rd.rewindBuffer(gl);
      rd.setNormal(0,-1,0);
      rd.putVertex(0,0,0);
      for (int i=0; i<=nPkte; i++)
        rd.putVertex(xx[i],0,zz[i]);
      int nVertices = nPkte+2;
      rd.copyBuffer(gl, nVertices);
      gl.glDrawArrays(GL3.GL_TRIANGLE_FAN,0,nVertices);

      //  ------  Deck-Kreis  (y=s) -------
      rd.rewindBuffer(gl);
      rd.setNormal(0,1,0);
      rd.putVertex(0,s,0);
      for (int i=0; i<=nPkte; i++)
        rd.putVertex(xx[i],s,zz[i]);
      rd.copyBuffer(gl, nVertices);
      gl.glDrawArrays(GL3.GL_TRIANGLE_FAN,0,nVertices);
   }

}