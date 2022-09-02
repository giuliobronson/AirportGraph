package model;

public class Airport {
    private String name;
    private int id;
    private static int count = 0;

    public Airport(String name) {
        count++;
        this.name = name;
        this.id = count;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
