package com.dating.klicked.Model;

public class SelectedSexualOrientationModel {
    String name;
    boolean isChecked;

    public SelectedSexualOrientationModel() {
    }

    public SelectedSexualOrientationModel(String name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    @Override
    public String toString() {
        return "SelectedSexualOrientationModel{" +
                "name='" + name + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
