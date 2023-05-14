package com.github.abdullahbeg.engine3d.objects;
import com.github.abdullahbeg.engine3d.mesh.Triangle;
import com.github.abdullahbeg.engine3d.mesh.Vertex;

import java.util.ArrayList;

public class Cube {

    public Cube(ArrayList<Triangle> tris) {

        addTriangles(tris);

    }

    public void addTriangles(ArrayList<Triangle> tris) {

        // SOUTH
        tris.add(new Triangle
                (
                        new Vertex(-1, -1, -1),
                        new Vertex(-1, 1, -1),
                        new Vertex(1, 1, -1)
                ));

        tris.add(new Triangle
                (
                        new Vertex(-1, -1, -1),
                        new Vertex(1, 1, -1),
                        new Vertex(1, -1, -1)
                ));



        // EAST
        tris.add(new Triangle
                (
                        new Vertex(1, -1, -1),
                        new Vertex(1, 1, -1),
                        new Vertex(1, 1, 1)
                ));

        tris.add(new Triangle
                (
                        new Vertex(1, -1, -1),
                        new Vertex(1, 1, 1),
                        new Vertex(1, -1, 1)
                ));



        // NORTH
        tris.add(new Triangle
                (
                        new Vertex(1, -1, 1),
                        new Vertex(1, 1, 1),
                        new Vertex(-1, 1, 1)
                ));

        tris.add(new Triangle
                (
                        new Vertex(1, -1, 1),
                        new Vertex(-1, 1, 1),
                        new Vertex(-1, -1, 1)
                ));



        // WEST
        tris.add(new Triangle
                (
                        new Vertex(-1, -1, 1),
                        new Vertex(-1, 1, 1),
                        new Vertex(-1, 1, -1)
                ));

        tris.add(new Triangle
                (
                        new Vertex(-1, -1, 1),
                        new Vertex(-1, 1, -1),
                        new Vertex(-1, -1, -1)
                ));



        // TOP
        tris.add(new Triangle
                (
                        new Vertex(-1, 1, -1),
                        new Vertex(-1, 1, 1),
                        new Vertex(1, 1, 1)
                ));

        tris.add(new Triangle
                (
                        new Vertex(-1, 1, -1),
                        new Vertex(1, 1, 1),
                        new Vertex(1, 1, -1)
                ));


        // BOTTOM
        tris.add(new Triangle
                (
                        new Vertex(-1, -1, 1),
                        new Vertex(-1, -1, -1),
                        new Vertex(1, -1, -1)
                ));

        tris.add(new Triangle
                (
                        new Vertex(-1, -1, 1),
                        new Vertex(1, -1, -1),
                        new Vertex(1, -1, 1)
                ));

    }
    
}
