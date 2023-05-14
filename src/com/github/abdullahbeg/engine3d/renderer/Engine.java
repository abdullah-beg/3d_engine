package com.github.abdullahbeg.engine3d.renderer;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.github.abdullahbeg.engine3d.mesh.Triangle;
import com.github.abdullahbeg.engine3d.objects.Cube;
import com.github.abdullahbeg.engine3d.userinput.InputHandler;

public class Engine extends JPanel implements Runnable {
    
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final int FPS = 60;
    private static final double GAME_TICK_INTERVAL = (double)1000000000 / FPS;

    private static final Color BACKGROUND_COLOR = Color.BLACK;

    private float yaw = 0;
    private float pitch = 0;
    private float roll = 0;
    private float scale = 250;

    private Thread engineThread;
    private final Main main;
    private final Renderer renderer;
    private final InputHandler inputHandler;

    private ArrayList<Triangle> triangles;

    public Engine(Main main) {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(BACKGROUND_COLOR);
        setDoubleBuffered(true);
        setFocusable(true);
                
        engineThread = new Thread(this);
        this.main = main;
        renderer = new Renderer(WIDTH, HEIGHT);   
        inputHandler = new InputHandler();

        addKeyListener(inputHandler.getKeyInput());

        triangles = new ArrayList<>();
        Cube c = new Cube(triangles); 
        
        engineThread.start();

    }

    @Override
    public void run() {

        long oldTime = System.nanoTime();
        long newTime;
        double delta = 0;
        long timer = 0;
        int frameCount = 0;

        while (engineThread != null) {
            newTime = System.nanoTime();
            delta += (newTime - oldTime) / GAME_TICK_INTERVAL;
            timer += (newTime - oldTime);
            oldTime = newTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;

            }

            frameCount++;

            // If one second has passed
            if (timer >= 1000000000) {
                main.setWindowTitle(" | " + frameCount + " FPS");
                frameCount = 0;
                timer = 0;

            }

        }

    }

    public void update() {

        yaw++;
        pitch++;
        roll++;

        inputHandler.updateKeyInput();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        renderer.renderTriangles(yaw, pitch, roll, scale, triangles, g2);
        
    }

}
