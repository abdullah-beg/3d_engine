package com.github.abdullahbeg.engine3d.mesh;

public class Vertex {
    
    private float x, y, z, w;
    
    public Vertex(float x, float y, float z) {

        this(x, y, z, 1);
        
    }
    
    public Vertex(float x, float y, float z, float w) {
        
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;

    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    public float getW() { return w; }

}