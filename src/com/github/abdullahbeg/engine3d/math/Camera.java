package com.github.abdullahbeg.engine3d.math;

import com.github.abdullahbeg.engine3d.mesh.Vertex;

public class Camera {
    
    private Vertex cameraOffset;
    
    public Camera() {
        
        cameraOffset = new Vertex(0, 0, -1);
        
    }
    
    public Vertex getCameraOffset() { return cameraOffset; }
    public void setCameraOffset(Vertex cameraOffset) { this.cameraOffset = cameraOffset; }

    private float calculateDisplacementX(float pitch, float VELOCITY) {

        return VELOCITY * (float)Math.sin(Math.toRadians(pitch));

    }

    private float calculateDisplacementY(float roll, float VELOCITY) {

        return VELOCITY * (float)Math.tan(Math.toRadians(roll));

    }

    private float calculateDisplacementZ(float pitch, float VELOCITY) {

        return VELOCITY * (float)Math.cos(Math.toRadians(pitch));

    }

    public Vertex calculateCameraOffset(float pitch, float roll, float VELOCITY) {

        return new Vertex(
            calculateDisplacementX(pitch, VELOCITY),
            calculateDisplacementY(roll, VELOCITY),
            calculateDisplacementZ(pitch, VELOCITY)
        );

    }
    
}
