package com.github.abdullahbeg.engine3d.userinput;

import java.awt.event.KeyEvent;
import java.awt.MouseInfo;

import com.github.abdullahbeg.engine3d.mesh.Vertex;
import com.github.abdullahbeg.engine3d.math.Camera;
import com.github.abdullahbeg.engine3d.math.Vector;
import com.github.abdullahbeg.engine3d.misc.Location;
import com.github.abdullahbeg.engine3d.render.Engine;

public class InputHandler {
    
    private final KeyInput keyInput;
    private final MouseInput mouseInput;
    private final MouseWheelInput mouseWheelInput;

    public InputHandler() {

        keyInput = new KeyInput();
        mouseInput = new MouseInput();
        mouseWheelInput = new MouseWheelInput();

    }

    public KeyInput getKeyInput() { return keyInput; }
    public MouseInput getMouseInput() { return mouseInput; }
    public MouseWheelInput getMouseWheelInput() { return mouseWheelInput; }

    public void updateKeyInput(float pitch, float roll, float VELOCITY, Camera camera) {

        Vertex offset;
        Vertex cameraOffset = camera.getCameraOffset();

        if (keyInput.isKeyPressed(KeyEvent.VK_W)) {

            offset = camera.calculateCameraOffset(pitch, roll, VELOCITY);
            cameraOffset = Vector.sub(cameraOffset, new Vertex(offset.getX(), 0, offset.getZ()));
            
        }

        if (keyInput.isKeyPressed(KeyEvent.VK_A)) {
            
            offset = camera.calculateCameraOffset(pitch + 90, roll, VELOCITY);
            cameraOffset = Vector.sub(cameraOffset, new Vertex(offset.getX(), 0, offset.getZ()));
            
        }
        
        if (keyInput.isKeyPressed(KeyEvent.VK_S)) {
            
            offset = camera.calculateCameraOffset(pitch, roll, VELOCITY);
            cameraOffset = Vector.add(cameraOffset, new Vertex(offset.getX(), 0, offset.getZ()));
            
        }
        
        if (keyInput.isKeyPressed(KeyEvent.VK_D)) {
            
            offset = camera.calculateCameraOffset(pitch - 90, roll, VELOCITY);
            cameraOffset = Vector.sub(cameraOffset, new Vertex(offset.getX(), 0, offset.getZ()));
            
        }

        if (keyInput.isKeyPressed(KeyEvent.VK_SPACE)) {
            cameraOffset = Vector.add(cameraOffset, new Vertex(0, -VELOCITY, 0));

        }

        if (keyInput.isKeyPressed(KeyEvent.VK_SHIFT)) {
            cameraOffset = Vector.add(cameraOffset, new Vertex(0, VELOCITY, 0));

        }

        camera.setCameraOffset(cameraOffset);

    }

    public void updateMouseInput(Location previousMouseLocation, Engine engine, float LOOK_SENSITIVITY_X, float LOOK_SENSITIVITY_Y) {

        if (keyInput.isKeyPressed(KeyEvent.VK_F)) {
            Location currentMouseLocation = new Location(
                    (float)MouseInfo.getPointerInfo().getLocation().getX(),
                    (float)MouseInfo.getPointerInfo().getLocation().getY()
            );

            float dx = previousMouseLocation.getX() - currentMouseLocation.getX();
            float dy = previousMouseLocation.getY() - currentMouseLocation.getY();

            previousMouseLocation.setNewLocation(currentMouseLocation);

            engine.setPitch(engine.getPitch() + dx / LOOK_SENSITIVITY_X);
            engine.setRoll(engine.getRoll() - dy / LOOK_SENSITIVITY_Y);

        } else {
            if (mouseInput.isLeftMouseButtonPressed()) {
                Location currentMouseLocation = new Location(
                        (float)MouseInfo.getPointerInfo().getLocation().getX(),
                        (float)MouseInfo.getPointerInfo().getLocation().getY()
                );

                float dx = mouseInput.getCenterLocation().getX() - currentMouseLocation.getX();
                float dy = mouseInput.getCenterLocation().getY() - currentMouseLocation.getY();

                engine.setPitch(engine.getPitch() + dx / LOOK_SENSITIVITY_X);
                engine.setRoll(engine.getRoll() - dy / LOOK_SENSITIVITY_Y);

                mouseInput.setCenterLocation(currentMouseLocation);

            }

        }

    }

    public void updateMouseWheelInput(Engine engine, float SCROLL_SPEED) {

        engine.setScale(engine.getScale() - mouseWheelInput.getMouseWheelRotation() * SCROLL_SPEED);
        mouseWheelInput.clearMouseWheelRotation();

    }

}
