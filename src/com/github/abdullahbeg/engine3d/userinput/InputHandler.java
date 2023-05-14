package com.github.abdullahbeg.engine3d.userinput;

import java.awt.event.KeyEvent;

public class InputHandler {
    
    private final KeyInput keyInput;

    public InputHandler() {

        keyInput = new KeyInput();

    }

    public KeyInput getKeyInput() { return keyInput; }

    public void updateKeyInput() {

        if (keyInput.isKeyPressed(KeyEvent.VK_A)) {
            
            System.out.println("A key pressed!");
            
        }
        
        if (keyInput.isKeyPressed(KeyEvent.VK_S)) {

            System.out.println("S key pressed!");
            
        }
        
        if (keyInput.isKeyPressed(KeyEvent.VK_D)) {
            
            System.out.println("D key pressed!");
            
        }

    }

}
