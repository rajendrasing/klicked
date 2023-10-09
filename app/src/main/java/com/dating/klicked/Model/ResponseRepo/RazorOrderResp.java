package com.dating.klicked.Model.ResponseRepo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RazorOrderResp {

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("allPaymentDetails")
    @Expose
    private AllPaymentDetails allPaymentDetails;
    @SerializedName("paymentStatus")
    @Expose
    private String paymentStatus;
    @SerializedName("isWalletSelected")
    @Expose
    private Boolean isWalletSelected;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("statusDetails")
    @Expose
    private List<Object> statusDetails = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public AllPaymentDetails getAllPaymentDetails() {
        return allPaymentDetails;
    }

    public void setAllPaymentDetails(AllPaymentDetails allPaymentDetails) {
        this.allPaymentDetails = allPaymentDetails;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Boolean getIsWalletSelected() {
        return isWalletSelected;
    }

    public void setIsWalletSelected(Boolean isWalletSelected) {
        this.isWalletSelected = isWalletSelected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Object> getStatusDetails() {
        return statusDetails;
    }

    public void setStatusDetails(List<Object> statusDetails) {
        this.statusDetails = statusDetails;
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
    public class AllPaymentDetails {

        @SerializedName("razorpay_order_id")
        @Expose
        private String razorpayOrderId;

        public String getRazorpayOrderId() {
            return razorpayOrderId;
        }

        public void setRazorpayOrderId(String razorpayOrderId) {
            this.razorpayOrderId = razorpayOrderId;
        }

        @Override
        public String toString() {
            return "AllPaymentDetails{" +
                    "razorpayOrderId='" + razorpayOrderId + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RazorOrderResp{" +
                "user='" + user + '\'' +
                ", allPaymentDetails=" + allPaymentDetails +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", isWalletSelected=" + isWalletSelected +
                ", id='" + id + '\'' +
                ", statusDetails=" + statusDetails +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
