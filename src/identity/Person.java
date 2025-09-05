package identity;

public class Person {

    private String name;

    private String email;

    //Every person have their own name but not email - the reason why I have mentioned only this field in constructor.
    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
