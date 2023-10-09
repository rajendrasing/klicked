package com.dating.klicked.Model;

public class CountryModel {
    String id, name;

    public CountryModel(String id, String name) {
        this.id = id;
        this.name = name;
    }


    public CountryModel() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }
}
