package com.github.abdullahbeg.engine3d.render;

import com.github.abdullahbeg.engine3d.math.Matrix;
import com.github.abdullahbeg.engine3d.mesh.Triangle;
import com.github.abdullahbeg.engine3d.mesh.Vertex;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Color;

public class Renderer {
    
    private final Matrix matrix;

    private static final float FOV_X = 120;
    private static final float FOV_Y = 90;
    private static final float NEAR = 0.1F;
    private static final float FAR = 1000;
    private final float WIDTH;
    private final float HEIGHT;

    public Renderer(int width, int height) {

        WIDTH = width;
        HEIGHT = height;

        float ASPECT = (float)height / width;

        matrix = new Matrix(FOV_X, FOV_Y, NEAR, FAR, ASPECT);

    }

    private void drawTriangle(Graphics2D g2, Triangle t) {

        Path2D path = new Path2D.Double();

        path.moveTo(t.getV1().getX(), t.getV1().getY());
        path.lineTo(t.getV2().getX(), t.getV2().getY());
        path.lineTo(t.getV3().getX(), t.getV3().getY());

        path.closePath();
        
        g2.setColor(Color.WHITE);
        g2.draw(path);

    }

    public void renderTriangles(float yaw, float pitch, float roll, float scale, ArrayList<Triangle> tris, Graphics2D g2) {

        for (Triangle t : tris) {

            Triangle transformed = t;

            transformed = TriangleTransform.rotateYaw(yaw, transformed);
            transformed = TriangleTransform.rotatePitch(pitch, transformed);
            transformed = TriangleTransform.rotateRoll(roll, transformed);
            transformed = TriangleTransform.applyProjectionMatrix(transformed, matrix);
            transformed = TriangleTransform.scale(scale, transformed);
            transformed = TriangleTransform.translate(new Vertex(WIDTH / 2, HEIGHT / 2, 0), transformed);

            drawTriangle(g2, transformed);

        }

    }

}
