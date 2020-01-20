package mate.academy.internetshop.model;

public class IdGenerator {
    private static Long bucketId = 0L;
    private static Long itemId = 0L;
    private static Long orderId = 0L;
    private static Long userId = 0L;
    private static Long roleId = 0L;

    public static Long getBucketId() {
        return bucketId;
    }

    public static Long getItemId() {
        return itemId;
    }

    public static Long getOrderId() {
        return orderId;
    }

    public static Long getUserId() {
        return userId;
    }

    public static Long getRoleId() {
        return roleId;
    }

    public static Long incBucketId() {
        bucketId++;
        return bucketId;
    }

    public static Long incItemId() {
        itemId++;
        return itemId;
    }

    public static Long incOrderId() {
        orderId++;
        return orderId;
    }

    public static Long incUserId() {
        userId++;
        return userId;
    }

    public static Long incRoleId() {
        roleId++;
        return roleId;
    }
}
