package com.dating.klicked.Fragment.Profile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.dating.klicked.Authentication.Adapter.StateListAdapter;
import com.dating.klicked.Authentication.EnterOtp;
import com.dating.klicked.Authentication.SubCardAdapter;
import com.dating.klicked.Authentication.View_Flipper;
import com.dating.klicked.Fragment.Adapter.AboutOrientationAdapter;
import com.dating.klicked.Fragment.Adapter.SpecificFeaturesAdapter;
import com.dating.klicked.Model.CountryModel;
import com.dating.klicked.Model.RequestRepo.UpdateProfile;
import com.dating.klicked.Model.ResponseRepo.CardResponse;
import com.dating.klicked.Model.ResponseRepo.UserProfileResponse;
import com.dating.klicked.Model.SelectedSexualOrientationModel;
import com.dating.klicked.R;
import com.dating.klicked.SharedPrefernce.SharedPrefManager;
import com.dating.klicked.SharedPrefernce.User_Data;
import com.dating.klicked.ViewPresenter.AboutPresenter;
import com.dating.klicked.databinding.FragmentAboutBinding;
import com.dating.klicked.utils.AppUtils;
import com.dating.klicked.utils.GridSpacingItemDecoration;
import com.irozon.sneaker.Sneaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.ResponseBody;

public class About extends Fragment implements AboutPresenter.AboutView, DatePickerDialog.OnDateSetListener, View.OnClickListener, AboutOrientationAdapter.onAboutOrientationClick {
    FragmentAboutBinding binding;
    private Dialog dialog, dialogBox;
    private View view;
    private AboutPresenter aboutPresenter;
    private String[] zodiacSignData, ganderData;
    String zodiacName, ganderName, CountryName, State, City, CountryCode, age;
    CountryModel countryModel;
    List<String> orientation = new ArrayList<>();
    UserProfileResponse userProfileResponses;
    ArrayList<SelectedSexualOrientationModel> orientationModels;
    User_Data userData;

    public About() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_about, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());
        userData = SharedPrefManager.getInstance(getContext()).getLoginDATA();

        orientationModels = new ArrayList<SelectedSexualOrientationModel>();
        orientationModels.clear();
        aboutPresenter = new AboutPresenter(this);

        aboutPresenter.GetProfile(getContext());

        binding.edDateofBirth.setOnClickListener(this);
        binding.txtChange.setOnClickListener(this);

        return binding.getRoot();
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
        Sneaker.with(getActivity())
                .setTitle(message)
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();

    }

    @Override
    public void onGetProfileSuccess(UserProfileResponse userProfileResponse, String message) {

        orientation.clear();
        orientation.add("Straight");
        orientation.add("Gay");
        orientation.add("Lesbian");
        orientation.add("Bisexual");
        orientation.add("Asexual");
        orientation.add("Demisexual");
        orientation.add("Pansexual");
        orientation.add("Queer");
        orientation.add("Bicurious");


        if (message.equalsIgnoreCase("ok")) {
            userProfileResponses = userProfileResponse;
            binding.edName.setText(userProfileResponse.getFirstName());
            binding.edBio.setText(userProfileResponse.getBio());
            binding.edOccuption.setText(userProfileResponse.getOccupation());
            binding.edDateofBirth.setText(userProfileResponse.getDob());

            if (userProfileResponse.getZodiacSign() != null) {
                Zodiac_Sign(userProfileResponse.getZodiacSign());
            } else {
                Zodiac_Sign("Select Zodiac Sign");
            }

            if (userProfileResponse.getGender() != null) {
                if (userProfileResponse.getGender().equalsIgnoreCase("Female")) {
                    Gender("Women");
                } else if (userProfileResponse.getGender().equalsIgnoreCase("male")) {
                    Gender("Men");
                } else {
                    Gender(userProfileResponse.getGender());
                }

            } else {
                Gender("Select Gender");
            }

            if (userProfileResponse.getAddress().size() > 0) {
                CountryName = userProfileResponse.getAddress().get(0).getCountry();
                State = userProfileResponse.getAddress().get(0).getState();
                City = userProfileResponse.getAddress().get(0).getCity();
                CountryCode = userProfileResponse.getAddress().get(0).getCountryCode();
                aboutPresenter.getCountry(getContext());
            } else {
                CountryName = "India";
                State = "Select State";
                City = "Select City";
                CountryCode = "+91";
                aboutPresenter.getCountry(getContext());
            }


            if (userProfileResponse.getOrientation().size() > 0) {
                for (int i = 0; i < userProfileResponse.getOrientation().size(); i++) {
                    SelectedSexualOrientationModel orientationModel = new SelectedSexualOrientationModel();
                    orientationModel.setName(userProfileResponse.getOrientation().get(i));
                    orientationModel.setChecked(true);
                    orientationModels.add(i, orientationModel);
                }

                orientation.removeAll(userProfileResponse.getOrientation());

                if (orientation.size() > 0) {
                    for (int i = 0; i < orientation.size(); i++) {
                        SelectedSexualOrientationModel orientationModel = new SelectedSexualOrientationModel();
                        orientationModel.setName(orientation.get(i));
                        orientationModel.setChecked(false);
                        orientationModels.add(userProfileResponse.getOrientation().size() + i, orientationModel);
                    }

                }


            } else {
                for (int i = 0; i < orientation.size(); i++) {
                    SelectedSexualOrientationModel orientationModel = new SelectedSexualOrientationModel();
                    orientationModel.setName(orientation.get(i));
                    orientationModel.setChecked(false);
                    orientationModels.add(orientationModel);
                }
            }

            Log.d("datatatat", orientationModels.toString());


            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
            int spanCount = 3; // 3 columns
            int spacing = 5; // 50px
            boolean includeEdge = false;
            binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
            binding.recyclerView.setHasFixedSize(true);
            binding.recyclerView.setLayoutManager(layoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setAdapter(new AboutOrientationAdapter(getContext(), orientationModels, this));


        }
    }

    @Override
    public void onCountrySuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            try {
                String response = responseBody.string();
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i <= jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    if (jsonObject2.getString("name").equalsIgnoreCase(CountryName)) {
                        aboutPresenter.getState(getContext(), jsonObject2.getString("id"));
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

            StateListAdapter countrySpinnerAdapter = new StateListAdapter(getContext(), countryModels);
            binding.spinnerState.setAdapter(countrySpinnerAdapter);
            for (int i = 0; i < countryModels.size(); i++) {
                if (State.equalsIgnoreCase(countryModels.get(i).getName())) {
                    binding.spinnerState.setSelection(i);
                }
            }

            binding.spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (!countryModels.get(position).getId().equalsIgnoreCase("00")) {
                        aboutPresenter.getCity(getContext(), countryModels.get(position).getId());
                        State = countryModels.get(position).getName();

                    } else {
                        State = countryModels.get(position).getName();

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

            StateListAdapter countrySpinnerAdapter = new StateListAdapter(getContext(), countryModels);
            binding.spinnerCity.setAdapter(countrySpinnerAdapter);
            if (City != null) {
                for (int i = 0; i < countryModels.size(); i++) {
                    if (City.equalsIgnoreCase(countryModels.get(i).getName())) {
                        binding.spinnerCity.setSelection(i);
                    }
                }
            }
            binding.spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (position == 0) {
                        City = null;
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
    public void onUpdateProfileSuccess(ResponseBody responseBody, String message) {
        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(getActivity())
                    .setTitle("SuccessFully Updated")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakSuccess();
            User_Data user_data = new User_Data(userData.getId(),
                    userProfileResponses.getEmail(),
                    userData.getToken(),
                    userData.getReferral_code(),
                    binding.edName.getText().toString().trim(),
                    userProfileResponses.getPhone(),
                    binding.edDateofBirth.getText().toString(),
                    ganderName,
                    userData.getProfileImage(),
                    binding.edOccuption.getText().toString());
            SharedPrefManager.getInstance(getContext()).SetLoginData(user_data);

        }
    }

    @Override
    public void onFailure(Throwable t) {
        Sneaker.with(getActivity())
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .setCornerRadius(4)
                .setDuration(1500)
                .sneakError();

    }

    private void Zodiac_Sign(String ZodiacSign) {
        zodiacSignData = getContext().getResources().getStringArray(R.array.zodiac_signs);
        ArrayAdapter aa = new ArrayAdapter(getContext(), R.layout.custom_about_spenner_layout, zodiacSignData);
        binding.spinnerZodiacSign.setAdapter(aa);
        for (int i = 0; i < zodiacSignData.length; i++) {
            if (ZodiacSign.equalsIgnoreCase(zodiacSignData[i])) {
                binding.spinnerZodiacSign.setSelection(i);
            }
        }


        binding.spinnerZodiacSign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                zodiacName = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

    }

    private void showPickerDialog(int day, int month, int year) {

        DatePickerDialog dtPickerDlg = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Dialog, this, year, month, day);
        dtPickerDlg.getDatePicker().setSpinnersShown(true);
        dtPickerDlg.getDatePicker().setCalendarViewShown(false);
        dtPickerDlg.setTitle("Select Date Of Birth");
        dtPickerDlg.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
       /* Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, i);
        c.set(Calendar.MONTH, i1);
        c.set(Calendar.DAY_OF_MONTH, i2);
        String mDate = DateFormat.format("dd/MM/yyyy", c).toString();
        binding.edDateofBirth.setText(mDate);*/

        Calendar userAge = new GregorianCalendar(i, i1, i2);
        Calendar minAdultAge = new GregorianCalendar();
        minAdultAge.add(Calendar.YEAR, -18);
        if (minAdultAge.before(userAge)) {
            // Toast.makeText(getContext(), "18 below", Toast.LENGTH_SHORT).show();
            Sneaker.with(getActivity())
                    .setTitle("18 below")
                    .setMessage("")
                    .setCornerRadius(4)
                    .setDuration(1500)
                    .sneakWarning();
        } else {
            String mDate = DateFormat.format("dd/MM/yyyy", userAge).toString();
            binding.edDateofBirth.setText(mDate);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ed_dateofBirth:
                String date = binding.edDateofBirth.getText().toString();
                if (date != null) {
                    String[] separated = date.split("/");
                    showPickerDialog(Integer.parseInt(separated[0]), Integer.parseInt(separated[1]) - 1, Integer.parseInt(separated[2]));

                } else {
                    showPickerDialog(01, 01, 2000);
                }

                break;

            case R.id.txt_change:
                if (!binding.edDateofBirth.getText().toString().equalsIgnoreCase("")) {
                    String[] separated = binding.edDateofBirth.getText().toString().split("/");
                    age = AppUtils.getAge(Integer.parseInt(separated[2]), Integer.parseInt(separated[1]), Integer.parseInt(separated[0]));
                } else {
                    age = null;
                }
                AboutOrientationAdapter.selectedList.clear();
                for (int i = 0; i < orientationModels.size(); i++) {
                    if (orientationModels.get(i).isChecked() == true) {
                        AboutOrientationAdapter.selectedList.add(orientationModels.get(i).getName());

                    }
                }

                List<UpdateProfile.Address> addresses = new ArrayList<UpdateProfile.Address>();
                UpdateProfile.Address updateProfile = new UpdateProfile.Address(City, State, CountryName, CountryCode);
                addresses.add(updateProfile);
                UpdateProfile profile = new UpdateProfile(userProfileResponses.getEmail(), binding.edName.getText().toString().trim(), ganderName, userProfileResponses.getPhone(), binding.edDateofBirth.getText().toString(), binding.edOccuption.getText().toString(), AboutOrientationAdapter.selectedList, String.valueOf(userProfileResponses.getHeight()), binding.edBio.getText().toString(), userProfileResponses.getCards(), addresses, age,userProfileResponses.getMaincards());
                aboutPresenter.UpdateUserProfile(getContext(), profile);
                //  Log.d("datatatat",AboutOrientationAdapter.selectedList.toString());
                break;
        }
    }

    private void Gender(String gender) {
        ganderData = getContext().getResources().getStringArray(R.array.gander);

        ArrayAdapter aa = new ArrayAdapter(getContext(), R.layout.custom_about_spenner_layout, ganderData);
        binding.spinnerGender.setAdapter(aa);
        for (int i = 0; i < ganderData.length; i++) {
            if (gender.equalsIgnoreCase(ganderData[i])) {
                binding.spinnerGender.setSelection(i);
            }
        }


        binding.spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ganderName = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
    }

    @Override
    public void onGetAboutOrientationData(ArrayList<SelectedSexualOrientationModel> list, int position) {


    }
}