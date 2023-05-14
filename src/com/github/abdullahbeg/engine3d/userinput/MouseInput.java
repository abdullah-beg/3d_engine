package com.github.abdullahbeg.engine3d.userinput;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

import com.github.abdullahbeg.engine3d.misc.Location;

public class MouseInput implements MouseInputListener {

    private boolean leftMouseButtonPressed, rightMouseButtonPressed;
    private Location mouseLocation;
    private Location centerLocation;

    @Override
    public void mousePressed(MouseEvent e) {
        
        mouseLocation = new Location(e.getX(), e.getY());

        int button = e.getButton();

        if (button == MouseEvent.BUTTON1) {
            leftMouseButtonPressed = true;
            centerLocation = new Location(
                (float)MouseInfo.getPointerInfo().getLocation().getX(),
                (float)MouseInfo.getPointerInfo().getLocation().getY()
            );
        }

        if (button == MouseEvent.BUTTON3) {
            rightMouseButtonPressed = true;
            centerLocation = new Location(
                (float)MouseInfo.getPointerInfo().getLocation().getX(),
                (float)MouseInfo.getPointerInfo().getLocation().getY()
            );
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        int button = e.getButton();

        if (button == MouseEvent.BUTTON1) leftMouseButtonPressed = false;
        if (button == MouseEvent.BUTTON3) rightMouseButtonPressed = false;
        
    }

    public boolean isLeftMouseButtonPressed() {

        return leftMouseButtonPressed;

    }

    public boolean isRightMouseButtonPressed() {

        return rightMouseButtonPressed;

    }

    public Location getMouseLocation() {

        return mouseLocation;

    }

    public Location getCenterLocation() {

        return centerLocation;

    }

    public void setCenterLocation(Location location) {

        centerLocation = location;

    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    
    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

}
