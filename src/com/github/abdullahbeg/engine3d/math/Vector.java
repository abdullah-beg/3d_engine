package com.github.abdullahbeg.engine3d.math;

import com.github.abdullahbeg.engine3d.mesh.Vertex;

public class Vector {
    
    private Vector() {}

    public static Vertex add(Vertex v1, Vertex v2) {

        return new Vertex(
            v1.getX() + v2.getX(),
            v1.getY() + v2.getY(),
            v1.getZ() + v2.getZ(),
            v1.getW()
        );

    }

    public static Vertex sub(Vertex v1, Vertex v2) {

        return new Vertex(
            v1.getX() - v2.getX(),
            v1.getY() - v2.getY(),
            v1.getZ() - v2.getZ(),
            v1.getW()
        );
        
    }
    
    public static Vertex mult(Vertex v, float value) {
        
        return new Vertex(
            v.getX() * value,
            v.getY() * value,
            v.getZ() * value,
            v.getW()
        );
        
    }
    
    public static Vertex div(Vertex v, float value) {
        
        return new Vertex(
            v.getX() / value,
            v.getY() / value,
            v.getZ() / value,
            v.getW()
        );
        
    }

    public static Vertex crossProduct(Vertex v1, Vertex v2) {

        return new Vertex(
            v1.getY() * v2.getZ() - v1.getZ() * v2.getY(),
            v1.getZ() * v2.getX() - v1.getX() * v2.getZ(),
            v1.getX() * v2.getY() - v1.getY() * v2.getX(),
            v1.getW()
        );

    }

    private static Vertex multiplyMatrixVector(float[][] matrix, Vertex v) {

        float[] vector = new float[] {v.getX(), v.getY(), v.getZ()};
        float[] output = new float[3];

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {

                output[col] += matrix[col][row] * vector[row];

            }

        }

        return new Vertex(output[0], output[1], output[2], v.getW());

    }

    public static Vertex rotateYaw(float yaw, Vertex v){

        float[][] yawRotation = new float[][]
        {
                {(float)Math.cos(Math.toRadians(yaw)), -(float)Math.sin(Math.toRadians(yaw)), 0},
                {(float)Math.sin(Math.toRadians(yaw)), (float)Math.cos(Math.toRadians(yaw)), 0},
                {0, 0, 1}
        };

        return multiplyMatrixVector(yawRotation, v);

    }

    public static Vertex rotatePitch(float pitch, Vertex v){

        float[][] pitchRotation = new float[][]
        {
                {(float)Math.cos(Math.toRadians(pitch)), 0, -(float)Math.sin(Math.toRadians(pitch))},
                {0, 1, 0},
                {(float)Math.sin(Math.toRadians(pitch)), 0, (float)Math.cos(Math.toRadians(pitch))}
        };

        return multiplyMatrixVector(pitchRotation, v);

    }

    public static Vertex rotateRoll(float roll, Vertex v) {

        float[][] rollRotation = new float[][]
        {
                {1, 0, 0},
                {0, (float)Math.cos(Math.toRadians(roll)), (float)Math.sin(Math.toRadians(roll))},
                {0, -(float)Math.sin(Math.toRadians(roll)), (float)Math.cos(Math.toRadians(roll))}
        };

        return multiplyMatrixVector(rollRotation, v);

    }

}
