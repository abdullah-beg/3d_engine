package com.github.abdullahbeg.engine3d.renderer;

import javax.swing.JFrame;
import java.io.IOException;

public class Main {
    
    private static final JFrame frame = new JFrame();
    private static final String WINDOW_TITLE = "3D ENGINE";
    
    public Main() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle(WINDOW_TITLE);

        Engine engine = new Engine(this);

        frame.add(engine);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void setWindowTitle(String title) {

        frame.setTitle(WINDOW_TITLE + title);

    }

    public static void main(String[] args) throws IOException {

        Main main = new Main();

    }


}
