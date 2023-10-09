package com.dating.klicked.Model.ResponseRepo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileResponse {
    @SerializedName("prime")
    @Expose
    private Prime prime;
    @SerializedName("remaingSpeedDatingTime")
    @Expose
    private String remaingSpeedDatingTime;
    @SerializedName("requestNo")
    @Expose
    private Integer requestNo;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("otp2")
    @Expose
    private String otp2;
    @SerializedName("isPhoneVerified")
    @Expose
    private Boolean isPhoneVerified;
    @SerializedName("cards")
    @Expose
    private List<String> cards = null;
    @SerializedName("maincards")
    @Expose
    private List<String> maincards = null;
    @SerializedName("myReferalcode")
    @Expose
    private String myReferalcode;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("profileImage")
    @Expose
    private String profileImage;
    @SerializedName("favMusic")
    @Expose
    private List<String> favMusic = null;
    @SerializedName("klickedMeter")
    @Expose
    private String klickedMeter;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("roleId")
    @Expose
    private Integer roleId;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("orientation")
    @Expose
    private List<String> orientation = null;
    @SerializedName("address")
    @Expose
    private List<Address> address = null;
    @SerializedName("specialFeature")
    @Expose
    private List<SpecialFeature> specialFeature = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("otpDate")
    @Expose
    private String otpDate;
    @SerializedName("DOB")
    @Expose
    private String dob;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("audioDescription")
    @Expose
    private String audioDescription;
    @SerializedName("zodiacSign")
    @Expose
    private String zodiacSign;
    @SerializedName("kyc")
    @Expose
    private List<Kyc> kyc = null;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("kycVerified")
    @Expose
    private String kycVerified;
    @SerializedName("total_reward")
    @Expose
    private Integer totalReward;
    @SerializedName("lastActivityTime")
    @Expose
    private String lastActivityTime;
    @SerializedName("spend")
    @Expose
    private Integer spend;
    @SerializedName("blockedByAdmin")
    @Expose
    private Boolean blockedByAdmin;
    @SerializedName("rejactedReason")
    @Expose
    private String rejactedReason;
    @SerializedName("purchasedPlan")
    @Expose
    private String purchasedPlan;

    public Prime getPrime() {
        return prime;
    }

    public void setPrime(Prime prime) {
        this.prime = prime;
    }

    public Integer getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(Integer requestNo) {
        this.requestNo = requestNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getOtp2() {
        return otp2;
    }

    public void setOtp2(String otp2) {
        this.otp2 = otp2;
    }

    public Boolean getIsPhoneVerified() {
        return isPhoneVerified;
    }

    public void setIsPhoneVerified(Boolean isPhoneVerified) {
        this.isPhoneVerified = isPhoneVerified;
    }

    public List<String> getCards() {
        return cards;
    }

    public void setCards(List<String> cards) {
        this.cards = cards;
    }

    public String getMyReferalcode() {
        return myReferalcode;
    }

    public void setMyReferalcode(String myReferalcode) {
        this.myReferalcode = myReferalcode;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public List<String> getFavMusic() {
        return favMusic;
    }

    public void setFavMusic(List<String> favMusic) {
        this.favMusic = favMusic;
    }

    public String getKlickedMeter() {
        return klickedMeter;
    }

    public void setKlickedMeter(String klickedMeter) {
        this.klickedMeter = klickedMeter;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getOrientation() {
        return orientation;
    }

    public void setOrientation(List<String> orientation) {
        this.orientation = orientation;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public List<SpecialFeature> getSpecialFeature() {
        return specialFeature;
    }

    public void setSpecialFeature(List<SpecialFeature> specialFeature) {
        this.specialFeature = specialFeature;
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

    public String getOtpDate() {
        return otpDate;
    }

    public void setOtpDate(String otpDate) {
        this.otpDate = otpDate;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAudioDescription() {
        return audioDescription;
    }

    public void setAudioDescription(String audioDescription) {
        this.audioDescription = audioDescription;
    }

    public String getZodiacSign() {
        return zodiacSign;
    }

    public void setZodiacSign(String zodiacSign) {
        this.zodiacSign = zodiacSign;
    }

    public List<Kyc> getKyc() {
        return kyc;
    }

    public void setKyc(List<Kyc> kyc) {
        this.kyc = kyc;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getKycVerified() {
        return kycVerified;
    }

    public void setKycVerified(String kycVerified) {
        this.kycVerified = kycVerified;
    }

    public Integer getTotalReward() {
        return totalReward;
    }

    public void setTotalReward(Integer totalReward) {
        this.totalReward = totalReward;
    }

    public String getLastActivityTime() {
        return lastActivityTime;
    }

    public void setLastActivityTime(String lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }

    public Integer getSpend() {
        return spend;
    }

    public void setSpend(Integer spend) {
        this.spend = spend;
    }

    public Boolean getBlockedByAdmin() {
        return blockedByAdmin;
    }

    public void setBlockedByAdmin(Boolean blockedByAdmin) {
        this.blockedByAdmin = blockedByAdmin;
    }

    public String getRejactedReason() {
        return rejactedReason;
    }

    public void setRejactedReason(String rejactedReason) {
        this.rejactedReason = rejactedReason;
    }

    public String getPurchasedPlan() {
        return purchasedPlan;
    }

    public void setPurchasedPlan(String purchasedPlan) {
        this.purchasedPlan = purchasedPlan;
    }

    public Boolean getPhoneVerified() {
        return isPhoneVerified;
    }

    public void setPhoneVerified(Boolean phoneVerified) {
        isPhoneVerified = phoneVerified;
    }

    public List<String> getMaincards() {
        return maincards;
    }

    public void setMaincards(List<String> maincards) {
        this.maincards = maincards;
    }

    public String getRemaingSpeedDatingTime() {
        return remaingSpeedDatingTime;
    }

    public void setRemaingSpeedDatingTime(String remaingSpeedDatingTime) {
        this.remaingSpeedDatingTime = remaingSpeedDatingTime;
    }

    public class Prime {

        @SerializedName("active")
        @Expose
        private Boolean active;

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }

        @Override
        public String toString() {
            return "Prime{" +
                    "active=" + active +
                    '}';
        }
    }

    public static class SpecialFeature {

        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("position")
        @Expose
        private Integer position;
        @SerializedName("_id")
        @Expose
        private String id;



        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getPosition() {
            return position;
        }

        public void setPosition(Integer position) {
            this.position = position;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "SpecialFeature{" +
                    "image='" + image + '\'' +
                    ", position=" + position +
                    ", id='" + id + '\'' +
                    '}';
        }
    }

    public class Address {

        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("countryCode")
        @Expose
        private String countryCode;
        @SerializedName("default")
        @Expose
        private Boolean _default;
        @SerializedName("_id")
        @Expose
        private String id;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public Boolean getDefault() {
            return _default;
        }

        public void setDefault(Boolean _default) {
            this._default = _default;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "country='" + country + '\'' +
                    ", city='" + city + '\'' +
                    ", state='" + state + '\'' +
                    ", countryCode='" + countryCode + '\'' +
                    ", _default=" + _default +
                    ", id='" + id + '\'' +
                    '}';
        }
    }

    public class Kyc {

        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("images")
        @Expose
        private List<String> images = null;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("_id")
        @Expose
        private String id;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Kyc{" +
                    "number='" + number + '\'' +
                    ", images=" + images +
                    ", type='" + type + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserProfileResponse{" +
                "prime=" + prime +
                ", requestNo=" + requestNo +
                ", id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", otp='" + otp + '\'' +
                ", otp2='" + otp2 + '\'' +
                ", isPhoneVerified=" + isPhoneVerified +
                ", cards=" + cards +
                ", maincards=" + maincards +
                ", myReferalcode='" + myReferalcode + '\'' +
                ", active=" + active +
                ", profileImage='" + profileImage + '\'' +
                ", favMusic=" + favMusic +
                ", klickedMeter='" + klickedMeter + '\'' +
                ", role='" + role + '\'' +
                ", roleId=" + roleId +
                ", category='" + category + '\'' +
                ", orientation=" + orientation +
                ", address=" + address +
                ", specialFeature=" + specialFeature +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", otpDate='" + otpDate + '\'' +
                ", dob='" + dob + '\'' +
                ", bio='" + bio + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", gender='" + gender + '\'' +
                ", height=" + height +
                ", occupation='" + occupation + '\'' +
                ", audioDescription='" + audioDescription + '\'' +
                ", zodiacSign='" + zodiacSign + '\'' +
                ", kyc=" + kyc +
                ", age=" + age +
                ", kycVerified='" + kycVerified + '\'' +
                ", totalReward=" + totalReward +
                ", lastActivityTime='" + lastActivityTime + '\'' +
                ", spend=" + spend +
                ", blockedByAdmin=" + blockedByAdmin +
                ", rejactedReason='" + rejactedReason + '\'' +
                ", purchasedPlan='" + purchasedPlan + '\'' +
                '}';
    }
}
