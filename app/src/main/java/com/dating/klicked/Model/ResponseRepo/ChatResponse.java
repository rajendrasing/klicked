package com.dating.klicked.Model.ResponseRepo;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatResponse {
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public static class Result {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("userId")
        @Expose
        private UserId userId;
        @SerializedName("idReceiver")
        @Expose
        private IdReceiver idReceiver;

        @SerializedName("klickedMeter")
        @Expose
        private Double klickedMeter;

        @SerializedName("outgoingMessages")
        @Expose
        private List<OutgoingMessage> outgoingMessages = null;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public UserId getUserId() {
            return userId;
        }

        public void setUserId(UserId userId) {
            this.userId = userId;
        }

        public IdReceiver getIdReceiver() {
            return idReceiver;
        }

        public void setIdReceiver(IdReceiver idReceiver) {
            this.idReceiver = idReceiver;
        }


        public Double getKlickedMeter() {
            return klickedMeter;
        }

        public void setKlickedMeter(Double klickedMeter) {
            this.klickedMeter = klickedMeter;
        }

        public List<OutgoingMessage> getOutgoingMessages() {
            return outgoingMessages;
        }

        public void setOutgoingMessages(List<OutgoingMessage> outgoingMessages) {
            this.outgoingMessages = outgoingMessages;
        }

        public class UserId {

            @SerializedName("_id")
            @Expose
            private String id;
            @SerializedName("profileImage")
            @Expose
            private String profileImage;
            @SerializedName("klickedMeter")
            @Expose
            private String klickedMeter;
            @SerializedName("firstName")
            @Expose
            private String firstName;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getProfileImage() {
                return profileImage;
            }

            public void setProfileImage(String profileImage) {
                this.profileImage = profileImage;
            }

            public String getKlickedMeter() {
                return klickedMeter;
            }

            public void setKlickedMeter(String klickedMeter) {
                this.klickedMeter = klickedMeter;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }
        }

        public class IdReceiver {

            @SerializedName("_id")
            @Expose
            private String id;
            @SerializedName("profileImage")
            @Expose
            private String profileImage;
            @SerializedName("klickedMeter")
            @Expose
            private String klickedMeter;
            @SerializedName("firstName")
            @Expose
            private String firstName;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getProfileImage() {
                return profileImage;
            }

            public void setProfileImage(String profileImage) {
                this.profileImage = profileImage;
            }

            public String getKlickedMeter() {
                return klickedMeter;
            }

            public void setKlickedMeter(String klickedMeter) {
                this.klickedMeter = klickedMeter;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }
        }

        public class OutgoingMessage {

            @SerializedName("role")
            @Expose
            private String role;
            @SerializedName("userId")
            @Expose
            private String userId;
            @SerializedName("outgoingMessage")
            @Expose
            private String outgoingMessage;
            @SerializedName("read")
            @Expose
            private Boolean read;
            @SerializedName("_id")
            @Expose
            private String id;
            @SerializedName("updatedAt")
            @Expose
            private String updatedAt;
            @SerializedName("createdAt")
            @Expose
            private String createdAt;

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getOutgoingMessage() {
                return outgoingMessage;
            }

            public void setOutgoingMessage(String outgoingMessage) {
                this.outgoingMessage = outgoingMessage;
            }

            public Boolean getRead() {
                return read;
            }

            public void setRead(Boolean read) {
                this.read = read;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }

}