package net.saif.rendering.math;

public class Vector3 {
    private double x;
    private double y;
    private double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Vector3 add(double x, double y, double z) {
        return new Vector3(this.x+x,this.y+y,this.z+z);
    }

    public Vector3 add(Vector3 vec) {
        return add(vec.x, vec.y, vec.z);
    }

    public Vector3 multiply(double factor) {
        return new Vector3(this.x*factor,this.y*factor,this.z*factor);
    }

    public Vector3 subtract(Vector3 vec) {
        return new Vector3(this.x-vec.x,this.y-vec.y,this.z-vec.z);
    }

    public float distance(Vector3 vec) {
        double dx = vec.x - x;
        double dy = vec.y - y;
        double dz = vec.z - z;

        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3 normalize() {
        float length = length();
        return new Vector3(this.x/length,this.y/length,this.z/length);
    }

    public Vector3 rotateYP(float yaw, float pitch) {
        // Convert to radians
        double yawRads = Math.toRadians(yaw);
        double pitchRads = Math.toRadians(pitch);

        // Step one: Rotate around X axis (pitch)
        float _y = (float) (y*Math.cos(pitchRads) - z*Math.sin(pitchRads));
        float _z = (float) (y*Math.sin(pitchRads) + z*Math.cos(pitchRads));

        // Step two: Rotate around the Y axis (yaw)
        float _x = (float) (x*Math.cos(yawRads) + _z*Math.sin(yawRads));
        _z = (float) (-x*Math.sin(yawRads) + _z*Math.cos(yawRads));

        return new Vector3(_x, _y, _z);
    }

    public static float dot(Vector3 a, Vector3 b) {
        double d = a.x * b.x + a.y * b.y + a.z * b.z;
        return (float) d;
    }

    @Override
    public Vector3 clone() {
        return new Vector3(x, y, z);
    }
}
