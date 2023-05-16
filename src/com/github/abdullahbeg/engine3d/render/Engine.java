package com.github.abdullahbeg.engine3d.render;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import com.github.abdullahbeg.engine3d.mesh.Triangle;
import com.github.abdullahbeg.engine3d.mesh.Vertex;
import com.github.abdullahbeg.engine3d.userinput.InputHandler;
import com.github.abdullahbeg.engine3d.math.Camera;
import com.github.abdullahbeg.engine3d.misc.Location;
import com.github.abdullahbeg.engine3d.object.Land;
import com.github.abdullahbeg.engine3d.object.Model;

public class Engine extends JPanel implements Runnable {
    
    // User info for Mouse
    private static final double USER_SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final double USER_SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private Location mouseLocation = new Location((float)USER_SCREEN_WIDTH / 2, (float)USER_SCREEN_HEIGHT / 2);

    // Window Size
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final int FPS = 30;
    private static final double GAME_TICK_INTERVAL = (double)1000000000 / FPS;

    // private static final Color BACKGROUND_COLOR = new Color(153,228,255); // DAY
    private static final Color BACKGROUND_COLOR = new Color(5, 6, 54); // NIGHT

    private Thread engineThread;
    private final Main main;
    private final Renderer renderer;
    private final InputHandler inputHandler;
    private final Camera camera;
    private BufferedImage window;

    private ArrayList<Triangle> triangles;
    private ArrayList<Vertex> lights;
    
    public Engine(Main main) {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(BACKGROUND_COLOR);
        setDoubleBuffered(true);
        setFocusable(true);
                
        this.main = main;
        engineThread = new Thread(this);
        renderer = new Renderer(WIDTH, HEIGHT);   
        camera = new Camera();
        
        inputHandler = new InputHandler(camera);
        addKeyListener(inputHandler.getKeyInput());
        addMouseListener(inputHandler.getMouseInput());
        addMouseWheelListener(inputHandler.getMouseWheelInput());

        engineThread.start();

        triangles = new ArrayList<>();
        lights = new ArrayList<>();

        Land l = new Land(triangles, 40, 2);
        // Model teapot = new Model("","",triangles);

        lights.add(new Vertex(0,47,0));

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

        inputHandler.updateKeyInput();
        inputHandler.updateMouseInput(mouseLocation);
        inputHandler.updateMouseWheelInput();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        window = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = (Graphics2D) g;

        renderer.renderTriangles(camera, triangles, lights, window);
        g2.drawImage(window, null, 0, 0);
        
    }

}
