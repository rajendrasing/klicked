package com.dating.klicked.Api;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryCodeApiManager {

    private static final String BASE_URl = "https://topups-sandbox.reloadly.com/";

    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient;
    private static int REQUEST_TIMEOUT = 30;
    private static String token;

    public static Retrofit getRetrofit(Context context) {
      //  token = MyPreferences.getInstance(context).getString(PrefConf.KEY_LOGIN_TOKEN, "");
        if (okHttpClient == null)


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URl)
                    .addConverterFactory(GsonConverterFactory.create())
                                       .build();
        }
        return retrofit;
    }


}
