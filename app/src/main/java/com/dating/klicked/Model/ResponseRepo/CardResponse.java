package com.dating.klicked.Model.ResponseRepo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardResponse {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("backgroundImg")
    @Expose
    private String backgroundImg;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("card")
    @Expose
    private String card;

    public CardResponse() {
    }

    public CardResponse(String name, String backgroundImg, String icon, String card) {
        this.name = name;
        this.backgroundImg = backgroundImg;
        this.icon = icon;
        this.card = card;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "CardResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", backgroundImg='" + backgroundImg + '\'' +
                ", icon='" + icon + '\'' +
                ", card='" + card + '\'' +
                '}';
    }
}
