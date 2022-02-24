package net.saif.rendering.entity;

import net.saif.rendering.math.Vector3;
import net.saif.rendering.math.ray.Ray;
import net.saif.rendering.scene.Material;
import net.saif.rendering.scene.Shape;

public class Sphere extends Shape {
    private final double radius;

    public Sphere(Vector3 position, Material material, double radius) {
        super(position, material);
        this.radius = radius;
    }

    @Override
    public Vector3 getNormalAt(Vector3 point) {
        return point.subtract(getPosition()).normalize();
    }

    @Override
    public Vector3 calculateIntersection(Ray ray) {
        Vector3 position = getPosition();
        float t = Vector3.dot(position.subtract(ray.getOrigin()), ray.getDirection());
        Vector3 p =  ray.getOrigin().add(ray.getDirection().multiply(t));

        float y = position.subtract(p).length();
        if (y < radius) {
            float x = (float) Math.sqrt(radius*radius - y*y);
            float t1 = t-x;
            if (t1 > 0) return ray.getOrigin().add(ray.getDirection().multiply(t1));
            else return null;
        } else {
            return null;
        }
    }
}
