package net.saif.rendering.math.pixel;

import java.awt.*;

public class PixelColor {
    private double red;
    private double green;
    private double blue;

    public PixelColor(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public double getRed() {
        return red;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public double getGreen() {
        return green;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public double getBlue() {
        return blue;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }

    public double getLuminance() {
        return red * 0.2126F + green * 0.7152F + blue * 0.0722F;
    }

    public int getRGB() {
        int redPart = (int) (red * 255);
        int greenPart = (int) (green * 255);
        int bluePart = (int) (blue * 255);

        // Shift bits to right place
        redPart = (redPart << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        greenPart = (greenPart << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        bluePart = bluePart & 0x000000FF; //Mask out anything not blue

        return 0xFF000000 | redPart | greenPart | bluePart;
    }

    public PixelColor multiply(float brightness) {
        brightness = Math.min(1, brightness);
        return new PixelColor(red * brightness, green * brightness, blue * brightness);
    }

    public PixelColor add(float n) {
        return new PixelColor(
                Math.min(1, red + n),
                Math.min(1, green + n),
                Math.min(1, blue + n)
        );
    }

    public Color toAWT() {
        return new Color((float) red, (float) green, (float) blue);
    }

    public PixelColor add(PixelColor c) {
        return new PixelColor(
                Math.min(1, red + c.red),
                Math.min(1, green + c.green),
                Math.min(1, blue + c.blue)
        );
    }

    public static PixelColor lerp(PixelColor a, PixelColor b, float factor) {
        double diffRed = b.red - a.red;
        double diffGreen = b.green - a.green;
        double diffBlue = b.blue - a.blue;
        return new PixelColor(a.red + (diffRed * factor), a.green + (diffGreen * factor), a.blue + (diffBlue * factor));
    }

    public static PixelColor fromAWT(Color color) {
        return new PixelColor(color.getRed() / 255.0, color.getBlue() / 255.0, color.getGreen() / 255.0);
    }
}
