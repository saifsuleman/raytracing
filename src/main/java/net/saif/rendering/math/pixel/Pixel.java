package net.saif.rendering.math.pixel;

public class Pixel {
    private PixelColor color;
    private float depth;
    private float emission;

    public Pixel(PixelColor color, float depth, float emission) {
        this.color = color;
        this.depth = depth;
        this.emission = emission;
    }

    public PixelColor getColor() {
        return color;
    }

    public void setColor(PixelColor color) {
        this.color = color;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public float getEmission() {
        return emission;
    }

    public void setEmission(float emission) {
        this.emission = emission;
    }
}
