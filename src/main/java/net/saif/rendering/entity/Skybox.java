package net.saif.rendering.entity;

import net.saif.rendering.math.Vector3;
import net.saif.rendering.math.pixel.Pixel;
import net.saif.rendering.math.pixel.PixelColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Skybox {
    private BufferedImage sphereImage;
    private boolean loaded;

    public Skybox(String resourceName) {
        sphereImage = new BufferedImage(2,2, BufferedImage.TYPE_INT_RGB);
        loaded = false;

        new Thread("Skybox loader") {
            @Override
            public void run() {
                try {
                    System.out.println("Loading skybox "+resourceName+"...");
                    sphereImage = ImageIO.read(getClass().getResourceAsStream("/res/"+resourceName));
                    System.out.println("Skybox ready.");
                    loaded = true;
                } catch (IOException | IllegalArgumentException ex) {
                    try {
                        sphereImage = ImageIO.read(getClass().getResourceAsStream("/res/error_skybox.jpg"));
                        loaded = true;
                    } catch (IOException | IllegalArgumentException ex2) {
                        ex2.printStackTrace();
                        System.exit(-1);
                    }
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    public PixelColor getColor(Vector3 d) {
        // Convert Unit vector to texture coordinates (Wikipedia UV Unwrapping page)
        float u = (float) (0.5+Math.atan2(d.getZ(), d.getX())/(2*Math.PI));
        float v = (float) (0.5 - Math.asin(d.getY())/Math.PI);

        try {
            Color color = new Color(sphereImage.getRGB((int)(u*(sphereImage.getWidth()-1)), (int)(v*(sphereImage.getHeight()-1))));
            return new PixelColor(color.getRed()/255.0, color.getGreen()/255.0, color.getBlue()/255.0);
        } catch (Exception e) {
            System.out.println("U: "+u+" V: "+v);
            e.printStackTrace();

            return new PixelColor(1, 0.4, 0.6);
        }
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void reload(String resourceName) {
        sphereImage = new BufferedImage(2,2, BufferedImage.TYPE_INT_RGB);
        loaded = false;

        new Thread("Skybox loader") {
            @Override
            public void run() {
                try {
                    System.out.println("Loading skybox "+resourceName+"...");
                    sphereImage = ImageIO.read(getClass().getResourceAsStream("/res/"+resourceName));
                    System.out.println("Skybox ready.");
                    loaded = true;
                } catch (IOException | IllegalArgumentException ex) {
                    try {
                        sphereImage = ImageIO.read(getClass().getResourceAsStream("/res/error_skybox.jpg"));
                        loaded = true;
                    } catch (IOException | IllegalArgumentException ex2) {
                        ex2.printStackTrace();
                        System.exit(-1);
                    }
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    public void reloadFromFile(File file) {
        sphereImage = new BufferedImage(2,2, BufferedImage.TYPE_INT_RGB);
        loaded = false;

        new Thread("Skybox loader") {
            @Override
            public void run() {
                try {
                    System.out.println("Loading skybox "+file.getName()+"...");
                    sphereImage = ImageIO.read(file);
                    System.out.println("Skybox ready.");
                    loaded = true;
                } catch (IOException | IllegalArgumentException ex) {
                    try {
                        sphereImage = ImageIO.read(getClass().getResourceAsStream("/res/error_skybox.jpg"));
                        loaded = true;
                    } catch (IOException | IllegalArgumentException ex2) {
                        ex2.printStackTrace();
                        System.exit(-1);
                    }
                    ex.printStackTrace();
                }
            }
        }.start();
    }
}
