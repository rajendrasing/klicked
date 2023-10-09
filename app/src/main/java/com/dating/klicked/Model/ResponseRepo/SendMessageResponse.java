package com.dating.klicked.Model.ResponseRepo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SendMessageResponse {

    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result {

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

        @Override
        public String toString() {
            return "Result{" +
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

    @Override
    public String toString() {
        return "SendMessageResponse{" +
                "result=" + result +
                '}';
    }
}
