package identity;


import contract.Identifiable;
import showpiece.Showpiece;

public abstract class Person extends Showpiece implements Identifiable {

    protected String name;
    protected String email;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract String getRoleDescription();

    @Override
    public String toString() {
        return "Person{name='" + name + "', email='" + email + "'}";
    }

    @Override
    public int hashCode() {
        return super.hashCode() + (name != null ? name.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof Person person)) return false;
        return name.equals(person.name);
    }
}
