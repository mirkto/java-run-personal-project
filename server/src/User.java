package src;

public class User {
    private String id;
    private String name;

    //------------- constructors -------------
    public User() {
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    //------------- getters and setters -------------
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //------------- methods -------------
    public boolean matches(String str) {
        return str.equalsIgnoreCase(name) || str.equalsIgnoreCase(id);
    }

    @Override
    public String toString() {
        return "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
    }
}
