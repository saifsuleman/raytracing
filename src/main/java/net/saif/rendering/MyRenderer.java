package net.saif.rendering;

import net.saif.rendering.entity.Sphere;
import net.saif.rendering.math.Vector3;
import net.saif.rendering.math.pixel.PixelColor;
import net.saif.rendering.rendering.Renderer;
import net.saif.rendering.scene.Material;
import net.saif.rendering.scene.Scene;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MyRenderer {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Ray Tracing");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Viewport port = new Viewport(frame);
        frame.add(port);
        port.run();
    }
}
