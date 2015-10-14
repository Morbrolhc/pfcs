//  ------  Vertex-Shader   ----------


#version 130
 
uniform mat4 viewMatrix, projMatrix;    // Transformationsmatrizen

in vec4 vertexPosition, vertexColor;    // Vertex-Attributes
out vec4 Color;                         // Vertex-Farbe fuer Fragment-Shader

    
void main()
{  vec4 vertex = viewMatrix * vertexPosition;         // VertexPos in CameraSystem  
   gl_Position = projMatrix * vertex;                 // Projection
   Color = vertexColor;
}
