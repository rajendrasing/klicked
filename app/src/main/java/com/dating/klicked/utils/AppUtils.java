package com.dating.klicked.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.dating.klicked.Api.ApiService;
import com.dating.klicked.Api.CountryCodeApiManager;
import com.dating.klicked.Api.KlickedManager;
import com.dating.klicked.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class AppUtils {
    public static final String DATE_PATTERN_SERVICE = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static final int PERMISSION_REQUEST_CODE = 200;
    public TextView titleTextView, descriptionTextView, okButton, cancelButton, btn_txt;
    public ImageView imageView;
    public LinearLayout txt_ready;
    private boolean cancelable;
    private static final float BITMAP_SCALE = 0.4f;
    //  private static final float BLUR_RADIUS = 7.5f;

    private Dialog dialog;

    public static ApiService PostmanApi(Context context) {
        return CountryCodeApiManager.getRetrofit(context).create(ApiService.class);
    }


    public static ApiService KlickedApi(Context context) {
        return KlickedManager.getRetrofit(context).create(ApiService.class);
    }

    public static Dialog hideShowProgress(Context context) {
        Dialog dialog = new Dialog(context);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.progress_dialog, null);
        dialog.setContentView(dialogView);
        dialog.setCancelable(false);

        return dialog;
    }

    public static void shareApp(Context context, String myReferral_code) {

        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String sAux = "Hey,\n \n" + "Its amazing install Klicked   \n Referral code : " + myReferral_code + "\n Download " + context.getResources().getString(R.string.app_name) + "\n" + "Available for Android & iPhone " + "\n";
            sAux = sAux + "For Android : https://play.google.com/store/apps/details?id=" + context.getPackageName() + "\n" /*+"For iPhone : https://apps.apple.com/be/app/imx/id1558636368"+"\n"*/;
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            context.startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void shareUserProfile(Context context, String userId) {

        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String sAux = "Hey I found this profile best suited with you, check it out \n" + "for android  https://www.klicked.co/UserProfile/" + userId +"\n for ios deeplink://klicked_userprofile=/"+ userId;
            i.putExtra(Intent.EXTRA_TEXT, sAux);
           context.startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean checkAndRequestPermissions(Activity context) {
        if (context != null) {

            int storagePermission = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            int storageWritePermission = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

            int permissionCamera = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CAMERA);

            int RecordAudioPermission = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.RECORD_AUDIO);
            int WriteSettingPermission = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_SETTINGS);
            List<String> listPermissionsNeeded = new ArrayList<>();
            if (storagePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (storageWritePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA);
            }


            if (RecordAudioPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
            }

            if (WriteSettingPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_SETTINGS);
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(context,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_REQUEST_CODE);
                return false;
            }
        } else {
            return false;
        }

        return true;
    }


    public static void hideKeyboard(View view, Context context) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }

    public static void FullScreen(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }


    public void showFAQLogoutDialog(String title, String description, String btn_text, Drawable drawable) {
        this.dialog.setContentView(R.layout.custom_faq_dialog);
        imageView = dialog.findViewById(R.id.image);
        titleTextView = dialog.findViewById(R.id.txt_title);
        descriptionTextView = dialog.findViewById(R.id.txt_des);
        btn_txt = dialog.findViewById(R.id.btn_txt);
        txt_ready = dialog.findViewById(R.id.txt_ready);
        descriptionTextView.setMovementMethod(new ScrollingMovementMethod());

        titleTextView.setText(title);
        descriptionTextView.setText(description);
        btn_txt.setText(btn_text);
        imageView.setImageDrawable(drawable);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setAttributes(getLayoutParams(dialog));
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCancelable(cancelable);
        }
    }


    public void showAppLogoutDialog(String title, String description, String okText, String cancelText) {
        this.dialog.setContentView(R.layout.custom_dialog_box);
        titleTextView = dialog.findViewById(R.id.title);
        descriptionTextView = dialog.findViewById(R.id.txt_des);
        cancelButton = dialog.findViewById(R.id.txt_cancel);
        okButton = dialog.findViewById(R.id.txt_ok);
        descriptionTextView.setMovementMethod(new ScrollingMovementMethod());

        titleTextView.setText(title);
        descriptionTextView.setText(description);
        okButton.setText(okText);
        cancelButton.setText(cancelText);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setAttributes(getLayoutParams(dialog));
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCancelable(cancelable);
            cancelButton.setOnClickListener(view -> AppUtils.this.cancel());
        }
    }


    public AppUtils(Activity activity, Boolean cancelable) {

        this.dialog = new Dialog(activity);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.cancelable = cancelable;
    }


    public void show() {
        if (dialog != null) {
            this.dialog.show();
        }
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void cancel() {
        if (dialog != null) {
            this.dialog.dismiss();
        }
    }

    private WindowManager.LayoutParams getLayoutParams(@NonNull Dialog dialog) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        if (dialog.getWindow() != null) {
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
        }
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        return layoutParams;
    }

    public static String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }


    public static String getDateTime(String timeStamp) {
        String dateTime = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(timeStamp);

            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
            sdf2.setTimeZone(TimeZone.getDefault());
            dateTime = sdf2.format(date);
            System.out.println("ssss" + dateTime);
            return dateTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("ssss" + dateTime);

        return dateTime;
    }

    public static String getDateTime1(String timeStamp, String pattern) {
        String dateTime = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(timeStamp);

            SimpleDateFormat sdf2 = new SimpleDateFormat(pattern);
            sdf2.setTimeZone(TimeZone.getDefault());
            dateTime = sdf2.format(date);
            System.out.println("ssss" + dateTime);
            return dateTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("ssss" + dateTime);

        return dateTime;
    }

    public static String getCurrentTime(String date) {
        String date1 = "";
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dates = sdf.format(today);

        if (date.equalsIgnoreCase(dates)) {
            date1 = "Last Seen today " + getDateTime(date);
        } else {
            date1 = "Last Seen " + getDateTime1(date, "EEE, d MMM yyyy");
        }

        return date1;
    }

    public static String getCurrentDate() {
        String date1 = "";
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date1 = dateFormat.format(calendar.getTime());

        return date1;
    }


    public static Boolean getCompareDate(String date) {
        Date Date1, Date2;
        long millse, mills;
        Boolean date1 = false;

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String currentDates = sdf.format(today);
        //String beforeDates = sdf.format(date);

        try {


            Date1 = sdf.parse(date);
            Date2 = sdf.parse(currentDates);


            millse = Date1.getTime() - Date2.getTime();
            mills = Math.abs(millse);


            long hh = (mills / (1000 * 60 * 60));
            long Mins = (int) (mills / (1000 * 60)) % 60;
            long Secs = (int) (mills / 1000) % 60;
            long timeDifDays = mills / (24 * 60 * 60 * 1000);

            if (hh >= 24) {

                date1 = true;
            }
        } catch (Exception e) {

        }


        return date1;
    }


    public static void showMessageOKCancel(String message, Activity activity, DialogInterface.OnClickListener okListener) {
        new android.app.AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OKAY", okListener)
                .create()
                .show();
    }


    public static Bitmap blur(Context context, Bitmap image, Float BLUR_RADIUS) {
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

    public static String centimeterToFeet(String centemeter) {
        int feetPart = 0;
        int inchesPart = 0;
        if (!TextUtils.isEmpty(centemeter)) {
            double dCentimeter = Double.valueOf(centemeter);
            feetPart = (int) Math.floor((dCentimeter / 2.54) / 12);
            System.out.println((dCentimeter / 2.54) - (feetPart * 12));
            inchesPart = (int) Math.ceil((dCentimeter / 2.54) - (feetPart * 12));
        }
        return String.format("%d' %d''", feetPart, inchesPart);
    }
}
