package ch.fhnw.util;

import ch.fhnw.util.math.Vec3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by joel on 05.11.15.
 */
public class OBJReader {

    public OBJReader() {
    }

    public void readMesh(String _fileName, ArrayList<Float> _outVertexList, ArrayList<Float> _outNormList) {
        ArrayList<Vec3> vertexList = new ArrayList<>(4000);
        ArrayList<Vec3> normList = new ArrayList<>(4000);
        _outVertexList.clear();
        _outNormList.clear();

        try(InputStream is = OBJReader.class.getClassLoader().getResourceAsStream(_fileName)) {
            Scanner in = new Scanner(is);
            in.useDelimiter(" |\r?\n");
            String ch = "\0";
            // Wait for vertices
            while (!ch.equals("v")) {
                in.nextLine();
                ch = in.next();
            }
            // Read all vertices
            while (ch.equals("v")) {
                vertexList.add(new Vec3(in.nextFloat(), in.nextFloat(), in.nextFloat()));
                ch = in.next();
            }
            // Wait for normals
            while (!ch.equals("vn")) {
                in.nextLine();
                ch = in.next();
            }
            // Read all normals
            while (ch.equals("vn")) {
                normList.add(new Vec3(in.nextFloat(), in.nextFloat(), in.nextFloat()));
                ch = in.next();
            }
            // Wait for faces
            while (!ch.equals("f")) {
                in.nextLine();
                ch = in.next();
            }
            // Read all faces
            int v_i;
            int n_i;
            in.useDelimiter(" |\r?\n|/");
            while (ch.equals("f")) {
                for(int i = 0; i < 3; i++) {
                    v_i = in.nextInt();
                    in.next();
                    n_i = in.nextInt();
                    Vec3 v = vertexList.get(v_i-1);
                    Vec3 n = normList.get(n_i-1);
                    _outVertexList.add(v.x);
                    _outVertexList.add(v.y);
                    _outVertexList.add(v.z);
                    _outNormList.add(n.x);
                    _outNormList.add(n.y);
                    _outNormList.add(n.z);
                }
                if(in.hasNext()){
                    ch = in.next();
                } else {
                    ch = "\0";
                }
            }
        } catch (IOException e){ e.printStackTrace();}
    }

}
