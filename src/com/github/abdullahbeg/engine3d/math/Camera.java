package com.github.abdullahbeg.engine3d.math;

import com.github.abdullahbeg.engine3d.mesh.Vertex;

public class Camera {
    
    private Vertex cameraOffset;

    private float yaw = 0;
    private float pitch = 0;
    private float roll = 0;
    
    private float scale = 250;

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

    public float getScale() { return scale; }
    public float getYaw() { return yaw; }
    public float getPitch() { return pitch; }
    public float getRoll() { return roll; }

    public void setScale(float scale) { this.scale = scale; }
    public void setYaw(float yaw) { this.yaw = yaw; }
    public void setPitch(float pitch) { this.pitch = pitch; }

    public void setRoll(float roll) { 
        
        if (roll < -45) { this.roll = -45; return; }
        else if (roll > 45) { this.roll = 45; return; }
        else { this.roll = roll; return; }

    }

}
