package com.github.abdullahbeg.engine3d.render;

import com.github.abdullahbeg.engine3d.math.Matrix;
import com.github.abdullahbeg.engine3d.math.Vector;
import com.github.abdullahbeg.engine3d.math.Camera;
import com.github.abdullahbeg.engine3d.mesh.Triangle;
import com.github.abdullahbeg.engine3d.mesh.Vertex;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class Renderer {
    
    private final Matrix matrix;

    private static final float FOV_X = 120;
    private static final float FOV_Y = 90;
    private static final float NEAR = 0.1F;
    private static final float FAR = 1000;
    private final int WIDTH;
    private final int HEIGHT;

    // Boundaries
    private final int UP, DOWN, LEFT, RIGHT;
    private static final float Z_BOUND = 0.00001F;

    private static final Vertex direction = new Vertex(0, 0, -1);
    private final Vertex OFFSET;

    private float roll = 0, pitch = 0, yaw = 0;

    public Renderer(int width, int height) {

        WIDTH = width;
        HEIGHT = height;

        UP = 0; 
        DOWN = HEIGHT - 1; 
        LEFT = 0; 
        RIGHT = WIDTH - 1;

        OFFSET = new Vertex(WIDTH / 2, HEIGHT / 2, 0);

        float ASPECT = (float)height / width;

        matrix = new Matrix(FOV_X, FOV_Y, NEAR, FAR, ASPECT);

    }

    public void renderTriangles(Camera camera, ArrayList<Triangle> tris, ArrayList<Vertex> lights, BufferedImage window) {

        // Light rotations
        ArrayList<Vertex> rotatedLights = new ArrayList<>();
        roll += 0.5;
        
        for (Vertex v : lights) {

            rotatedLights.add(Vector.rotateRoll(roll, v));

        }


        // Triangle Rotations
        ArrayList<Triangle> rotated = new ArrayList<>();
        pitch++; // Uncomment this to rotate object around Y axis

        Triangle rot;
        for (Triangle t : tris) {

            rot = t;
            // rot = TriangleTransform.rotateYaw(yaw, rot);
            // rot = TriangleTransform.rotatePitch(pitch, rot); // Uncomment to introduce rotating objects
            // rot = TriangleTransform.rotateRoll(roll, rot);

            rotated.add(rot);

        }

        ArrayList<Triangle> transformed = new ArrayList<>();
        
        transformTriangles(rotated, transformed, camera, rotatedLights);

        ArrayList<Triangle> draw = TriangleClip.clipAll(UP, DOWN, LEFT, RIGHT, transformed);

        float[] depthBuffer = new float[HEIGHT * WIDTH];
        Arrays.fill(depthBuffer, Float.NEGATIVE_INFINITY);

        for (Triangle t : draw) {

            rasterizeTriangle(window, t, depthBuffer);

        }

    }

    public void transformTriangles(ArrayList<Triangle> input, ArrayList<Triangle> output, Camera camera, ArrayList<Vertex> lights) {
        
        Triangle transformed;

        for (Triangle t : input) {

            transformed = t;
            
            transformed = TriangleTransform.translate(camera.getCameraOffset(), transformed);
            transformed = TriangleTransform.rotateYaw(camera.getYaw(), transformed);
            transformed = TriangleTransform.rotatePitch(camera.getPitch(), transformed);
            transformed = TriangleTransform.rotateRoll(camera.getRoll(), transformed);

            ArrayList<Triangle> zClipped = TriangleClip.clipZ(transformed, Z_BOUND);

            for (Triangle zt : zClipped) {

                transformed = TriangleTransform.applyProjectionMatrix(zt, matrix);
                
                Vertex line1 = Vector.sub(transformed.getV2(), transformed.getV1());
                Vertex line2 = Vector.sub(transformed.getV3(), transformed.getV1());
                Vertex normal = Vector.normalise(Vector.crossProduct(line1, line2));

                if (Vector.scalarProduct(normal, direction) > 0) {
                    
                    transformed = TriangleTransform.scale(camera.getScale(), transformed);
                    transformed = TriangleTransform.translate(OFFSET, transformed);
                    
                    float x = (t.getV1().getX() + t.getV2().getX() + t.getV3().getX()) / 3;
                    float y = (t.getV1().getY() + t.getV2().getY() + t.getV3().getY()) / 3;
                    float z = (t.getV1().getZ() + t.getV2().getZ() + t.getV3().getZ()) / 3;

                    Vertex l1 = Vector.sub(t.getV2(), t.getV1());
                    Vertex l2 = Vector.sub(t.getV3(), t.getV1());
                    Vertex n = Vector.normalise(Vector.crossProduct(l1, l2));

                    float light = 0;
                    for (Vertex v : lights) {

                        Vertex l = v;
                        
                        l = Vector.sub(l, new Vertex(x,y,z));
                        l = Vector.normalise(l);
                        
                        light += Vector.scalarProduct(l, n);

                    }

                    // light = (float)Math.pow(light, 1/2.4);
                    light = Math.min(1, light);
                    light = Math.max(0.17F, light);

                    output.add(
                        new Triangle(
                            transformed.getV1(),
                            transformed.getV2(),
                            transformed.getV3(),
                            transformed.getTexture(),
                            light
                        )
    
                    );
                
                }

            }
            
        }
        
    }

    private void rasterizeTriangle(BufferedImage image, Triangle t, float[] depthBuffer) {

        Vertex[] vertices = t.getVertices();

        if (vertices[0].getY() > vertices[1].getY()) {
            Vertex holder = vertices[0];
            vertices[0] = vertices[1];
            vertices[1] = holder;

        }

        if (vertices[0].getY() > vertices[2].getY()) {
            Vertex holder = vertices[0];
            vertices[0] = vertices[2];
            vertices[2] = holder;

        }

        if (vertices[1].getY() > vertices[2].getY()) {
            Vertex holder = vertices[1];
            vertices[1] = vertices[2];
            vertices[2] = holder;

        }

        int p0x = (int)vertices[0].getX();
        int p0y = (int)vertices[0].getY();
        int p1x = (int)vertices[1].getX();
        int p1y = (int)vertices[1].getY();
        int p2x = (int)vertices[2].getX();
        int p2y = (int)vertices[2].getY();

        if (p0y < p1y) {

            double slope1 = ((double)p1x - p0x) / (p1y - p0y);
            double slope2 = ((double)p2x - p0x) / (p2y - p0y);

            for (int i = 0; i <= p1y - p0y; i++) {
                int x1 = (int)(p0x + i * slope1);
                int x2 = (int)(p0x + i * slope2);
                int y = p0y + i;

                double uStart = vertices[0].getU() + ((double)y - p0y) / (p1y - p0y) * (vertices[1].getU() - vertices[0].getU());
                double vStart = vertices[0].getV() + ((double)y - p0y) / (p1y - p0y) * (vertices[1].getV() - vertices[0].getV());
                double wStart = vertices[0].getW() + ((double)y - p0y) / (p1y - p0y) * (vertices[1].getW() - vertices[0].getW());
                double zStart = vertices[0].getZ() + ((double)y - p0y) / (p1y - p0y) * (vertices[1].getZ() - vertices[0].getZ());

                double uEnd = vertices[0].getU() + ((double)y - p0y) / (p2y - p0y) * (vertices[2].getU() - vertices[0].getU());
                double vEnd = vertices[0].getV() + ((double)y - p0y) / (p2y - p0y) * (vertices[2].getV() - vertices[0].getV());
                double wEnd = vertices[0].getW() + ((double)y - p0y) / (p2y - p0y) * (vertices[2].getW() - vertices[0].getW());
                double zEnd = vertices[0].getZ() + ((double)y - p0y) / (p2y - p0y) * (vertices[2].getZ() - vertices[0].getZ());

                if (x1 > x2) {
                    int holder = x1;
                    x1 = x2;
                    x2 = holder;

                    double holder2;

                    holder2 = uStart;
                    uStart = uEnd;
                    uEnd = holder2;

                    holder2 = vStart;
                    vStart = vEnd;
                    vEnd = holder2;

                    holder2 = wStart;
                    wStart = wEnd;
                    wEnd = holder2;

                    holder2 = zStart;
                    zStart = zEnd;
                    zEnd = holder2;

                }

                if (x2 > x1) {
                    double u = uStart * t.getTexture().getTextureWidth();
                    double v = vStart * t.getTexture().getTextureHeight();
                    double w = wStart;
                    double z = zStart;

                    double ustep = (uEnd - uStart) / (x2 - x1) * t.getTexture().getTextureWidth();
                    double vstep = (vEnd - vStart) / (x2 - x1) * t.getTexture().getTextureHeight();
                    double wstep = (wEnd - wStart) / (x2 - x1);
                    double zstep = (zEnd - zStart) / (x2 - x1);

                    for (int x = x1; x <= x2; x++) {
                        u += ustep;
                        v += vstep;
                        w += wstep;
                        z += zstep;

                        if (z > depthBuffer[y * WIDTH + x]) {
                            
                            int color = t.getTexture().getTexturePixel(u / w, v / w);
                            float light = t.getLight();

                            int red = (int)(((color >> 16) & 0xFF) * light);
                            int green = (int)(((color >> 8) & 0xFF) * light);
                            int blue = (int)((color & 0xFF) * light);

                            int rgb = (red << 16) | (green << 8) | blue;

                            image.setRGB(x, y, rgb);
                            depthBuffer[y * WIDTH + x] = (float)z;

                        }

                    }

                }

            }

        }

        if (p1y < p2y) {
            
            double slope1 = ((double)p2x - p1x) / (p2y - p1y);
            double slope2 = ((double)p2x - p0x) / (p2y - p0y);
            double sx = p2x - (p2y - p1y) * slope2;
    
            for (int i = 0; i <= p2y - p1y; i++) {
                int x1 = (int)(p1x + i * slope1);
                int x2 = (int)(sx + i * slope2);
                int y = p1y + i;
    
                double uStart = vertices[1].getU() + ((double)y - p1y) / (p2y - p1y) * (vertices[2].getU() - vertices[1].getU());
                double vStart = vertices[1].getV() + ((double)y - p1y) / (p2y - p1y) * (vertices[2].getV() - vertices[1].getV());
                double wStart = vertices[1].getW() + ((double)y - p1y) / (p2y - p1y) * (vertices[2].getW() - vertices[1].getW());
                double zStart = vertices[1].getZ() + ((double)y - p1y) / (p2y - p1y) * (vertices[2].getZ() - vertices[1].getZ());

                double uEnd = vertices[0].getU() + ((double)y - p0y) / (p2y - p0y) * (vertices[2].getU() - vertices[0].getU());
                double vEnd = vertices[0].getV() + ((double)y - p0y) / (p2y - p0y) * (vertices[2].getV() - vertices[0].getV());
                double wEnd = vertices[0].getW() + ((double)y - p0y) / (p2y - p0y) * (vertices[2].getW() - vertices[0].getW());
                double zEnd = vertices[0].getZ() + ((double)y - p0y) / (p2y - p0y) * (vertices[2].getZ() - vertices[0].getZ());

                if (x1 > x2) {
                    int holder = x1;
                    x1 = x2;
                    x2 = holder;

                    double holder2;

                    holder2 = uStart;
                    uStart = uEnd;
                    uEnd = holder2;
                    
                    holder2 = vStart;
                    vStart = vEnd;
                    vEnd = holder2;

                    holder2 = wStart;
                    wStart = wEnd;
                    wEnd = holder2;

                    holder2 = zStart;
                    zStart = zEnd;
                    zEnd = holder2;

                }

                if (x2 > x1) {
                    double u = uStart * t.getTexture().getTextureWidth();
                    double v = vStart * t.getTexture().getTextureWidth();
                    double w = wStart;
                    double z = zStart;

                    double ustep = (uEnd - uStart) / (x2 - x1) * t.getTexture().getTextureHeight();
                    double vstep = (vEnd - vStart) / (x2 - x1) * t.getTexture().getTextureHeight();
                    double wstep = (wEnd - wStart) / (x2 - x1);
                    double zstep = (zEnd - zStart) / (x2 - x1);

                    for (int x = x1; x <= x2; x++) {
                        u += ustep;
                        v += vstep;
                        w += wstep;
                        z += zstep;

                        if (z > depthBuffer[y * WIDTH + x]) {

                            int color = t.getTexture().getTexturePixel(u / w, v / w);
                            float light = t.getLight();

                            int red = (int)(((color >> 16) & 0xFF) * light);
                            int green = (int)(((color >> 8) & 0xFF) * light);
                            int blue = (int)((color & 0xFF) * light);

                            int rgb = (red << 16) | (green << 8) | blue;

                            image.setRGB(x, y, rgb);
                            depthBuffer[y * WIDTH + x] = (float)z;

                        }

                    }

                }

            }

        }

    }

}
