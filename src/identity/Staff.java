package identity;

public class Staff extends Person {

    private String role;

    //as staff is Person also it must have name and also specified role.
    public Staff(String name, String role) {
        super(name);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
