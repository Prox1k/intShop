package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Bucket {

    private List<Item> items;
    private Long userId;
    private Long bucketId;

    public Bucket() {
        this.items = new ArrayList<>();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Long getBucketId() {
        return bucketId;
    }

    public void setBucketId(Long bucketId) {
        this.bucketId = bucketId;
    }

    @Override
    public String toString() {
        return "Bucket{"
                + "items=" + items
                + ", userId=" + userId
                + ", bucketId=" + bucketId
                + '}';
    }
}
