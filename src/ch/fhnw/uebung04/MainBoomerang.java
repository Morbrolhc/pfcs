package ch.fhnw.uebung04;

import ch.fhnw.util.OBJReader;

/**
 * Created by joel on 05.11.15.
 */
public class MainBoomerang {

    public MainBoomerang() {
        OBJReader reader = new OBJReader();
        reader.readMesh("boomerang.obj");
    }


    public static void main(String[] args) {
        new MainBoomerang();
    }
}
