package com.dating.klicked.Model.RequestRepo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProfile {

    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("DOB")
    @Expose
    public String dob;
    @SerializedName("occupation")
    @Expose
    public String occuptation;
    @SerializedName("orientation")
    @Expose
    public List<String> orientation = null;
    @SerializedName("height")
    @Expose
    public String Height;
    @SerializedName("bio")
    @Expose
    public String bio;
    @SerializedName("cards")
    @Expose
    public List<String> cards = null;
    @SerializedName("address")
    @Expose
    public List<Address> address = null;

    @SerializedName("age")
    @Expose
    public String age;

    @SerializedName("maincards")
    @Expose
    public List<String> maincardsId = null;


    public UpdateProfile() {
    }

    public UpdateProfile(String email, String firstName, String gender, String phone, String dob, String occuptation, List<String> orientation, String Height, String bio, List<String> cards, List<Address> address, String age,List<String> maincardsId) {
        super();
        this.email = email;
        this.firstName = firstName;
        this.gender = gender;
        this.phone = phone;
        this.dob = dob;
        this.occuptation = occuptation;
        this.orientation = orientation;
        this.Height = Height;
        this.cards = cards;
        this.address = address;
        this.bio = bio;
        this.age = age;
        this.maincardsId = maincardsId;
    }

    public static class Address {

        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("state")
        @Expose
        public String state;
        @SerializedName("country")
        @Expose
        public String country;
        @SerializedName("countryCode")
        @Expose
        public String countryCode;


        public Address() {
        }

        public Address(String city, String state, String country, String countryCode) {
            super();
            this.city = city;
            this.state = state;
            this.country = country;
            this.countryCode = countryCode;
        }

    }

}
