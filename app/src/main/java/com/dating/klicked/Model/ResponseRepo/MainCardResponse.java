package com.dating.klicked.Model.ResponseRepo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainCardResponse {

    @SerializedName("cards")
    @Expose
    private List<Card> cards = null;

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public class Card {
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("backgroundImg")
        @Expose
        private String backgroundImg;
        @SerializedName("icon")
        @Expose
        private String icon;


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


    }
}