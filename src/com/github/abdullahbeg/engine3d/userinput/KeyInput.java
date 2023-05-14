package com.github.abdullahbeg.engine3d.userinput;

import java.util.HashMap;
import java.util.Map;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

    private Map<Integer, Boolean> keys = new HashMap<>();

    public KeyInput() {

        keys.put(KeyEvent.VK_W, false);
        keys.put(KeyEvent.VK_A, false);
        keys.put(KeyEvent.VK_S, false);
        keys.put(KeyEvent.VK_D, false);
        keys.put(KeyEvent.VK_SPACE, false);
        keys.put(KeyEvent.VK_SHIFT, false);
        keys.put(KeyEvent.VK_F, true);
    
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (keys.containsKey(key)) {
            if (key == KeyEvent.VK_F) { keys.put(key, !keys.get(key)); }
            else { keys.put(key, true); }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (keys.containsKey(key)) {
            if (key == KeyEvent.VK_F) {}
            else { keys.put(key, false); }
        }

    }

    public boolean isKeyPressed(int keyCode) {
        return keys.getOrDefault(keyCode, false);
    }

}