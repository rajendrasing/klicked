package com.dating.klicked.Model.RequestRepo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RazorSaveDataBody {
    @SerializedName("razorpay_order_id")
    @Expose
    private String razorpayOrderId;
    @SerializedName("razorpay_payment_id")
    @Expose
    private String razorpayPaymentId;
    @SerializedName("razorpay_signature")
    @Expose
    private String razorpaySignature;
    @SerializedName("payedAmount")
    @Expose
    private Integer payedAmount;
    @SerializedName("paymentType")
    @Expose
    private String paymentType;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("planName")
    @Expose
    private String planName;


    public RazorSaveDataBody(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature, Integer payedAmount, String paymentType, String status, String currency, String planName) {
        this.razorpayOrderId = razorpayOrderId;
        this.razorpayPaymentId = razorpayPaymentId;
        this.razorpaySignature = razorpaySignature;
        this.payedAmount = payedAmount;
        this.paymentType = paymentType;
        this.status = status;
        this.currency = currency;
        this.planName = planName;
    }
}
