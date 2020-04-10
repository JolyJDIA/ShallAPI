package jolyjdia.nms.util;

import net.minecraft.server.v1_15_R1.Vector3f;

public class CraftVector {
    private final float x;
    private final float y;
    private final float z;

    public CraftVector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f toVector3f() {
        return new Vector3f(x, y, z);
    }

    public static CraftVector fromVector3f(Vector3f vector3F) {
        return new CraftVector(vector3F.getX(), vector3F.getY(), vector3F.getZ());
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
