package com.dating.klicked.Authentication.Adapter;

public class CardSelectedModel {
    String name, subCard, backgroundImage, icon;
    boolean checked;
    int position;

    public CardSelectedModel(String name, boolean checked, String subCard, String backgroundImage, String icon, int position) {
        this.name = name;
        this.checked = checked;
        this.subCard = subCard;
        this.backgroundImage = backgroundImage;
        this.icon = icon;
        this.position = position;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getSubCard() {
        return subCard;
    }

    public void setSubCard(String subCard) {
        this.subCard = subCard;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    @Override
    public String toString() {
        return "CardSelectedModel{" +
                "name='" + name + '\'' +
                ", subCard='" + subCard + '\'' +
                ", backgroundImage='" + backgroundImage + '\'' +
                ", icon='" + icon + '\'' +
                ", checked=" + checked +
                ", position=" + position +
                '}';
    }
}
