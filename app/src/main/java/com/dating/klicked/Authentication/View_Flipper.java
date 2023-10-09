package com.dating.klicked.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dating.klicked.Authentication.Adapter.CardAdapter;
import com.dating.klicked.Authentication.Adapter.CardSelectedModel;
import com.dating.klicked.Authentication.Adapter.MainCardAdapter;
import com.dating.klicked.Authentication.Adapter.ShowCardAdapter;
import com.dating.klicked.Authentication.Adapter.ShowCardModal;
import com.dating.klicked.Authentication.Adapter.StateListAdapter;
import com.dating.klicked.Fragment.Adapter.Chat_messageAdapter;
import com.dating.klicked.Model.CountryModel;
import com.dating.klicked.Model.GetCardModel;
import com.dating.klicked.Model.RequestRepo.UpdateProfile;
import com.dating.klicked.Model.ResponseRepo.CardResponse;
import com.dating.klicked.R;
import com.dating.klicked.SharedPerfence.MyPreferences;
import com.dating.klicked.SharedPerfence.PrefConf;
import com.dating.klicked.ViewPresenter.PhoneNumber_presenter;
import com.dating.klicked.ViewPresenter.View_Flipper_Presenter;
import com.dating.klicked.databinding.ActivityViewFlipperBinding;
import com.dating.klicked.utils.AppUtils;
import com.dating.klicked.utils.SpacesItemDecoration;
import com.dating.klicked.utils.Validation;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.irozon.sneaker.Sneaker;
import com.shawnlin.numberpicker.NumberPicker;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

public class View_Flipper extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, View_Flipper_Presenter.Flipper_View, CompoundButton.OnCheckedChangeListener, MainCardAdapter.onMainCardClick, CardAdapter.onCardClick, NumberPicker.OnScrollListener {
    ActivityViewFlipperBinding binding;
    private View view;
    private Context context;
    private Dialog dialog;
    private View_Flipper_Presenter presenter;
    BottomSheetDialog bottomSheetDialog;
    String gender = null, Country, State, City, Date1, name, email, CountryCode, Occupation, phone, bio, age;
    int height;
    CountryModel countryModel;
    ArrayList<String> Orientation;
    ArrayList<GetCardModel> list = new ArrayList<GetCardModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_flipper);

        view = binding.getRoot();
        context = View_Flipper.this;
        dialog = AppUtils.hideShowProgress(context);

        presenter = new View_Flipper_Presenter(this);

        presenter.getCountry(context);
          presenter.getAllCard(context);
        CountryCode = MyPreferences.getInstance(context).getString(PrefConf.COUNTRY_CODE, null);
        phone = MyPreferences.getInstance(context).getString(PrefConf.USER_NUMBER, null);
        email = MyPreferences.getInstance(context).getString(PrefConf.User_Email, null);
        name = MyPreferences.getInstance(context).getString(PrefConf.USER_Name, null);

        if (name != null) {
            binding.userNameLayout.edName.setText(name);
        }
        Calendar cal = Calendar.getInstance();
        Log.d("ndjknc", String.valueOf(cal.get(Calendar.YEAR)));
        Orientation = new ArrayList<String>();

        binding.userNameLayout.txtNameBack.setOnClickListener(this);
        binding.userNameLayout.txtNameLets.setOnClickListener(this);
        binding.userEmailLayout.txtEmailBack.setOnClickListener(this);
        binding.userEmailLayout.txtEmailNext.setOnClickListener(this);
        binding.userDateOfBirthLayout.txtDateBack.setOnClickListener(this);
        binding.userDateOfBirthLayout.txtDateNext.setOnClickListener(this);
        binding.userGenderLayout.txtGenderBack.setOnClickListener(this);
        binding.userGenderLayout.txtGenderNext.setOnClickListener(this);
        binding.userOccupationLayout.txtOccupationBack.setOnClickListener(this);
        binding.userOccupationLayout.txtOccuptionNext.setOnClickListener(this);
        binding.userStateLayout.txtStateBack.setOnClickListener(this);
        binding.userStateLayout.txtStateNext.setOnClickListener(this);
        binding.userGenderLayout.GanderRadio.setOnCheckedChangeListener(this);
        binding.userOrientationLayout.txtOrientationBack.setOnClickListener(this);
        binding.userOrientationLayout.txtOrientationNext.setOnClickListener(this);
        binding.userHeightLayout.txtHeightBack.setOnClickListener(this);
        binding.userHeightLayout.txtHeightNext.setOnClickListener(this);
        binding.userCardLayout.txtCardBack.setOnClickListener(this);
        binding.userCardLayout.txtCardNext.setOnClickListener(this);
        binding.userOrientationLayout.CBStraight.setOnCheckedChangeListener(this);
        binding.userOrientationLayout.CBGay.setOnCheckedChangeListener(this);
        binding.userOrientationLayout.CBLesbian.setOnCheckedChangeListener(this);
        binding.userOrientationLayout.CBBisexual.setOnCheckedChangeListener(this);
        binding.userOrientationLayout.CBAsexual.setOnCheckedChangeListener(this);
        binding.userOrientationLayout.CBDemisexual.setOnCheckedChangeListener(this);
        binding.userOrientationLayout.CBPansexual.setOnCheckedChangeListener(this);
        binding.userOrientationLayout.CBQueer.setOnCheckedChangeListener(this);
        binding.userOrientationLayout.CBBicurious.setOnCheckedChangeListener(this);
        binding.userHeightLayout.numberpicker.setOnScrollListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.txt_Name_back:
                onBackPressed();
                break;

            case R.id.txt_name_lets:
                name = binding.userNameLayout.edName.getText().toString();
                bio = binding.userNameLayout.description.getText().toString();
                if (name.isEmpty() || bio.isEmpty()) {
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING")
                            .setContentText("Please Enter Your Name and About !!!!!")
                            .setConfirmText("OK")
                            .setConfirmButtonTextColor(getResources().getColor(R.color.white))
                            .setContentTextSize(14)
                            .setConfirmButtonBackgroundColor(getResources().getColor(R.color.global__primary))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                } else {
                    if (email != null) {
                        binding.userEmailLayout.edEmail.setText(email);
                    }
                    binding.viewflipper.setDisplayedChild(1);

                }
                break;


            case R.id.txt_Email_back:
                onBackPressed();
                break;


            case R.id.txt_email_next:
                email = binding.userEmailLayout.edEmail.getText().toString().trim();
                if (!email.isEmpty() && !Validation.isValidEmail(email)) {
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING")
                            .setContentText("Please Enter Valid Email !!!!!")
                            .setConfirmText("OK")
                            .setConfirmButtonTextColor(getResources().getColor(R.color.white))
                            .setContentTextSize(14)
                            .setConfirmButtonBackgroundColor(getResources().getColor(R.color.global__primary))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                } else {
                    binding.viewflipper.setDisplayedChild(2);
                    Date today = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(today);
                    c.add(Calendar.YEAR, -15);// Subtract 18 year
                    long maxDate = c.getTime().getTime(); // Twice!
                    binding.userDateOfBirthLayout.datePicker.setMaxDate(maxDate);


                }

                break;

            case R.id.txt_date_back:
                onBackPressed();
                break;


            case R.id.txt_date_next:


                if (binding.userDateOfBirthLayout.datePicker.getMonth() < 10) {
                    Date1 = binding.userDateOfBirthLayout.datePicker.getDayOfMonth() + "/0" + (binding.userDateOfBirthLayout.datePicker.getMonth() + 1) + "/" + binding.userDateOfBirthLayout.datePicker.getYear();

                } else {
                    Date1 = binding.userDateOfBirthLayout.datePicker.getDayOfMonth() + "/" + (binding.userDateOfBirthLayout.datePicker.getMonth() + 1) + "/" + binding.userDateOfBirthLayout.datePicker.getYear();

                }
                age = AppUtils.getAge(binding.userDateOfBirthLayout.datePicker.getYear(), binding.userDateOfBirthLayout.datePicker.getMonth() + 1, binding.userDateOfBirthLayout.datePicker.getDayOfMonth());

                Log.d("Date", Date1 + age);
                binding.viewflipper.setDisplayedChild(3);

                break;


            case R.id.txt_Gender_back:
                onBackPressed();
                break;

            case R.id.txt_gender_next:
                if (gender == null) {
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING")
                            .setContentText("Please Select Gender !!!!!")
                            .setConfirmText("OK")
                            .setConfirmButtonTextColor(getResources().getColor(R.color.white))
                            .setContentTextSize(14)
                            .setConfirmButtonBackgroundColor(getResources().getColor(R.color.global__primary))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                } else {
                    binding.viewflipper.setDisplayedChild(4);

                }
                break;

            case R.id.txt_Occupation_back:
                onBackPressed();
                break;

            case R.id.txt_occuption_next:
                Occupation = binding.userOccupationLayout.edOccuption.getText().toString();
                if (Occupation.isEmpty()) {
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING")
                            .setContentText("Please Enter Your  Occupation  !!!!!")
                            .setConfirmText("OK")
                            .setConfirmButtonTextColor(getResources().getColor(R.color.white))
                            .setContentTextSize(14)
                            .setConfirmButtonBackgroundColor(getResources().getColor(R.color.global__primary))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                } else {
                  //  presenter.getCountry(context);

                    binding.viewflipper.setDisplayedChild(5);

                }
                break;

            case R.id.txt_State_back:
                onBackPressed();
                break;

            case R.id.txt_State_next:
                if (State.equalsIgnoreCase("Select State")) {
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING")
                            .setContentText("Please Select State  !!!!!")
                            .setConfirmText("OK")
                            .setConfirmButtonTextColor(getResources().getColor(R.color.white))
                            .setContentTextSize(14)
                            .setConfirmButtonBackgroundColor(getResources().getColor(R.color.global__primary))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                } else if (City != null && City.equalsIgnoreCase("Select City")) {
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING")
                            .setContentText("Please Select City  !!!!!")
                            .setConfirmText("OK")
                            .setConfirmButtonTextColor(getResources().getColor(R.color.white))
                            .setContentTextSize(14)
                            .setConfirmButtonBackgroundColor(getResources().getColor(R.color.global__primary))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                } else {
                    binding.viewflipper.setDisplayedChild(6);

                }
                break;

            case R.id.txt_Orientation_back:
                onBackPressed();
                break;

            case R.id.txt_Orientation_next:
                if (Orientation.size() <= 0) {
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING")
                            .setContentText("Please Select Orientations !!!!!")
                            .setConfirmText("OK")
                            .setConfirmButtonTextColor(getResources().getColor(R.color.white))
                            .setContentTextSize(14)
                            .setConfirmButtonBackgroundColor(getResources().getColor(R.color.global__primary))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                } else {
                    binding.viewflipper.setDisplayedChild(7);


                }

                break;


            case R.id.txt_height_back:
                onBackPressed();

                break;
            case R.id.txt_height_next:
                height = binding.userHeightLayout.numberpicker.getValue();
                if (height < 30) {
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("WARNING")
                            .setContentText("Please Select Height !!!!!")
                            .setConfirmText("OK")
                            .setConfirmButtonTextColor(getResources().getColor(R.color.white))
                            .setContentTextSize(14)
                            .setConfirmButtonBackgroundColor(getResources().getColor(R.color.global__primary))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                } else {
                    binding.viewflipper.setDisplayedChild(8);
                    presenter.getAllCard(context);
                }

                break;

            case R.id.txt_card_back:
                onBackPressed();
                break;

            case R.id.txt_card_next:
                if (CardAdapter.subCardName.size() > 0) {
                    Log.d("INFOOO", name + email + Date1 + gender + State + City + CountryCode + Orientation + CardAdapter.subCardName.toString() + phone);

                    List<UpdateProfile.Address> addresses = new ArrayList<UpdateProfile.Address>();
                    UpdateProfile.Address updateProfile = new UpdateProfile.Address(City, State, Country, CountryCode);
                    addresses.add(updateProfile);
                    UpdateProfile profile = new UpdateProfile(email, name, gender, phone, Date1, Occupation, Orientation, String.valueOf(height), bio, CardAdapter.subCardName, addresses, age,CardAdapter.CardId);
                    presenter.UpdateUserProfile(context, profile);
                } else {
                    Sneaker.with(View_Flipper.this)
                            .setTitle("Please select your cards")
                            .setMessage("")
                            .setCornerRadius(4)
                            .setDuration(1500)
                            .sneakWarning();
                }
                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (binding.viewflipper.getDisplayedChild() == 0) {
            super.onBackPressed();
            onSaveInstanceState(new Bundle());

        } else if (binding.viewflipper.getDisplayedChild() == 1) {
            binding.viewflipper.setDisplayedChild(0);

        } else if (binding.viewflipper.getDisplayedChild() == 2) {
            binding.viewflipper.setDisplayedChild(1);

        } else if (binding.viewflipper.getDisplayedChild() == 3) {
            binding.viewflipper.setDisplayedChild(2);

        } else if (binding.viewflipper.getDisplayedChild() == 4) {
            binding.viewflipper.setDisplayedChild(3);

        } else if (binding.viewflipper.getDisplayedChild() == 5) {
            binding.viewflipper.setDisplayedChild(4);

        } else if (binding.viewflipper.getDisplayedChild() == 6) {
            binding.viewflipper.setDisplayedChild(5);

        } else if (binding.viewflipper.getDisplayedChild() == 7) {
            binding.viewflipper.setDisplayedChild(6);

        } else if (binding.viewflipper.getDisplayedChild() == 8) {
            binding.viewflipper.setDisplayedChild(7);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void Gender_Bottom_Sheet() {

        bottomSheetDialog = new BottomSheetDialog(View_Flipper.this);
        bottomSheetDialog.setContentView(R.layout.gander_bottom_sheets);

        RadioGroup radioGroup = bottomSheetDialog.findViewById(R.id.radioGroup1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton) bottomSheetDialog.findViewById(radioGroup.getCheckedRadioButtonId());

                gender = rb.getText().toString();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bottomSheetDialog.dismiss();
                    }
                }, 500);

            }
        });
        bottomSheetDialog.show();
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rd_women:
                gender = "Women";
                break;

            case R.id.rd_men:
                gender = "Men";
                break;
            case R.id.rd_other:
                Gender_Bottom_Sheet();
                break;
        }
    }

    @Override
    public void showHideProgress(boolean isShow) {
        if (isShow) {
            dialog.show();
        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    dialog.dismiss();
                }


            }, 1000);

        }
    }

    @Override
    public void onError(String message) {
        Sneaker.with(View_Flipper.this)
                .setTitle(message)
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }

    @Override
    public void onCountrySuccess(ResponseBody responseBody, String message) {

        Country = MyPreferences.getInstance(View_Flipper.this).getString(PrefConf.COUNTRY_NAME, "india");

        if (message.equalsIgnoreCase("ok")) {
            try {
                String response = responseBody.string();
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i <= jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    if (jsonObject2.getString("name").equalsIgnoreCase(Country)) {
                        presenter.getState(context, jsonObject2.getString("id"));
                    }
                }


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onStateSuccess(ResponseBody responseBody, String message) {
        ArrayList<CountryModel> countryModels = new ArrayList<CountryModel>();
        countryModel = new CountryModel();

        if (message.equalsIgnoreCase("ok")) {
            countryModels.clear();
            try {
                countryModel.setName("Select State");
                countryModel.setId("00");
                countryModels.add(countryModel);
                String response = responseBody.string();
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i <= jsonArray.length(); i++) {
                    CountryModel countryModel = new CountryModel();
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    countryModel.setId(jsonObject2.getString("id"));
                    countryModel.setName(jsonObject2.getString("name"));
                    countryModels.add(countryModel);
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            StateListAdapter countrySpinnerAdapter = new StateListAdapter(context, countryModels);
            binding.userStateLayout.spinnerState.setAdapter(countrySpinnerAdapter);
            binding.userStateLayout.spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (!countryModels.get(position).getId().equalsIgnoreCase("00")) {
                        presenter.getCity(View_Flipper.this, countryModels.get(position).getId());
                        State = countryModels.get(position).getName();
                        binding.userStateLayout.linearCity.setVisibility(View.VISIBLE);

                    } else {
                        State = countryModels.get(position).getName();
                        binding.userStateLayout.linearCity.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }

    @Override
    public void onCitySuccess(ResponseBody responseBody, String message) {
        ArrayList<CountryModel> countryModels = new ArrayList<CountryModel>();
        countryModel = new CountryModel();

        if (message.equalsIgnoreCase("ok")) {
            countryModels.clear();
            try {
                countryModel.setName("Select City");
                countryModel.setId("00");
                countryModels.add(countryModel);
                String response = responseBody.string();
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i <= jsonArray.length(); i++) {
                    CountryModel countryModel = new CountryModel();
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    countryModel.setId(jsonObject2.getString("id"));
                    countryModel.setName(jsonObject2.getString("name"));
                    countryModels.add(countryModel);
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            StateListAdapter countrySpinnerAdapter = new StateListAdapter(context, countryModels);
            binding.userStateLayout.spinnerCity.setAdapter(countrySpinnerAdapter);
            binding.userStateLayout.spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (position == 0) {
                        City = "Select City";
                    } else {
                        City = countryModels.get(position).getName();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }

    @Override
    public void onGetCardSuccess(List<CardResponse> cardResponses, String message) {
        list.clear();
        if (message.equalsIgnoreCase("ok")) {

           /* for (int i = 0; i < cardResponses.size(); i++) {

                presenter.getAllSubCard(context, cardResponses.get(i).getName());
            }*/

            CardAdapter cardAdapter = new CardAdapter(cardResponses, View_Flipper.this, this,false);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(View_Flipper.this, 3, LinearLayoutManager.VERTICAL, false);
            binding.userCardLayout.cardRecycler.setLayoutManager(layoutManager);
            //  int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
            binding.userCardLayout.cardRecycler.addItemDecoration(new SpacesItemDecoration(30));
            binding.userCardLayout.cardRecycler.setItemAnimator(new DefaultItemAnimator());
            binding.userCardLayout.cardRecycler.setHasFixedSize(true);
            binding.userCardLayout.cardRecycler.setAdapter(cardAdapter);

        }
    }

    @Override
    public void onGetSubCardSuccess(List<CardResponse> cardResponses, String message, String name) {
        if (message.equalsIgnoreCase("ok")) {

            Log.d("cardResponse", String.valueOf(cardResponses.size()));

            list.add(new GetCardModel(name, cardResponses));

            MainCardAdapter cardAdapter = new MainCardAdapter(list, View_Flipper.this, this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            binding.userCardLayout.cardRecycler.setLayoutManager(layoutManager);
            binding.userCardLayout.cardRecycler.setItemAnimator(new DefaultItemAnimator());
            binding.userCardLayout.cardRecycler.setHasFixedSize(true);
            binding.userCardLayout.cardRecycler.setAdapter(cardAdapter);
        }
    }

    @Override
    public void onUpdateProfileSuccess(ResponseBody responseBody, String message) {

        if (message.equalsIgnoreCase("ok")) {
            startActivity(new Intent(View_Flipper.this, Audio_Des.class));
        }

    }

    @Override
    public void onFailure(Throwable t) {
        Sneaker.with(View_Flipper.this)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId()) {
            case R.id.CB_Straight:
                if (b == true) {
                    Orientation.add("Straight");
                } else {
                    Orientation.remove("Straight");
                }
                break;
            case R.id.CB_Gay:
                if (b == true) {
                    Orientation.add("Gay");
                } else {
                    Orientation.remove("Gay");
                }
                break;
            case R.id.CB_Lesbian:
                if (b == true) {
                    Orientation.add("Lesbian");
                } else {
                    Orientation.remove("Lesbian");
                }
                break;
            case R.id.CB_Bisexual:
                if (b == true) {
                    Orientation.add("Bisexual");
                } else {
                    Orientation.remove("Bisexual");
                }
                break;
            case R.id.CB_Asexual:
                if (b == true) {
                    Orientation.add("Asexual");
                } else {
                    Orientation.remove("Asexual");
                }
                break;
            case R.id.CB_Demisexual:
                if (b == true) {
                    Orientation.add("Demisexual");
                } else {
                    Orientation.remove("Demisexual");
                }
                break;
            case R.id.CB_Pansexual:
                if (b == true) {
                    Orientation.add("Pansexual");
                } else {
                    Orientation.remove("Pansexual");
                }
                break;
            case R.id.CB_Queer:
                if (b == true) {
                    Orientation.add("Queer");
                } else {
                    Orientation.remove("Queer");
                }
                break;
            case R.id.CB_Bicurious:
                if (b == true) {
                    Orientation.add("Bicurious");
                } else {
                    Orientation.remove("Bicurious");
                }
                break;
        }

    }

    @Override
    public void onCardClickListener(ShowCardModal cardResponses) {
       /* runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ShowCardAdapter cardAdapter = new ShowCardAdapter(SubCardAdapter.subCardName, SubCardAdapter.subCardBackImg, SubCardAdapter.subCardIcon, View_Flipper.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                linearLayoutManager.setStackFromEnd(true);
                cardAdapter.notifyDataSetChanged();
                cardAdapter.update(SubCardAdapter.subCardName, SubCardAdapter.subCardBackImg, SubCardAdapter.subCardIcon);
                binding.userCardLayout.selectedCardRecycler.setLayoutManager(linearLayoutManager);
                binding.userCardLayout.selectedCardRecycler.setHasFixedSize(true);
                binding.userCardLayout.selectedCardRecycler.setAdapter(cardAdapter);
            }

        });


        Log.d("cardName", SubCardAdapter.subCardName.toString() + " " + SubCardAdapter.subCardName.size());
        Log.d("cardName", SubCardAdapter.subCardBackImg.toString() + " " + SubCardAdapter.subCardBackImg.size());
        Log.d("cardName", SubCardAdapter.subCardIcon.toString() + " " + SubCardAdapter.subCardIcon.size());
   */

    }


    @Override
    public void onCardClickListener(ArrayList<String> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ShowCardAdapter cardAdapter = new ShowCardAdapter(CardAdapter.subCardName, CardAdapter.subCardBackImg, CardAdapter.subCardIcon, View_Flipper.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                linearLayoutManager.setStackFromEnd(true);
                cardAdapter.notifyDataSetChanged();
                cardAdapter.update(CardAdapter.subCardName, CardAdapter.subCardBackImg, CardAdapter.subCardIcon);
                binding.userCardLayout.selectedCardRecycler.setLayoutManager(linearLayoutManager);
                binding.userCardLayout.selectedCardRecycler.setHasFixedSize(true);
                binding.userCardLayout.selectedCardRecycler.setAdapter(cardAdapter);
            }
        });


        Log.d("cardName", CardAdapter.subCardName.toString() + " " + CardAdapter.subCardName.size());
        Log.d("cardName", CardAdapter.subCardBackImg.toString() + " " + CardAdapter.subCardBackImg.size());
        Log.d("cardName", CardAdapter.subCardIcon.toString() + " " + CardAdapter.subCardIcon.size());

    }


    @Override
    public void onScrollStateChange(NumberPicker view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            binding.userHeightLayout.showHieght.setText(view.getValue() + "cm  =  " + AppUtils.centimeterToFeet(String.valueOf(view.getValue())));
            //    Toast.makeText(View_Flipper.this, ""+AppUtils.centimeterToFeet(String.valueOf(view.getValue())), Toast.LENGTH_SHORT).show();
        }
    }
}