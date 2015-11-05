package ch.fhnw.util;

import java.util.Iterator;
import java.util.List;

/**
 * Created by joel on 05.11.15.
 */
public class FloatUtil {

    public static float[] ListToPrimitiveArray(List<Float> list) {
        float[] array = new float[list.size()];
        Iterator<Float> iter = list.iterator();
        for(int i = 0; i < list.size(); i++) {
            array[i] = iter.next().floatValue();
        }
        return array;
    }
}
