package com.dating.klicked.Model.ResponseRepo;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PendingResponse {
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("count")
    @Expose
    private Integer count;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    public class Result {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("sender")
        @Expose
        private Sender sender;
        @SerializedName("receiver")
        @Expose
        private Receiver receiver;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Sender getSender() {
            return sender;
        }

        public void setSender(Sender sender) {
            this.sender = sender;
        }

        public Receiver getReceiver() {
            return receiver;
        }

        public void setReceiver(Receiver receiver) {
            this.receiver = receiver;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public class Sender {

            @SerializedName("_id")
            @Expose
            private String id;
            @SerializedName("prime")
            @Expose
            private Prime prime;
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
            @SerializedName("audioDescription")
            @Expose
            private String audioDescription;

            @SerializedName("kycVerified")
            @Expose
            private String kycVerified;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Prime getPrime() {
                return prime;
            }

            public void setPrime(Prime prime) {
                this.prime = prime;
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

            public String getAudioDescription() {
                return audioDescription;
            }

            public void setAudioDescription(String audioDescription) {
                this.audioDescription = audioDescription;
            }

            public Boolean getPhoneVerified() {
                return isPhoneVerified;
            }

            public void setPhoneVerified(Boolean phoneVerified) {
                isPhoneVerified = phoneVerified;
            }

            public String getKycVerified() {
                return kycVerified;
            }

            public void setKycVerified(String kycVerified) {
                this.kycVerified = kycVerified;
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

            }
        }

        public class Receiver {

            @SerializedName("_id")
            @Expose
            private String id;
            @SerializedName("prime")
            @Expose
            private Prime prime;
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
            @SerializedName("audioDescription")
            @Expose
            private String audioDescription;
            @SerializedName("kycVerified")
            @Expose
            private String kycVerified;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Prime getPrime() {
                return prime;
            }

            public void setPrime(Prime prime) {
                this.prime = prime;
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

            public String getAudioDescription() {
                return audioDescription;
            }

            public void setAudioDescription(String audioDescription) {
                this.audioDescription = audioDescription;
            }

            public Boolean getPhoneVerified() {
                return isPhoneVerified;
            }

            public void setPhoneVerified(Boolean phoneVerified) {
                isPhoneVerified = phoneVerified;
            }

            public String getKycVerified() {
                return kycVerified;
            }

            public void setKycVerified(String kycVerified) {
                this.kycVerified = kycVerified;
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

            }
        }
    }
}
