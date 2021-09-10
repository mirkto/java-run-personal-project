package src;

public class Person {
    private String  id;
    private String  name;
    //------------- constructors -------------
    public Person() {}
    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    //------------- setters and getters -------------
    public void     setId(String id)        {  this.id = id;        }
    public void     setName(String name)    {  this.name = name;    }

    public String   getId()     { return id;    }
    public String   getName()   { return name;  }

    //------------- methods -------------
    @Override
    public String toString() {
        return "\nPerson -  Id: " + id + ",  Name: " + name;
    }
}
