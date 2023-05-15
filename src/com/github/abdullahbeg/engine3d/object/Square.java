package com.github.abdullahbeg.engine3d.object;

import java.util.ArrayList;

import com.github.abdullahbeg.engine3d.mesh.Triangle;
import com.github.abdullahbeg.engine3d.mesh.Vertex;
import com.github.abdullahbeg.engine3d.texture.Texture;

public class Square {

    public Square(ArrayList<Triangle> tris) {

        addTriangles(tris);

    }

    public void addTriangles(ArrayList<Triangle> tris) {

        Texture logo = new Texture();

        tris.add(new Triangle
                (
                        new Vertex(-1, -1, -1, 1,1),
                        new Vertex(-1, 1, -1, 1,0),
                        new Vertex(1, 1, -1, 0,0),
                        logo
                ));

        tris.add(new Triangle
                (
                        new Vertex(-1, -1, -1, 1,1),
                        new Vertex(1, 1, -1, 0,0),
                        new Vertex(1, -1, -1, 0,1),
                        logo
                ));

    }
    
}
