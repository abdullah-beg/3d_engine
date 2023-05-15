package com.github.abdullahbeg.engine3d.render;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Toolkit;

import com.github.abdullahbeg.engine3d.mesh.Triangle;
import com.github.abdullahbeg.engine3d.objects.Cube;
import com.github.abdullahbeg.engine3d.userinput.InputHandler;
import com.github.abdullahbeg.engine3d.math.Camera;
import com.github.abdullahbeg.engine3d.misc.Location;

public class Engine extends JPanel implements Runnable {
    
    // User info for Mouse
    private static final double USER_SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final double USER_SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private Location mouseLocation = new Location((float)USER_SCREEN_WIDTH / 2, (float)USER_SCREEN_HEIGHT / 2);

    // Window Size
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final int FPS = 60;
    private static final double GAME_TICK_INTERVAL = (double)1000000000 / FPS;

    private static final Color BACKGROUND_COLOR = new Color(153,228,255);
    

    private float yaw = 0;
    private float pitch = 0;
    private float roll = 0;
    
    private float scale = 250;
    

    private static final float SCROLL_SPEED = 5;

    private static final float LOOK_SENSITIVITY_X = 4;
    private static final float LOOK_SENSITIVITY_Y = 4;

    private static final float VELOCITY = 0.077F;


    private Thread engineThread;
    private final Main main;
    private final Renderer renderer;
    private final InputHandler inputHandler;
    private final Camera camera;


    private ArrayList<Triangle> triangles;
    
    public Engine(Main main) {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(BACKGROUND_COLOR);
        setDoubleBuffered(true);
        setFocusable(true);
                
        engineThread = new Thread(this);
        this.main = main;
        renderer = new Renderer(WIDTH, HEIGHT);   
        camera = new Camera();
        
        inputHandler = new InputHandler();
        addKeyListener(inputHandler.getKeyInput());
        addMouseListener(inputHandler.getMouseInput());
        addMouseWheelListener(inputHandler.getMouseWheelInput());

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

        inputHandler.updateKeyInput(pitch, roll, VELOCITY, camera);
        inputHandler.updateMouseInput(mouseLocation, this, LOOK_SENSITIVITY_X, LOOK_SENSITIVITY_Y);
        inputHandler.updateMouseWheelInput(this, SCROLL_SPEED);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        renderer.renderTriangles(yaw, pitch, roll, scale, camera, triangles, g2);
        
    }

    public float getScale() { return scale; }
    public float getYaw() { return yaw; }
    public float getPitch() { return pitch; }
    public float getRoll() { return roll; }

    public void setScale(float scale) { this.scale = scale; }
    public void setYaw(float yaw) { this.yaw = yaw; }
    public void setPitch(float pitch) { this.pitch = pitch; }

    
    public void setRoll(float roll) { 
        
        if (roll < -45) { this.roll = -45; return; }
        else if (roll > 45) { this.roll = 45; return; }
        else { this.roll = roll; return; }

    }

}
