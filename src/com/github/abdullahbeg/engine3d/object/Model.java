package com.github.abdullahbeg.engine3d.object;

import java.util.ArrayList;

import com.github.abdullahbeg.engine3d.mesh.Triangle;
import com.github.abdullahbeg.engine3d.texture.Texture;

public class Model {

    private ArrayList<Triangle> triangles;

    public Model(String objFileName, String textureFileName, ArrayList<Triangle> tris) {

        triangles = OBJLoader.loadObject(objFileName, new Texture(textureFileName));
        tris.addAll(triangles);

    }
    
}
