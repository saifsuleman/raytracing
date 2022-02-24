package net.saif.rendering;

import net.saif.rendering.entity.Sphere;
import net.saif.rendering.math.Vector3;
import net.saif.rendering.math.pixel.PixelColor;
import net.saif.rendering.rendering.Renderer;
import net.saif.rendering.scene.Material;
import net.saif.rendering.scene.Scene;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Viewport extends JPanel {
    private Robot robot;
    private Scene scene;
    private float mouseSensitivity = 0.01F;
    private BufferedImage frameBuffer;

    public Viewport(JFrame frame) {
        setFocusable(true);

        setSize(1280, 720);

        try {
            this.robot = new Robot();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    scene.getCamera().setPosition(scene.getCamera().getPosition().add(new Vector3(1,0,0)
                            .rotateYP(scene.getCamera().getYaw(),scene.getCamera().getPitch())));
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    scene.getCamera().setPosition(scene.getCamera().getPosition().add(new Vector3(-1,0,0)
                            .rotateYP(scene.getCamera().getYaw(),scene.getCamera().getPitch())));
                } else if (e.getKeyCode() == KeyEvent.VK_W) {
                    scene.getCamera().setPosition(scene.getCamera().getPosition().add(new Vector3(0,0,1)
                            .rotateYP(scene.getCamera().getYaw(),scene.getCamera().getPitch())));
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    scene.getCamera().setPosition(scene.getCamera().getPosition().add(new Vector3(0,0,-1)
                            .rotateYP(scene.getCamera().getYaw(),scene.getCamera().getPitch())));
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    scene.getCamera().setYaw(scene.getCamera().getYaw()+10);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    scene.getCamera().setYaw(scene.getCamera().getYaw()-10);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    scene.getCamera().setYaw(scene.getCamera().getPitch()+10);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    scene.getCamera().setYaw(scene.getCamera().getPitch()-10);
                }
            }
        });
    }

    public void run() {
        scene = new Scene();
        scene.getCamera().setYaw(0);
        scene.getCamera().setPitch(0);

        net.saif.rendering.rendering.Renderer renderer = new Renderer();
        scene.add(new Sphere(new Vector3(1, 0, 0), new Material(new PixelColor(1, 0, 0), 0.4f, 0.4f), 1));
        scene.add(new Sphere(new Vector3(5, 0, 0), new Material(new PixelColor(0.4, 0.2, 1), 0.4f, 0.4f), 1));

        long deltaTime = 1;

        try {
            File file = new File("image.png");
            BufferedImage img = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_RGB);
            renderer.render(scene, img.getGraphics(), 1280, 720);
            ImageIO.write(img, "PNG", new FileOutputStream(file));
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            long startTime = System.currentTimeMillis();

            BufferedImage tempBuffer = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_RGB);
            renderer.render(scene, tempBuffer.getGraphics(), 1280, 720);
            frameBuffer = tempBuffer;

            deltaTime = System.currentTimeMillis() * startTime;
            repaint();

            System.out.println("FPS: " + 1000.0/deltaTime);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (frameBuffer != null) g.drawImage(frameBuffer, 0, 0, this);
    }
}
