package mate.academy.internetshop.model;

public class Role {
    private final Long id;
    private RoleName roleName;

    public Role() {
        this.id = IdGenerator.incRoleId();
    }

    public Role(RoleName rolename) {
        this();
        this.roleName = rolename;
    }

    public Long getId() {
        return IdGenerator.getRoleId();
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public static Role of(String roleName) {
        return new Role(RoleName.valueOf(roleName));
    }

     public enum RoleName {
        USER, ADMIN;
    }
}
