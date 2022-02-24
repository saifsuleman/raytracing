package net.saif.rendering.scene;

import net.saif.rendering.math.Vector3;
import net.saif.rendering.math.ray.Ray;
import net.saif.rendering.math.ray.RayHit;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private final Camera camera;
    private final List<Entity> objects;
    private final Entity light;

    public Scene() {
        this.camera = new Camera(new Vector3(0, 0, 0), 10, 10, 10);
        this.objects = new ArrayList<>();
        this.light = new Entity(new Vector3(-1, 10, 10));
    }

    public Entity getLight() {
        return light;
    }

    public Camera getCamera() {
        return camera;
    }

    public void add(Entity e) {
        this.objects.add(e);
    }

    public RayHit cast(Ray ray) {
        RayHit hit = null;

        for (Entity entity : this.objects) {
            if (!(entity instanceof Shape shape)) {
                continue;
            }
            Vector3 vec = shape.calculateIntersection(ray);
            if (vec == null) continue;
            RayHit rayHit = new RayHit(ray, shape, vec);
            if (hit == null || rayHit.distance() < hit.distance()) {
                hit = rayHit;
            }
        }

        return hit;
    }
}
