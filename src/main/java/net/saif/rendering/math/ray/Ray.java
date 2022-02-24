package net.saif.rendering.math.ray;

import net.saif.rendering.math.Vector3;

public class Ray {
    private final Vector3 direction;
    private Vector3 origin;

    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction;

        this.direction.normalize();
    }

    public Vector3 getDirection() {
        return direction;
    }

    public Vector3 getOrigin() {
        return origin;
    }

    public void setDirection(Vector3 vec) {
        this.direction.setX(vec.getX());
        this.direction.setY(vec.getY());
        this.direction.setZ(vec.getZ());
        this.direction.normalize();
    }

    public void setOrigin(Vector3 origin) {
        this.origin = origin;
    }
}
