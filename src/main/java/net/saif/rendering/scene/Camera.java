package net.saif.rendering.scene;

import net.saif.rendering.math.Vector3;

public class Camera extends Entity {
    private float fov;

    public Camera(Vector3 position, float pitch, float yaw, float fov) {
        super(position, pitch, yaw);
        this.fov = fov;
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }
}
