package ch.fhnw.util;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by joel on 05.11.15.
 */
public class OBJReader {

    public OBJReader() {
    }

    public Mesh readMesh(String fileName) {
        ArrayList<Float> list = new ArrayList<>(1000);
        String name;
        try(Scanner in = new Scanner(new File(getClass().getClassLoader().getResource(fileName).getFile()))) {
            in.useDelimiter(" ");
            String ch = "\0";
            while (!ch.equals("v")) {
                in.nextLine();
                ch = in.next();
            }
            System.out.println(in.nextLine());
            list.add(in.nextFloat());
            list.add(in.nextFloat());
            list.add(in.nextFloat());
            ch = in.next();
            System.out.println(in.next());
        } catch (IOException e){ e.printStackTrace();}
        return null;
    }

}
