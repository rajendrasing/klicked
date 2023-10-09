package com.dating.klicked.Authentication.Adapter;

public class ShowCardModal {
    String name, backgroundImage, icon,SubName;
    boolean checked;

    public ShowCardModal(String name, String backgroundImage, String icon,String SubName,boolean checked) {
        this.name = name;
        this.backgroundImage = backgroundImage;
        this.icon = icon;
        this.checked = checked;
        this.SubName = SubName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getSubName() {
        return SubName;
    }

    public void setSubName(String subName) {
        SubName = subName;
    }

    @Override
    public String toString() {
        return "ShowCardModal{" +
                "name='" + name + '\'' +
                ", backgroundImage='" + backgroundImage + '\'' +
                ", icon='" + icon + '\'' +
                ", SubName='" + SubName + '\'' +
                ", checked=" + checked +
                '}';
    }
}
