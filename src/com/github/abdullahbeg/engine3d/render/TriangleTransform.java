package com.github.abdullahbeg.engine3d.render;

import com.github.abdullahbeg.engine3d.math.Vector;
import com.github.abdullahbeg.engine3d.math.Matrix;
import com.github.abdullahbeg.engine3d.mesh.Triangle;
import com.github.abdullahbeg.engine3d.mesh.Vertex;

public class TriangleTransform {

    private TriangleTransform() {}

    public static Triangle rotateYaw(float yaw, Triangle t) {

        Vertex v1 = Vector.rotateYaw(yaw, t.getV1()); 
        Vertex v2 = Vector.rotateYaw(yaw, t.getV2()); 
        Vertex v3 = Vector.rotateYaw(yaw, t.getV3()); 
        
        return new Triangle(v1, v2, v3);

    }

    public static Triangle rotatePitch(float pitch, Triangle t) {

        Vertex v1 = Vector.rotatePitch(pitch, t.getV1()); 
        Vertex v2 = Vector.rotatePitch(pitch, t.getV2()); 
        Vertex v3 = Vector.rotatePitch(pitch, t.getV3()); 
        
        return new Triangle(v1, v2, v3);

    }

    public static Triangle rotateRoll(float roll, Triangle t) {
        
        Vertex v1 = Vector.rotateRoll(roll, t.getV1()); 
        Vertex v2 = Vector.rotateRoll(roll, t.getV2()); 
        Vertex v3 = Vector.rotateRoll(roll, t.getV3()); 
        
        return new Triangle(v1, v2, v3);

    }

    public static Triangle applyProjectionMatrix(Triangle t, Matrix matrix) {

        Vertex v1 = matrix.applyProjectionMatrix(t.getV1());
        Vertex v2 = matrix.applyProjectionMatrix(t.getV2());
        Vertex v3 = matrix.applyProjectionMatrix(t.getV3());

        return new Triangle(v1, v2, v3);

    }

    public static Triangle scale(float scale, Triangle t) {

        Vertex v1 = Vector.mult(t.getV1(), scale);
        Vertex v2 = Vector.mult(t.getV2(), scale);
        Vertex v3 = Vector.mult(t.getV3(), scale);

        return new Triangle(v1, v2, v3);

    }

    public static Triangle translate(Vertex offset, Triangle t) {

        Vertex v1 = Vector.add(t.getV1(), offset);
        Vertex v2 = Vector.add(t.getV2(), offset);
        Vertex v3 = Vector.add(t.getV3(), offset);

        return new Triangle(v1, v2, v3);

    }
    
}
