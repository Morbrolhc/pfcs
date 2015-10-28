//  ----------  Vertex-Shader mit Beleuchtung (ambient+diffuse+specular)  ------------
//                                                      E. Gutknecht, Juli 2015
#version 130

//  --------  uniform Variabeln  -------
uniform mat4 viewMatrix, projMatrix;    // Transformationsmatrizen
uniform int ShadingLevel;               // 0=keine Beleuchtung, 1=Beleuchtung
uniform vec4 LightPosition;             // Koord. der Lichtquelle 

//  --------  Vertex-Attribute  --------
in  vec4 vertexPosition;                // Vertex-Attribute
in  vec4 vertexColor;   
in  vec4 vertexNormal;   

//  --------  Output Variabeln  --------
out vec4 Color;                         // Vertex-Farbe fuer Fragment-Shader


//  --------  globale Variabeln --------
float ambient = 0.2;                     // Streulicht
float diffuse = 0.4;                     // Reflexionskoeff. diffuse Reflexion
float specular = 0.4;                    // Reflexionskoeff. spiegelnde Reflexion
float specularExp = 20;                  // Exponent spiegelnde Reflexion (Shininess)


// -------  Hilfsmethoden  ----------------

float diffuseIntens(vec3 toLight, vec3 Normal,   // diffuse Reflexion 
                     float diffuse)                    
{  float intens = dot(Normal, toLight);           // Lambert-Gesetz 
   if ( intens < 0.0f ) 
     return 0.0f;
   else
     return diffuse * intens;
}      


float specularIntens(vec3 toLight, vec3 Normal, vec3 toEye,   // spiegelnde Reflexion               
                    float specular, float specularExp)
{  vec3 HalfVector = normalize(toLight + toEye);
   float intens = dot(Normal, HalfVector);                     // Phong-Approx.  
   if ( intens < 0.0f ) 
     return  0.0f;      
   else
     return specular * pow(intens,specularExp);
}      
    
    
//  ------------  main Methode  --------------------------    
void main()
{  vec4 vertex = viewMatrix * vertexPosition;                      // ModelView-Transformation 
   gl_Position = projMatrix * vertex;                              // Projektion
   Color = vertexColor;
   float Id, Is;                                                   
   //  --------  Beleuchtung  ---------
   if ( ShadingLevel == 1 )                                        // Beleuchtung   
   {  vec3 Normal = normalize((viewMatrix * vertexNormal).xyz);    // ModelView-Transf.
      vec3 toLight = normalize(LightPosition.xyz - vertex.xyz);
      vec3 toEye = normalize(-vertex.xyz);                         // Richtung zur Kamera (in O)
      Id = diffuseIntens(toLight, Normal, diffuse);                // diffus reflektiertes Licht
      if ( Id == 0.0 )
        Is = 0.0;
      else
        Is = specularIntens(toLight, Normal, toEye,                // spiegelnd reflektiertes Licht
                            specular, specularExp);
      vec3 whiteColor = vec3(1,1,1); 
      vec3 reflectedLight = (ambient + Id) * Color.rgb + Is * whiteColor; 
      Color.rgb = min(reflectedLight, whiteColor);
   }
}
