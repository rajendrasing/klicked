package com.dating.klicked.SharedPrefernce;

public class User_Data {
    String id, email, Token, referral_code, UserName, PhoneNo, DOB, Gender, ProfileImage,Occupation;


    public User_Data(String id, String email, String token, String referral_code, String userName, String phoneNo, String DOB, String gender, String ProfileImage , String Occupation) {
        this.id = id;
        this.email = email;
        this.Token = token;
        this.referral_code = referral_code;
        this.UserName = userName;
        this.PhoneNo = phoneNo;
        this.DOB = DOB;
        this.Gender = gender;
        this.ProfileImage = ProfileImage;
        this.Occupation = Occupation;
    }

    public User_Data() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getReferral_code() {
        return referral_code;
    }

    public void setReferral_code(String referral_code) {
        this.referral_code = referral_code;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }
}
