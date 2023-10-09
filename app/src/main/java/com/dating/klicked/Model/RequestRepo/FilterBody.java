package com.dating.klicked.Model.RequestRepo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterBody {

    @SerializedName("filters")
    @Expose
    public Filters filters;


    public FilterBody() {
    }


    public FilterBody(Filters filters) {
        super();
        this.filters = filters;
    }

    public static class Filters {

        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("minAge")
        @Expose
        public Integer minAge;
        @SerializedName("maxAge")
        @Expose
        public Integer maxAge;
        @SerializedName("maxHeight")
        @Expose
        public Integer maxHeight;
        @SerializedName("minHeight")
        @Expose
        public Integer minHeight;
        @SerializedName("orientation")
        @Expose
        public List<String> orientation = null;
        @SerializedName("zodiacSign")
        @Expose
        public String zodiacSign;
        @SerializedName("cards")
        @Expose
        public List<String> cards = null;


        public Filters() {
        }

         public Filters(String city, Integer minAge, Integer maxAge, Integer maxHeight, Integer minHeight, List<String> orientation, String zodiacSign) {
            super();
            this.city = city;
            this.minAge = minAge;
            this.maxAge = maxAge;
            this.maxHeight = maxHeight;
            this.minHeight = minHeight;
            this.orientation = orientation;
            this.zodiacSign = zodiacSign;
        }

    }
}