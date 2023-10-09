package com.dating.klicked.Model.ResponseRepo;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UserChatResponse {

    @SerializedName("receiverName")
    @Expose
    private String receiverName;
    @SerializedName("array")
    @Expose
    private List<Array> array = null;

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public List<Array> getArray() {
        return array;
    }

    public void setArray(List<Array> array) {
        this.array = array;
    }
    public static class Array {

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

        public Array() {
        }

        public Array(String role, String userId, String outgoingMessage, Boolean read, String id, String updatedAt, String createdAt) {
            this.role = role;
            this.userId = userId;
            this.outgoingMessage = outgoingMessage;
            this.read = read;
            this.id = id;
            this.updatedAt = updatedAt;
            this.createdAt = createdAt;
        }

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

        @Override
        public String toString() {
            return "Array{" +
                    "role='" + role + '\'' +
                    ", userId='" + userId + '\'' +
                    ", outgoingMessage='" + outgoingMessage + '\'' +
                    ", read=" + read +
                    ", id='" + id + '\'' +
                    ", updatedAt='" + updatedAt + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    '}';
        }
    }
}