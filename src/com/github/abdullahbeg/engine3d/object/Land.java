package com.github.abdullahbeg.engine3d.object;

import java.util.ArrayList;
import java.util.Random;

import com.github.abdullahbeg.engine3d.mesh.Triangle;
import com.github.abdullahbeg.engine3d.mesh.Vertex;
import com.github.abdullahbeg.engine3d.texture.Texture;

public class Land {

    public Land(ArrayList<Triangle> tris, int n, int size) {

        addTriangles(tris, n, size);

    }

    public void addTriangles(ArrayList<Triangle> tris, int n, int size) {

        Random random = new Random();

        Texture t = new Texture();

        int numVertices = n + 1;
        Vertex[][] vertices = new Vertex[numVertices][numVertices];

        for (int row = 0; row < numVertices; row++) {
            for (int col = 0; col < numVertices; col++) {
                float randomY = -2 + random.nextFloat() * 3f;
                vertices[row][col] = new Vertex(
                                        (row - (n/2)) * size, 
                                        randomY, 
                                        (col - (n/2)) * size
                );
            }
        }

        for (int i = 0; i < n*n; i++) {
            int row = i / n;
            int col = i % n;

            Vertex a = vertices[row][col];
            Vertex b = vertices[row][col + 1];
            Vertex c = vertices[row + 1][col];
            Vertex d = vertices[row + 1][col + 1];

            tris.add(new Triangle(a, b, c, t));
            tris.add(new Triangle(b, d, c, t));
    
        }

    }
    
}
