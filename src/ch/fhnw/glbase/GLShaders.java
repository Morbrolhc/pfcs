package ch.fhnw.glbase; /**
* This is a port of some sample code from:
* http://www.lighthouse3d.com/cg-topics/code-samples/opengl-3-3-glsl-1-5-sample/
*/
import java.io.*;
import javax.media.opengl.*;
public class GLShaders
{

    static int loadShaders(GL3 gl,                             // Compile and Link Vertex- and Fragmentshader
                           String vertexShaderFileName,
                           String fragmentShaderFileName)
    {  int v = gl.glCreateShader(GL3.GL_VERTEX_SHADER);
       int f = gl.glCreateShader(GL3.GL_FRAGMENT_SHADER);
       String vs = textFileRead(vertexShaderFileName);
       String fs = textFileRead(fragmentShaderFileName);
       gl.glShaderSource(v, 1, new String[] { vs }, null);
       gl.glShaderSource(f, 1, new String[] { fs }, null);
       gl.glCompileShader(v);
       gl.glCompileShader(f);
       System.out.println(printShaderInfoLog(gl, v));
       System.out.println(printShaderInfoLog(gl, f));
       int p = gl.glCreateProgram();
       gl.glAttachShader(p, v);
       gl.glAttachShader(p, f);
       gl.glLinkProgram(p);
       gl.glUseProgram(p);
       System.out.println("ProgramInfoLog: " + printProgramInfoLog(gl, p));
       return (p);
    }


    /** Retrieves the info log for the shader */
    static public String printShaderInfoLog(GL3 gl, int obj)
    {  // Otherwise, we'll get the GL info log
       final int logLen = getShaderParameter(gl, obj, GL3.GL_INFO_LOG_LENGTH);
       if (logLen <= 0)
         return "";
       // Get the log
       final int[] retLength = new int[1];
       final byte[] bytes = new byte[logLen + 1];
       gl.glGetShaderInfoLog(obj, logLen, retLength, 0, bytes, 0);
       final String logMessage = new String(bytes);
       return String.format("ShaderLog: %s", logMessage);
    }


    /** Get a shader parameter value. See 'glGetShaderiv' */
    static private int getShaderParameter(GL3 gl, int obj, int paramName)
    {  final int params[] = new int[1];
       gl.glGetShaderiv(obj, paramName, params, 0);
       return params[0];
    }


    /** Retrieves the info log for the program */
    static public String printProgramInfoLog(GL3 gl, int obj)
    {  // get the GL info log
       final int logLen = getProgramParameter(gl, obj, GL3.GL_INFO_LOG_LENGTH);
       if (logLen <= 0)
         return "";
       // Get the log
       final int[] retLength = new int[1];
       final byte[] bytes = new byte[logLen + 1];
       gl.glGetProgramInfoLog(obj, logLen, retLength, 0, bytes, 0);
       final String logMessage = new String(bytes);
       return logMessage;
    }


    /** Gets a program parameter value */
    static public int getProgramParameter(GL3 gl, int obj, int paramName)
    {  final int params[] = new int[1];
       gl.glGetProgramiv(obj, paramName, params, 0);
       return params[0];
    }


    static public String textFileRead(String filePath)
    {  // Read the data in
       try (BufferedReader reader = new BufferedReader(
               new InputStreamReader(GLShaders.class.getClassLoader().getResourceAsStream(filePath)))) {
          StringBuilder sb = new StringBuilder();
          String line;
          while ((line = reader.readLine()) != null)
            sb.append(line).append("\n");
          return sb.toString();
       }
       catch (final Exception ex)
       {  ex.printStackTrace();
       }
      return "";
    }

}