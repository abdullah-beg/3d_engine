package com.github.abdullahbeg.engine3d.mesh;

import com.github.abdullahbeg.engine3d.texture.Texture;

public class Triangle {
    
    private Vertex v1, v2, v3;
    
    private Texture texture;

    private float light;

    public Triangle(Vertex v1, Vertex v2, Vertex v3, Texture texture, float light) {

        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;

        this.texture = texture;

        this.light = light;

    }

    public Triangle(Vertex v1, Vertex v2, Vertex v3, Texture texture) {

        this(v1, v2, v3, texture, 0);

    }

    public Vertex getV1() { return v1; }
    public Vertex getV2() { return v2; }
    public Vertex getV3() { return v3; }

    public Texture getTexture() { return texture; }

    public float getLight() { return light; }

    public Vertex[] getVertices() { return new Vertex[] {v1, v2, v3}; }

}
