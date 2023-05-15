package com.github.abdullahbeg.engine3d.math;

import com.github.abdullahbeg.engine3d.mesh.Vertex;

public class Matrix {

    private final float NEAR;
    private final float FAR;
    private final float ASPECT;

    private final float RANGE_X;
    private final float RANGE_Y;

    private float[][] projectionMatrix;

    public Matrix(float FOV_X, float FOV_Y, float NEAR, float FAR, float ASPECT) {

        this.NEAR = NEAR;
        this.FAR = FAR;

        this.ASPECT = ASPECT;

        RANGE_X = (float)Math.tan(Math.toRadians(FOV_X / 2) * NEAR);
        RANGE_Y = (float)Math.tan(Math.toRadians(FOV_Y / 2) * NEAR);

        setProjectionMatrix();

    }

    private void setProjectionMatrix() {

        float sx = ((2 * NEAR) / (RANGE_X * ASPECT + RANGE_X * ASPECT));
        float sy = (NEAR / RANGE_Y);
        float sz = ((-(FAR + NEAR)) / (FAR - NEAR));
        float pz = ((-(2 * NEAR * FAR)) / (FAR - NEAR));


        projectionMatrix = new float[][]
        {
                {sx, 0, 0, 0},
                {0, sy, 0, 0},
                {0, 0, sz, pz},
                {0, 0, -1, 0}
        };

    }

    private Vertex multiplyMatrixVector4x4(float[][] matrix, Vertex v) {

        float[] vectorMatrix = new float[] {v.getX(), v.getY(), v.getZ(), v.getW()};
        float[] output = new float[4];

        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {

                output[col] += matrix[col][row] * vectorMatrix[row];

            }

        }

        return new Vertex(output[0], output[1], output[2], output[3], v.getU(), v.getV());

    }

    public Vertex applyProjectionMatrix(Vertex v) {

        Vertex transformed = multiplyMatrixVector4x4(projectionMatrix, v);

        if (transformed.getW() != 0) {
            float w = transformed.getW();

            return new Vertex(transformed.getX() / w, transformed.getY() / w, transformed.getZ() / w, 1 / w, v.getU(), v.getV());

        }

        return new Vertex(transformed.getX(), transformed.getY(), transformed.getZ(), 1, v.getU(), v.getV());

    }

}
