package net.saif.rendering.math.pixel;

public class PixelBuffer {
    private final int width;
    private final int height;
    private Pixel[][] buffer;

    public PixelBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        this.buffer = new Pixel[width][height];
    }

    public void setPixel(int x, int y, Pixel pixel) {
        this.buffer[x][y] = pixel;
    }

    public Pixel getPixel(int x, int y) {
        return this.buffer[x][y];
    }
}
