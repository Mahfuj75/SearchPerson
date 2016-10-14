package com.onenation.oneworld.mahfuj75.searchperson.objectclass;

/**
 * Created by mahfu on 10/3/2016.
 */

public class MissingPerson {
    private String name;
    private String image;
    private  String age;
    private String lostFound;
    private String lastSeenLocation;



    public MissingPerson() {
    }


    public MissingPerson(String lastSeenLocation, String name, String image, String age, String lostFound) {
        this.lastSeenLocation = lastSeenLocation;
        this.name = name;
        this.image = image;
        this.age = age;
        this.lostFound = lostFound;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLastSeenLocation() {
        return lastSeenLocation;
    }

    public void setLastSeenLocation(String lastSeenLocation) {
        this.lastSeenLocation = lastSeenLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLostFound() {
        return lostFound;
    }

    public void setLostFound(String lostFound) {
        this.lostFound = lostFound;
    }
}
