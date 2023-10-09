package com.dating.klicked.Model.ResponseRepo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewedProfiledResponse {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("users")
    @Expose
    private List<User> users = null;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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

    public class User {

        @SerializedName("Id")
        @Expose
        private Id id;
        @SerializedName("_id")
        @Expose
        private String ids;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;

        public Id getId() {
            return id;
        }

        public void setId(Id id) {
            this.id = id;
        }

        public String getIds() {
            return ids;
        }

        public void setIds(String ids) {
            this.ids = ids;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public class Id {

            @SerializedName("_id")
            @Expose
            private String ids;
            @SerializedName("profileImage")
            @Expose
            private String profileImage;
            @SerializedName("address")
            @Expose
            private List<Address> address = null;
            @SerializedName("DOB")
            @Expose
            private String dob;
            @SerializedName("firstName")
            @Expose
            private String firstName;
            @SerializedName("gender")
            @Expose
            private String gender;
            @SerializedName("audioDescription")
            @Expose
            private String audioDescription;

            public String getIds() {
                return ids;
            }

            public void setIds(String ids) {
                this.ids = ids;
            }

            public String getProfileImage() {
                return profileImage;
            }

            public void setProfileImage(String profileImage) {
                this.profileImage = profileImage;
            }

            public List<Address> getAddress() {
                return address;
            }

            public void setAddress(List<Address> address) {
                this.address = address;
            }

            public String getDob() {
                return dob;
            }

            public void setDob(String dob) {
                this.dob = dob;
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

            public String getAudioDescription() {
                return audioDescription;
            }

            public void setAudioDescription(String audioDescription) {
                this.audioDescription = audioDescription;
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
