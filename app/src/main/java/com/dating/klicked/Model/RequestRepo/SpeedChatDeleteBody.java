package com.dating.klicked.Model.RequestRepo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpeedChatDeleteBody {
    @SerializedName("id")
    @Expose
    public List<String> id = null;

    public SpeedChatDeleteBody() {
    }


    public SpeedChatDeleteBody(List<String> id) {
        super();
        this.id = id;
    }
}
