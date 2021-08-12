package sg.edu.rp.c346.id19045083.oursingapore;

import java.io.Serializable;

public class Food implements Serializable {

    private int _id;
    private String name;
    private String location;
    private String description;
    private int stars;

    public Food(int _id, String name, String location, String description, int stars) {
        this._id = _id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.stars = stars;
    }

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
