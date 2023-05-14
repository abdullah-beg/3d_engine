package com.github.abdullahbeg.engine3d.objects;
import com.github.abdullahbeg.engine3d.mesh.Triangle;
import com.github.abdullahbeg.engine3d.mesh.Vertex;

import java.util.ArrayList;
import java.awt.Color;

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
                        new Vertex(1, 1, -1),
                        Color.RED
                ));

        tris.add(new Triangle
                (
                        new Vertex(-1, -1, -1),
                        new Vertex(1, 1, -1),
                        new Vertex(1, -1, -1),
                        Color.RED
                ));



        // EAST
        tris.add(new Triangle
                (
                        new Vertex(1, -1, -1),
                        new Vertex(1, 1, -1),
                        new Vertex(1, 1, 1),
                        Color.GREEN
                ));

        tris.add(new Triangle
                (
                        new Vertex(1, -1, -1),
                        new Vertex(1, 1, 1),
                        new Vertex(1, -1, 1),
                        Color.GREEN
                ));



        // NORTH
        tris.add(new Triangle
                (
                        new Vertex(1, -1, 1),
                        new Vertex(1, 1, 1),
                        new Vertex(-1, 1, 1),
                        Color.BLUE
                ));

        tris.add(new Triangle
                (
                        new Vertex(1, -1, 1),
                        new Vertex(-1, 1, 1),
                        new Vertex(-1, -1, 1),
                        Color.BLUE
                ));



        // WEST
        tris.add(new Triangle
                (
                        new Vertex(-1, -1, 1),
                        new Vertex(-1, 1, 1),
                        new Vertex(-1, 1, -1),
                        Color.WHITE
                ));

        tris.add(new Triangle
                (
                        new Vertex(-1, -1, 1),
                        new Vertex(-1, 1, -1),
                        new Vertex(-1, -1, -1),
                        Color.WHITE
                ));



        // TOP
        tris.add(new Triangle
                (
                        new Vertex(-1, 1, -1),
                        new Vertex(-1, 1, 1),
                        new Vertex(1, 1, 1),
                        Color.MAGENTA
                ));

        tris.add(new Triangle
                (
                        new Vertex(-1, 1, -1),
                        new Vertex(1, 1, 1),
                        new Vertex(1, 1, -1),
                        Color.MAGENTA
                ));


        // BOTTOM
        tris.add(new Triangle
                (
                        new Vertex(-1, -1, 1),
                        new Vertex(-1, -1, -1),
                        new Vertex(1, -1, -1),
                        Color.CYAN
                ));

        tris.add(new Triangle
                (
                        new Vertex(-1, -1, 1),
                        new Vertex(1, -1, -1),
                        new Vertex(1, -1, 1),
                        Color.CYAN
                ));

    }
    
}
