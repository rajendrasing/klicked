package com.dating.klicked.Model.ResponseRepo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RewardHistoryResponse {

    @SerializedName("reward")
    @Expose
    private List<RewardItem> reward;

    public void setReward(List<RewardItem> reward) {
        this.reward = reward;
    }

    public List<RewardItem> getReward() {
        return reward;
    }

    @Override
    public String toString() {
        return
                "RewardHistoryResponse{" +
                        "reward = '" + reward + '\'' +
                        "}";
    }

    public class RewardItem {

        @SerializedName("createdAt")
        @Expose
        private String createdAt;

        @SerializedName("Userpoint")
        @Expose
        private String userpoint;

        @SerializedName("shareId")
        @Expose
        private ShareId shareId;

        @SerializedName("_id")
        @Expose
        private String id;

        @SerializedName("userId")
        @Expose
        private UserId userId;

        @SerializedName("Sharepoint")
        @Expose
        private String sharepoint;

        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setUserpoint(String userpoint) {
            this.userpoint = userpoint;
        }

        public String getUserpoint() {
            return userpoint;
        }

        public void setShareId(ShareId shareId) {
            this.shareId = shareId;
        }

        public ShareId getShareId() {
            return shareId;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setUserId(UserId userId) {
            this.userId = userId;
        }

        public UserId getUserId() {
            return userId;
        }

        public void setSharepoint(String sharepoint) {
            this.sharepoint = sharepoint;
        }

        public String getSharepoint() {
            return sharepoint;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        @Override
        public String toString() {
            return
                    "RewardItem{" +
                            "createdAt = '" + createdAt + '\'' +
                            ",userpoint = '" + userpoint + '\'' +
                            ",shareId = '" + shareId + '\'' +
                            ",_id = '" + id + '\'' +
                            ",userId = '" + userId + '\'' +
                            ",sharepoint = '" + sharepoint + '\'' +
                            ",updatedAt = '" + updatedAt + '\'' +
                            "}";
        }

        public class ShareId {

            @SerializedName("_id")
            @Expose
            private String id;

            @SerializedName("firstName")
            @Expose
            private String firstName;

            public void setId(String id) {
                this.id = id;
            }

            public String getId() {
                return id;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getFirstName() {
                return firstName;
            }

            @Override
            public String toString() {
                return
                        "ShareId{" +
                                "_id = '" + id + '\'' +
                                ",firstName = '" + firstName + '\'' +
                                "}";
            }
        }

        public class UserId {

            @SerializedName("total_reward")
            @Expose
            private String totalReward;

            @SerializedName("_id")
            @Expose
            private String id;

            public void setTotalReward(String totalReward) {
                this.totalReward = totalReward;
            }

            public String getTotalReward() {
                return totalReward;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getId() {
                return id;
            }

            @Override
            public String toString() {
                return
                        "UserId{" +
                                "total_reward = '" + totalReward + '\'' +
                                ",_id = '" + id + '\'' +
                                "}";
            }
        }

    }
}