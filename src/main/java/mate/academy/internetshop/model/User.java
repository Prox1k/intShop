package mate.academy.internetshop.model;

public class User {

    private String name;
    private Long userId;
    private Bucket bucket;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Bucket getBucket() {
        return bucket;
    }

    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }

    @Override
    public String toString() {
        return "User{"
                + "name='" + name + '\''
                + ", userId=" + userId
                + ", bucket=" + bucket
                + '}';
    }
}
