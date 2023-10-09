package com.dating.klicked.Model.ResponseRepo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlanResponse {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("dailyRequest")
    @Expose
    private String dailyRequest;
    @SerializedName("viewHightlightPicture")
    @Expose
    private Boolean viewHightlightPicture;
    @SerializedName("mostCompatibleProfile")
    @Expose
    private Boolean mostCompatibleProfile;
    @SerializedName("hideMyProfile")
    @Expose
    private Boolean hideMyProfile;
    @SerializedName("mostLikedProfile")
    @Expose
    private Boolean mostLikedProfile;
    @SerializedName("whoLikedMyProfile")
    @Expose
    private Boolean whoLikedMyProfile;
    @SerializedName("chatMessage")
    @Expose
    private String chatMessage;
    @SerializedName("profileViewed")
    @Expose
    private String profileViewed;
    @SerializedName("callMinutes")
    @Expose
    private String callMinutes;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("viewProfilePicture")
    @Expose
    private Boolean viewProfilePicture;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

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

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDailyRequest() {
        return dailyRequest;
    }

    public void setDailyRequest(String dailyRequest) {
        this.dailyRequest = dailyRequest;
    }

    public Boolean getViewHightlightPicture() {
        return viewHightlightPicture;
    }

    public void setViewHightlightPicture(Boolean viewHightlightPicture) {
        this.viewHightlightPicture = viewHightlightPicture;
    }

    public Boolean getMostCompatibleProfile() {
        return mostCompatibleProfile;
    }

    public void setMostCompatibleProfile(Boolean mostCompatibleProfile) {
        this.mostCompatibleProfile = mostCompatibleProfile;
    }

    public Boolean getHideMyProfile() {
        return hideMyProfile;
    }

    public void setHideMyProfile(Boolean hideMyProfile) {
        this.hideMyProfile = hideMyProfile;
    }

    public Boolean getMostLikedProfile() {
        return mostLikedProfile;
    }

    public void setMostLikedProfile(Boolean mostLikedProfile) {
        this.mostLikedProfile = mostLikedProfile;
    }

    public Boolean getWhoLikedMyProfile() {
        return whoLikedMyProfile;
    }

    public void setWhoLikedMyProfile(Boolean whoLikedMyProfile) {
        this.whoLikedMyProfile = whoLikedMyProfile;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public String getProfileViewed() {
        return profileViewed;
    }

    public void setProfileViewed(String profileViewed) {
        this.profileViewed = profileViewed;
    }

    public String getCallMinutes() {
        return callMinutes;
    }

    public void setCallMinutes(String callMinutes) {
        this.callMinutes = callMinutes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getViewProfilePicture() {
        return viewProfilePicture;
    }

    public void setViewProfilePicture(Boolean viewProfilePicture) {
        this.viewProfilePicture = viewProfilePicture;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "PlanResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", details=" + details +
                ", discount=" + discount +
                ", price=" + price +
                ", duration=" + duration +
                ", dailyRequest=" + dailyRequest +
                ", viewHightlightPicture=" + viewHightlightPicture +
                ", mostCompatibleProfile=" + mostCompatibleProfile +
                ", hideMyProfile=" + hideMyProfile +
                ", mostLikedProfile=" + mostLikedProfile +
                ", whoLikedMyProfile=" + whoLikedMyProfile +
                ", chatMessage=" + chatMessage +
                ", profileViewed=" + profileViewed +
                ", callMinutes=" + callMinutes +
                ", description='" + description + '\'' +
                ", viewProfilePicture=" + viewProfilePicture +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }

    public class Detail {

        @SerializedName("detail")
        @Expose
        private String detail;
        @SerializedName("_id")
        @Expose
        private String id;

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Detail{" +
                    "detail='" + detail + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }
    }
}
