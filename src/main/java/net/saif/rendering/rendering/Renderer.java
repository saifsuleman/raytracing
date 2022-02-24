package net.saif.rendering.rendering;

import net.saif.rendering.entity.Skybox;
import net.saif.rendering.math.Vector3;
import net.saif.rendering.math.pixel.Pixel;
import net.saif.rendering.math.pixel.PixelBuffer;
import net.saif.rendering.math.pixel.PixelColor;
import net.saif.rendering.math.ray.Ray;
import net.saif.rendering.math.ray.RayHit;
import net.saif.rendering.scene.Camera;
import net.saif.rendering.scene.Entity;
import net.saif.rendering.scene.Scene;
import net.saif.rendering.scene.Shape;

import java.awt.*;

public class Renderer {
    private static final float GLOBAL_ILLUMINATION = 0.3F;
    private static final float SKY_EMISSION = 1.0F;
    private static final int MAX_REFLECTION_BOUNCES = 5;

    private Skybox box;

    public Renderer() {
        box = new Skybox("Sky.jpg");
    }

    public PixelBuffer render(Scene scene, Graphics gfx, int width, int height) {
        PixelBuffer buffer = new PixelBuffer(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float[] coords = getNormalizedScreenCoordinates(x, y, width, height);

                float u = coords[0];
                float v = coords[1];

                Pixel pixel = computePixelInfo(scene, u, v);
                buffer.setPixel(x, y, pixel);
                gfx.setColor(pixel.getColor().toAWT());
                gfx.fillRect(x, y, 1, 1);
            }
        }

        return buffer;
    }

    private float[] getNormalizedScreenCoordinates(int x, int y, int width, int height) {
        float u, v;

        if (width > height) {
            u = (float) (x - width / 2 + height / 2) / height * 2 - 1;
            v = -((float) y / height * 2 - 1);
        } else {
            u = (float) x / width * 2 - 1;
            v = -((float) (y - height / 2 + width / 2) / width * 2 - 1);
        }

        return new float[]{u, v};
    }

    private Pixel computePixelInfo(Scene scene, float u, float v) {
        Vector3 eyePos = new Vector3(0, 0, (float) (-1 / Math.tan(Math.toRadians(scene.getCamera().getFov() / 2))));
        Camera cam = scene.getCamera();
        Vector3 rayDir = new Vector3(u, v, 0).subtract(eyePos).normalize().rotateYP(cam.getYaw(), cam.getPitch());
        RayHit hit = scene.cast(new Ray(eyePos.add(cam.getPosition()), rayDir));
        if (hit != null) {
            return computePixelAtHit(scene, hit, MAX_REFLECTION_BOUNCES);
        } else {
            // show skybox fuck me
            return new Pixel(box.getColor(rayDir), 0.1f, SKY_EMISSION);
        }
    }

    private Pixel computePixelAtHit(Scene scene, RayHit hit, int bounces) {
        Vector3 hitPos = hit.getHitLocation();
        Vector3 rayDirection = hit.getRay().getDirection();
        Shape hitShape = hit.getHitShape();
        PixelColor hitColor = hitShape.getMaterial().getColor();

        float brightness = getDiffuseBrightness(scene, hit);
        float specular = getSpecularBrightness(scene, hit);
        float reflectivity = hit.getHitShape().getMaterial().getReflectivity();
        float emission = hitShape.getMaterial().getEmission();

        Pixel reflection;
        Vector3 reflectionVector = rayDirection.subtract(hit.getNormal().multiply(2 * Vector3.dot(rayDirection, hit.getNormal())));
        Vector3 reflectionRayOrigin = hitPos.add(reflectionVector.multiply(0.001F)); // Add a little to avoid hitting the same solid again
        RayHit reflectionHit = bounces > 0 ? scene.cast(new Ray(reflectionRayOrigin, reflectionVector)) : null;
        if (reflectionHit != null) {
            reflection = computePixelAtHit(scene, reflectionHit, bounces - 1);
        } else {
//            PixelColor sbColor = scene.getSkybox().getColor(reflectionVector);
            PixelColor sbColor = new PixelColor(1, 0.5, 0);
            reflection = new Pixel(sbColor, Float.POSITIVE_INFINITY, (float) (sbColor.getLuminance() * SKY_EMISSION));
        }

        PixelColor pixelColor = PixelColor.lerp(hitColor, reflection.getColor(), reflectivity) // Reflected color
                .multiply(brightness) // Diffuse lighting
                .add(specular) // Specular lighting
                .add(hitColor.multiply(emission)) // Object emission
                .add(reflection.getColor().multiply(reflection.getEmission() * reflectivity)); // Indirect illumination

        return new Pixel(pixelColor, scene.getCamera().getPosition().distance(hitPos), Math.min(1, emission + reflection.getEmission() * reflectivity + specular));
    }

    private static float getDiffuseBrightness(Scene scene, RayHit hit) {
        Entity sceneLight = scene.getLight();

        // Raytrace to light to check if something blocks the light
        RayHit lightBlocker = scene.cast(new Ray(sceneLight.getPosition(), hit.getHitLocation().subtract(sceneLight.getPosition()).normalize()));
        if (lightBlocker != null && lightBlocker.getHitShape().getMaterial() != hit.getHitShape().getMaterial()) {
            return GLOBAL_ILLUMINATION; // GOBAL_ILLUMINATION = Minimum brightness
        } else {
            return Math.max(GLOBAL_ILLUMINATION, Math.min(1, Vector3.dot(hit.getNormal(), sceneLight.getPosition().subtract(hit.getHitLocation()))));
        }
    }

    private static float getSpecularBrightness(Scene scene, RayHit hit) {
        Vector3 hitPos = hit.getHitLocation();
        Vector3 cameraDirection = scene.getCamera().getPosition().subtract(hitPos).normalize();
        Vector3 lightDirection = hitPos.subtract(scene.getLight().getPosition()).normalize();
        Vector3 lightReflectionVector = lightDirection.subtract(hit.getNormal().multiply(2*Vector3.dot(lightDirection, hit.getNormal())));

        float specularFactor = Math.max(0, Math.min(1, Vector3.dot(lightReflectionVector, cameraDirection)));
        return (float) Math.pow(specularFactor, 2)*hit.getHitShape().getMaterial().getReflectivity();
    }
}
