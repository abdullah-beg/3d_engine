package com.github.abdullahbeg.engine3d.render;

import com.github.abdullahbeg.engine3d.math.Matrix;
import com.github.abdullahbeg.engine3d.math.Vector;
import com.github.abdullahbeg.engine3d.math.Camera;
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

    private static final Vertex direction = new Vertex(0, 0, -1);

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
        
        g2.setColor(t.getColor());
        g2.fill(path);

    }

    public void renderTriangles(float yaw, float pitch, float roll, float scale, Camera camera, ArrayList<Triangle> tris, Graphics2D g2) {

        ArrayList<Triangle> draw = new ArrayList<>();
        transformTriangles(tris, draw, yaw, pitch, roll, scale, camera);

        for (Triangle t : draw) {

            drawTriangle(g2, t);

        }

    }

    public void transformTriangles(ArrayList<Triangle> input, ArrayList<Triangle> output, float yaw, float pitch, float roll, float scale, Camera camera) {

        for (Triangle t : input) {

            Triangle transformed = t;

            transformed = TriangleTransform.translate(camera.getCameraOffset(), transformed);
            transformed = TriangleTransform.rotateYaw(yaw, transformed);
            transformed = TriangleTransform.rotatePitch(pitch, transformed);
            transformed = TriangleTransform.rotateRoll(roll, transformed);
            transformed = TriangleTransform.applyProjectionMatrix(transformed, matrix);
            
            Vertex line1 = Vector.sub(transformed.getV2(), transformed.getV1());
            Vertex line2 = Vector.sub(transformed.getV3(), transformed.getV1());
            Vertex normal = Vector.normalise(Vector.crossProduct(line1, line2));
            
            if (Vector.scalarProduct(normal, direction) > 0) {
                
                transformed = TriangleTransform.scale(scale, transformed);
                transformed = TriangleTransform.translate(new Vertex(WIDTH / 2, HEIGHT / 2, 0), transformed);

                double light = Vector.scalarProduct(normal, direction);
                light = Math.pow(Math.abs(light), 2.0);

                int r = (int)(transformed.getColor().getRed() * light);
                int g = (int)(transformed.getColor().getGreen() * light);
                int b = (int)(transformed.getColor().getBlue() * light);

                Color c = new Color(r,g,b);

                output.add(
                    new Triangle(
                        transformed.getV1(),
                        transformed.getV2(),
                        transformed.getV3(),
                        c
                    )

                );
            
            }

        }
        
    }

}
