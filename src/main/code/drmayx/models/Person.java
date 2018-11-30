package code.drmayx.models;

public class Person {

    private int id;
    private String name;
    private String info;

    public Person() {}
    public Person(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return "Name: " + this.name + "\tInfo: " + this.info;
    }
}
