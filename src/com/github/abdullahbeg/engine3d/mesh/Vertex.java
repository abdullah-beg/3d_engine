package com.github.abdullahbeg.engine3d.mesh;

public class Vertex {
    
    private float x, y, z, w;
    private float u, v;

    public Vertex(float x, float y, float z) {

        this(x, y, z, 1, 0, 0);
        
    }

    public Vertex(float x, float y, float z, float u, float v) {

        this(x, y, z, 1, u, v);

    }
    
    public Vertex(float x, float y, float z, float w, float u, float v) {
        
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;

        this.u = u;
        this.v = v;

    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    public float getW() { return w; }

    public float getU() { return u; }
    public float getV() { return v; }

}