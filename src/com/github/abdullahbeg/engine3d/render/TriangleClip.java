package com.github.abdullahbeg.engine3d.render;

import java.util.ArrayList;

import com.github.abdullahbeg.engine3d.mesh.Triangle;
import com.github.abdullahbeg.engine3d.mesh.Vertex;

public class TriangleClip {
    
    private TriangleClip() {}

    public static ArrayList<Triangle> clipZ(Triangle t, float zBound) {

        ArrayList<Triangle> output = new ArrayList<>();

        Vertex[] vertices = t.getVertices();
        int outside = 0;

        for (Vertex v : vertices) {

            if (v.getZ() < zBound) { outside++; }

        }

        if (outside == 0) { 
            output.add(t); 

        } else if (outside == 1) { 

            for (int i = 0; i < 3; i++) { 

                // Find the point which is outside
                if (vertices[i].getZ() <= zBound) {

                    int i2 = (i + 1) % 3;
                    int i3 = (i + 2) % 3;
                    Vertex[] intersects = zIntersect(vertices[i], vertices[i2], vertices[i3], zBound);

                    output.add(new Triangle(intersects[1], intersects[0], vertices[i2], t.getColor()));
                    output.add(new Triangle(intersects[1], vertices[i2], vertices[i3], t.getColor()));
                    
                    return output;

                }

            }

        } else if (outside == 2) {

            for (int i = 0; i < 3; i++) {

                // Find the point which is inside
                if (vertices[i].getZ() > zBound) {

                    int i2 = (i + 1) % 3;
                    int i3 = (i + 2) % 3;
                    Vertex[] intersects = zIntersect(vertices[i], vertices[i2], vertices[i3], zBound);

                    output.add(new Triangle(vertices[i], intersects[0], intersects[1], t.getColor()));

                    return output;

                }

            }

        }

        // Else if it is outside, we aren't drawing it, do nothing.

        return output;

    }

    public static ArrayList<Triangle> clipAll(int UP, int DOWN, int LEFT, int RIGHT, ArrayList<Triangle> tris) {

        ArrayList<Triangle> output = new ArrayList<>();

        for (Triangle t : tris) {
            output = clipTop(UP, tris);
        }

        for (Triangle t : output) {
            output = clipBottom(DOWN, output);
        }

        for (Triangle t : output) {
            output = clipLeft(LEFT, output);
        }

        for (Triangle t : output) {
            output = clipRight(RIGHT, output);
        }

        return output;

    }

    private static ArrayList<Triangle> clipTop(int yBound, ArrayList<Triangle> tris) {

        ArrayList<Triangle> output = new ArrayList<>();

        for (Triangle t : tris) {

            Vertex[] vertices = t.getVertices();
            int outside = 0;
    
            for (Vertex v : vertices) {
    
                if (v.getY() < yBound) { outside++; }
    
            }
    
            if (outside == 0) {
                output.add(t);
    
            } else if (outside == 1) {
    
                for (int i = 0; i < 3; i++) {
    
                    if (vertices[i].getY() < yBound) {
    
                        int i2 = (i + 1) % 3;
                        int i3 = (i + 2) % 3;
                        Vertex[] intersects = yIntersect(vertices[i], vertices[i2], vertices[i3], yBound);
                    
                        output.add(new Triangle(intersects[1], intersects[0], vertices[i2], t.getColor()));
                        output.add(new Triangle(intersects[1], vertices[i2], vertices[i3], t.getColor()));

                        break;
    
                    }
    
                }
    
            } else if (outside == 2) {

                for (int i = 0; i < 3; i++) {

                    if (vertices[i].getY() >= yBound) {

                        int i2 = (i + 1) % 3;
                        int i3 = (i + 2) % 3;
                        Vertex[] intersects = yIntersect(vertices[i], vertices[i2], vertices[i3], yBound);

                        output.add(new Triangle(vertices[i], intersects[0], intersects[1], t.getColor()));
                        
                        break;

                    }

                }

            }

        }

        return output;

    }

    private static ArrayList<Triangle> clipBottom(int yBound, ArrayList<Triangle> tris) {

        ArrayList<Triangle> output = new ArrayList<>();

        for (Triangle t : tris) {

            Vertex[] vertices = t.getVertices();
            int outside = 0;
    
            for (Vertex v : vertices) {
    
                if (v.getY() > yBound) { outside++; }
    
            }
    
            if (outside == 0) {
                output.add(t);
    
            } else if (outside == 1) {
    
                for (int i = 0; i < 3; i++) {
    
                    if (vertices[i].getY() > yBound) {
    
                        int i2 = (i + 1) % 3;
                        int i3 = (i + 2) % 3;
                        Vertex[] intersects = yIntersect(vertices[i], vertices[i2], vertices[i3], yBound);
                    
                        output.add(new Triangle(intersects[1], intersects[0], vertices[i2], t.getColor()));
                        output.add(new Triangle(intersects[1], vertices[i2], vertices[i3], t.getColor()));

                        break;
    
                    }
    
                }
    
            } else if (outside == 2) {

                for (int i = 0; i < 3; i++) {

                    if (vertices[i].getY() <= yBound) {

                        int i2 = (i + 1) % 3;
                        int i3 = (i + 2) % 3;
                        Vertex[] intersects = yIntersect(vertices[i], vertices[i2], vertices[i3], yBound);

                        output.add(new Triangle(vertices[i], intersects[0], intersects[1], t.getColor()));
                        
                        break;

                    }

                }

            }

        }

        return output;

    }

    private static ArrayList<Triangle> clipLeft(int xBound, ArrayList<Triangle> tris) {

        ArrayList<Triangle> output = new ArrayList<>();

        for (Triangle t : tris) {

            Vertex[] vertices = t.getVertices();
            int outside = 0;

            for (Vertex v : vertices) {

                if (v.getX() < xBound) { outside++; }

            }

            if (outside == 0) {
                output.add(t);

            } else if (outside == 1) {

                for (int i = 0; i < 3; i++) {

                    if (vertices[i].getX() < xBound) {

                        int i2 = (i + 1) % 3;
                        int i3 = (i + 2) % 3;
                        Vertex[] intersects = xIntersect(vertices[i], vertices[i2], vertices[i3], xBound);
                    
                        output.add(new Triangle(intersects[1], intersects[0], vertices[i2], t.getColor()));
                        output.add(new Triangle(intersects[1], vertices[i2], vertices[i3], t.getColor()));
                        
                        break;

                    }

                }

            } else if (outside == 2) {

                for (int i = 0; i < 3; i++) {

                    if (vertices[i].getX() >= xBound) {

                        int i2 = (i + 1) % 3;
                        int i3 = (i + 2) % 3;
                        Vertex[] intersects = xIntersect(vertices[i], vertices[i2], vertices[i3], xBound);

                        output.add(new Triangle(vertices[i], intersects[0], intersects[1], t.getColor()));
                        
                        break;

                    }
                
                }

            }

        }

        return output;

    }

    private static ArrayList<Triangle> clipRight(int xBound, ArrayList<Triangle> tris) {

        ArrayList<Triangle> output = new ArrayList<>();

        for (Triangle t : tris) {

            Vertex[] vertices = t.getVertices();
            int outside = 0;

            for (Vertex v : vertices) {

                if (v.getX() > xBound) { outside++; }

            }

            if (outside == 0) {
                output.add(t);

            } else if (outside == 1) {

                for (int i = 0; i < 3; i++) {

                    if (vertices[i].getX() > xBound) {

                        int i2 = (i + 1) % 3;
                        int i3 = (i + 2) % 3;
                        Vertex[] intersects = xIntersect(vertices[i], vertices[i2], vertices[i3], xBound);
                    
                        output.add(new Triangle(intersects[1], intersects[0], vertices[i2], t.getColor()));
                        output.add(new Triangle(intersects[1], vertices[i2], vertices[i3], t.getColor()));
                        
                        break;

                    }

                }

            } else if (outside == 2) {

                for (int i = 0; i < 3; i++) {

                    if (vertices[i].getX() <= xBound) {

                        int i2 = (i + 1) % 3;
                        int i3 = (i + 2) % 3;
                        Vertex[] intersects = xIntersect(vertices[i], vertices[i2], vertices[i3], xBound);

                        output.add(new Triangle(vertices[i], intersects[0], intersects[1], t.getColor()));
                        
                        break;

                    }
                
                }

            }

        }

        return output;

    }

    private static Vertex[] xIntersect(Vertex v1, Vertex v2, Vertex v3, int xIntersect) {

        float dx = v1.getX() - v2.getX();
        float dy = v1.getY() - v2.getY();
        float dz = v1.getZ() - v2.getZ();

        float deltaX = (xIntersect - v2.getX());

        Vertex intersect1 = new Vertex(
            xIntersect,
            (dy/dx) * deltaX + v2.getY(),
            (dz/dx) * deltaX + v2.getZ()
        );

        dx = v1.getX() - v3.getX();
        dy = v1.getY() - v3.getY();
        dz = v1.getZ() - v3.getZ();

        deltaX = (xIntersect - v3.getX());

        Vertex intersect2 = new Vertex(
            xIntersect,
            (dy/dx) * deltaX + v3.getY(),
            (dz/dx) * deltaX + v3.getZ()
        );

        return new Vertex[] {intersect1, intersect2}; 

    }

    private static Vertex[] yIntersect(Vertex v1, Vertex v2, Vertex v3, int yIntersect) {

        float dx = v1.getX() - v2.getX();
        float dy = v1.getY() - v2.getY();
        float dz = v1.getZ() - v2.getZ();

        float deltaX = (yIntersect - v2.getY());

        Vertex intersect1 = new Vertex(
            (dx/dy) * deltaX + v2.getX(),
            yIntersect,
            (dz/dy) * deltaX + v2.getZ()
        );

        dx = v1.getX() - v3.getX();
        dy = v1.getY() - v3.getY();
        dz = v1.getZ() - v3.getZ();

        deltaX = (yIntersect - v3.getY());

        Vertex intersect2 = new Vertex(
            (dx/dy) * deltaX + v3.getX(),
            yIntersect,
            (dz/dx) * deltaX + v3.getZ()
        );

        return new Vertex[] {intersect1, intersect2}; 

    }

    private static Vertex[] zIntersect(Vertex v1, Vertex v2, Vertex v3, float zIntersect) {

        float dx = v1.getX() - v2.getX();
        float dy = v1.getY() - v2.getY();
        float dz = v1.getZ() - v2.getZ();
        float dw = v1.getW() - v2.getW();

        float deltaZ = (zIntersect - v2.getZ());

        Vertex intersect1 = new Vertex(
            (dx/dz) * deltaZ + v2.getX(),
            (dy/dz) * deltaZ + v2.getY(),
            zIntersect,
            (dw/dz) * deltaZ + v2.getW()
        );

        dx = v1.getX() - v3.getX();
        dy = v1.getY() - v3.getY();
        dz = v1.getZ() - v3.getZ();
        dw = v1.getW() - v3.getW();

        deltaZ = (zIntersect - v3.getZ());

        Vertex intersect2 = new Vertex(
            (dx/dz) * (deltaZ) + v3.getX(),
            (dy/dz) * (deltaZ) + v3.getY(),
            zIntersect,
            (dw/dz) * deltaZ + v3.getW()
        );

        return new Vertex[] {intersect1, intersect2};

    }

}
