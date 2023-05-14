package com.github.abdullahbeg.engine3d.userinput;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseWheelInput implements MouseWheelListener{
    
    private int mouseWheelRotation;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        mouseWheelRotation = e.getWheelRotation();

    }

    public int getMouseWheelRotation() { return mouseWheelRotation; }

    public void clearMouseWheelRotation() { mouseWheelRotation = 0; }

}
