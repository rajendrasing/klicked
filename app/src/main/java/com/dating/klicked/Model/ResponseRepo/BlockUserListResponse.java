package com.dating.klicked.Model.ResponseRepo;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlockUserListResponse {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("blockedUser")
    @Expose
    private List<BlockedUser> blockedUser = null;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<BlockedUser> getBlockedUser() {
        return blockedUser;
    }

    public void setBlockedUser(List<BlockedUser> blockedUser) {
        this.blockedUser = blockedUser;
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

    public class BlockedUser {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("DOB")
        @Expose
        private String dob;
        @SerializedName("address")
        @Expose
        private List<Address> address = null;
        @SerializedName("profileImage")
        @Expose
        private String profileImage;
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

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public List<Address> getAddress() {
            return address;
        }

        public void setAddress(List<Address> address) {
            this.address = address;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getAudioDescription() {
            return audioDescription;
        }

        public void setAudioDescription(String audioDescription) {
            this.audioDescription = audioDescription;
        }

        public String getKycVerified() {
            return kycVerified;
        }

        public void setKycVerified(String kycVerified) {
            this.kycVerified = kycVerified;
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
