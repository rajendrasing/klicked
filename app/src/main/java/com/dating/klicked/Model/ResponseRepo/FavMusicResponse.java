package com.dating.klicked.Model.ResponseRepo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavMusicResponse {

    @SerializedName("favMusic")
    @Expose
    private List<String> favMusic = null;

    public List<String> getFavMusic() {
        return favMusic;
    }

    public void setFavMusic(List<String> favMusic) {
        this.favMusic = favMusic;
    }

}