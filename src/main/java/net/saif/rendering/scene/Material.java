package net.saif.rendering.scene;

import net.saif.rendering.math.pixel.PixelColor;

import java.awt.*;

public class Material {
    private PixelColor color;
    private float reflectivity;
    private float emission;

    public Material(PixelColor color, float reflectivity, float emission) {
        this.color = color;
        this.reflectivity = reflectivity;
        this.emission = emission;
    }

    public PixelColor getColor() {
        return color;
    }

    public void setColor(PixelColor color) {
        this.color = color;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public float getEmission() {
        return emission;
    }

    public void setEmission(float emission) {
        this.emission = emission;
    }
}
