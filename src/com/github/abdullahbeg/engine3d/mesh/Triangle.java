package com.github.abdullahbeg.engine3d.mesh;


public class Triangle {
    
    private Vertex v1, v2, v3;

    public Triangle(Vertex v1, Vertex v2, Vertex v3) {

        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;

    }

    public Vertex getV1() { return v1; }
    public Vertex getV2() { return v2; }
    public Vertex getV3() { return v3; }

}
