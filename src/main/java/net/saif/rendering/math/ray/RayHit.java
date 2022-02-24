package net.saif.rendering.math.ray;

import net.saif.rendering.math.Vector3;
import net.saif.rendering.scene.Shape;

public class RayHit {
    private final Ray ray;
    private final Shape hitShape;
    private final Vector3 hitLocation;
    private final Vector3 hitNormal;

    public RayHit(Ray ray, Shape hitShape, Vector3 hitLocation) {
        this.ray = ray;
        this.hitShape = hitShape;
        this.hitLocation = hitLocation;
        this.hitNormal = hitShape.getNormalAt(hitLocation);
    }

    public Ray getRay() {
        return ray;
    }

    public Shape getHitShape() {
        return hitShape;
    }

    public Vector3 getHitLocation() {
        return hitLocation;
    }

    public Vector3 getNormal() {
        return hitNormal;
    }

    public float distance() {
        return ray.getOrigin().distance(hitLocation);
    }
}
