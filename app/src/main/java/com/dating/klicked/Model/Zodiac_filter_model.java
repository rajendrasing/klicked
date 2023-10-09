package com.dating.klicked.Model;

public class Zodiac_filter_model {
    Integer icon;
    String name;

    public Zodiac_filter_model(Integer icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public Zodiac_filter_model() {
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Zodiac_filter_model{" +
                "icon=" + icon +
                ", name='" + name + '\'' +
                '}';
    }
}
