package net.saif.rendering.scene;

import net.saif.rendering.math.Vector3;

public class Entity {
    private Vector3 position;
    private float pitch;
    private float yaw;

    public Entity(Vector3 position, float pitch, float yaw) {
        this.position = position;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public Entity(Vector3 position) {
        this(position, 0, 0);
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
}
