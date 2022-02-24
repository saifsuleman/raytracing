package net.saif.rendering.scene;

import net.saif.rendering.math.Vector3;
import net.saif.rendering.math.ray.Ray;

public abstract class Shape extends Entity {
    private Material material;

    public Shape(Vector3 position, Material material) {
        super(position);
        this.material = material;
    }

    public abstract Vector3 getNormalAt(Vector3 point);

    public abstract Vector3 calculateIntersection(Ray ray);

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
