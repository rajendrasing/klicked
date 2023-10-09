package com.dating.klicked.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dating.klicked.MainActivity;
import com.dating.klicked.Model.RequestRepo.RazorCaptureBody;
import com.dating.klicked.Model.RequestRepo.RazorSaveDataBody;
import com.dating.klicked.Model.ResponseRepo.CapturePaymentSuccessResp;
import com.dating.klicked.Model.ResponseRepo.PaymentDataSaveResp;
import com.dating.klicked.Model.ResponseRepo.RazorOrderResp;
import com.dating.klicked.R;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.PlanPurchasePresenter;
import com.dating.klicked.databinding.ActivityPlanPurchaseBinding;
import com.dating.klicked.utils.AppUtils;
import com.google.android.material.snackbar.Snackbar;
import com.irozon.sneaker.Sneaker;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;


import org.json.JSONObject;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.exception.AppNotFoundException;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;


public class PlanPurchaseActivity extends AppCompatActivity implements PlanPurchasePresenter.PlanPurchaseView, PaymentResultWithDataListener, PaymentStatusListener {
    ActivityPlanPurchaseBinding binding;
    private View view;
    private Context context;
    private Dialog dialog;
    PlanPurchasePresenter presenter;
    private PaymentData paymentData1 = null;
    private RazorOrderResp razorOrderResp = null;
    User_Data userData;
    String price, docId, planName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_plan_purchase);

        view = binding.getRoot();
        context = PlanPurchaseActivity.this;

        presenter = new PlanPurchasePresenter(this);

        userData = SharedPrefManager.getInstance(PlanPurchaseActivity.this).getLoginDATA();

        dialog = AppUtils.hideShowProgress(context);
        price = getIntent().getStringExtra("price");
        planName = getIntent().getStringExtra("planName");
        if (price != null) {
            presenter.razorOrder(price, context);
        }

    }

    public void startPayment(String orderId) {

        Checkout checkout = new Checkout();
        checkout.setKeyID(getResources().getString(R.string.razor_api_key));
        final Activity activity = this;


        //Pass your payment options to the Razorpay Checkout as a JSONObject

        try {
            JSONObject options = new JSONObject();

            options.put("name", userData.getUserName());
            //  options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", orderId);//from response of step 3.
            //   options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", price);//pass amount in currency subunits
            options.put("prefill.email", userData.getEmail());
            options.put("prefill.contact", userData.getPhoneNo());
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("Errorrrrrr", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void showHideProgress(boolean isShow) {
        if (isShow) {
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(String message) {
        Sneaker.with(PlanPurchaseActivity.this)
                .setTitle(message)
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }

    @Override
    public void onRazorOrderSuccess(RazorOrderResp razorOrderResp, String message) {
        if (message.equalsIgnoreCase("ok")) {
            this.razorOrderResp = razorOrderResp;
            docId = razorOrderResp.getId();
            makePayment("1.00", "8766372989@paytm", "Omender Singh", "Test", docId);
            //    startPayment(razorOrderResp.getAllPaymentDetails().getRazorpayOrderId());
        }

    }

    @Override
    public void onRazorCaptureSuccess(CapturePaymentSuccessResp successResp, String message) {
        if (message.equalsIgnoreCase("ok")) {
            if (paymentData1 != null) {
                RazorSaveDataBody dataBody = new RazorSaveDataBody(paymentData1.getOrderId(), paymentData1.getPaymentId()
                        , paymentData1.getSignature(), successResp.getAmount(), successResp.getMethod(), successResp.getStatus(), "INR", planName);


                presenter.savePaymentData(docId, dataBody, context);
            } else {
                String str = " null";
                System.out.println("paymentData1  " + str);
            }
        }

    }

    @Override
    public void onRazorDataSaved(PaymentDataSaveResp responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            AppUtils.showMessageOKCancel("Payment Success ", this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }

    @Override
    public void onFailure(Throwable t) {
        Sneaker.with(PlanPurchaseActivity.this)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        this.paymentData1 = paymentData;
        // System.out.println("suus order " + orderResp.getResult().getId());
        System.out.println("suus " + s + " " + paymentData.getExternalWallet() + " " + paymentData.getOrderId() + " pid " + paymentData.getPaymentId() + " sig " + paymentData.getSignature() + " " + paymentData.getUserContact() + " " + paymentData.getUserEmail());

        Toast.makeText(context, "Payment successful " + s, Toast.LENGTH_SHORT).show();
        RazorCaptureBody razorCaptureBody = new RazorCaptureBody(Integer.parseInt(price), paymentData.getOrderId(),
                paymentData.getPaymentId(), paymentData.getSignature(), "INR");
        presenter.razorCapture(razorCaptureBody, context);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Snackbar.make(view, s, Snackbar.LENGTH_SHORT).show();
    }

    private void makePayment(String amount, String upi, String name, String desc, String transactionId) {
        // on below line we are calling an easy payment method and passing
        // all parameters to it such as upi id,name, description and others.


        EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(this)
                .setPayeeVpa("paytmqr2810050501011ov9aocs6i5c@paytm")
                .setPayeeName("Omender Singh")
                .setPayeeMerchantCode("0011")
                .setTransactionId("T2020090212343")
                .setTransactionRefId("T2020090212343")
                .setDescription("Description")
                .setAmount("1.00");

        try {
            EasyUpiPayment easyUpiPayment = builder.build();
            easyUpiPayment.startPayment();
            easyUpiPayment.setPaymentStatusListener(this);
        } catch (AppNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onTransactionCancelled() {
        toast("Cancelled by user");
    }

    @Override
    public void onTransactionCompleted(@NonNull TransactionDetails transactionDetails) {
        // Transaction Completed
        Log.d("TransactionDetails", transactionDetails.toString());
        //statusView.setText(transactionDetails.toString());

        switch (transactionDetails.getTransactionStatus()) {
            case SUCCESS:
                onTransactionSuccess();
                break;
            case FAILURE:
                onTransactionFailed();
                break;
            case SUBMITTED:
                onTransactionSubmitted();
                break;
        }
    }

    private void onTransactionSuccess() {
        // Payment Success
        toast("Success");
        //  imageView.setImageResource(R.drawable.ic_success);
    }

    private void onTransactionSubmitted() {
        // Payment Pending
        toast("Pending | Submitted");
        // imageView.setImageResource(R.drawable.ic_success);
    }

    private void onTransactionFailed() {
        // Payment Failed
        toast("Failed");
        //    imageView.setImageResource(R.drawable.ic_failed);
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}