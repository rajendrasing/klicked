package com.dating.klicked.Api;

import com.dating.klicked.Model.RequestRepo.FilterBody;
import com.dating.klicked.Model.RequestRepo.RazorCaptureBody;
import com.dating.klicked.Model.RequestRepo.RazorSaveDataBody;
import com.dating.klicked.Model.RequestRepo.SendMessageBody;
import com.dating.klicked.Model.RequestRepo.SpeedChatDeleteBody;
import com.dating.klicked.Model.RequestRepo.UpdateProfile;
import com.dating.klicked.Model.RequestRepo.VerifyOTP_Body;
import com.dating.klicked.Model.ResponseRepo.BlockUserListResponse;
import com.dating.klicked.Model.ResponseRepo.CapturePaymentSuccessResp;
import com.dating.klicked.Model.ResponseRepo.CardResponse;
import com.dating.klicked.Model.ResponseRepo.CardSubCardResponse;
import com.dating.klicked.Model.ResponseRepo.ChatResponse;
import com.dating.klicked.Model.ResponseRepo.CountryCode;
import com.dating.klicked.Model.ResponseRepo.FAQResponse;
import com.dating.klicked.Model.ResponseRepo.FavMusicResponse;
import com.dating.klicked.Model.ResponseRepo.FavouriteResponse;
import com.dating.klicked.Model.ResponseRepo.FeedbackResponse;
import com.dating.klicked.Model.ResponseRepo.GiftResponse;
import com.dating.klicked.Model.ResponseRepo.HomeResponse;
import com.dating.klicked.Model.ResponseRepo.HotelResponse;
import com.dating.klicked.Model.ResponseRepo.KlickedResponse;
import com.dating.klicked.Model.ResponseRepo.MainCardResponse;
import com.dating.klicked.Model.ResponseRepo.PaymentDataSaveResp;
import com.dating.klicked.Model.ResponseRepo.PendingResponse;
import com.dating.klicked.Model.ResponseRepo.PlanResponse;
import com.dating.klicked.Model.ResponseRepo.RazorOrderResp;
import com.dating.klicked.Model.ResponseRepo.RedeemHistoryResponse;
import com.dating.klicked.Model.ResponseRepo.RewardHistoryResponse;
import com.dating.klicked.Model.ResponseRepo.SendMessageResponse;
import com.dating.klicked.Model.ResponseRepo.SubHintsResponse;
import com.dating.klicked.Model.ResponseRepo.UserChatResponse;
import com.dating.klicked.Model.ResponseRepo.UserProfileResponse;
import com.dating.klicked.Model.ResponseRepo.UserResponse;
import com.dating.klicked.Model.ResponseRepo.ViewedProfiledResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("countries")
    Call<List<CountryCode>> GetAllCountriesCode();


    @POST("location/countries")
    Call<ResponseBody> GetCountry();

    @FormUrlEncoded
    @POST("location/state")
    Call<ResponseBody> GetState(@Field("id") String id);

    @FormUrlEncoded
    @POST("location/city")
    Call<ResponseBody> GetCity(@Field("id") String id);

    @FormUrlEncoded
    @POST("user/register")
    Call<ResponseBody> SignUp(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("user/sendOtp")
    Call<ResponseBody> SendOtp(@Field("phone") String phone);

    @POST("user/emailVerify")
    Call<ResponseBody> PhoneNO_Verify(@Body VerifyOTP_Body body);

    @GET("user/getCards")
    Call<List<CardResponse>> GetAllCard();

    @GET("user/mainCardById")
    Call<MainCardResponse> MAIN_CARD_RESPONSE_CALL(@Query("id") String userid);

    @GET("user/getsubCards")
    Call<List<CardResponse>> GetAllSubCard(@Query("card") String CardName);

    @PUT("user")
    Call<ResponseBody> UpdateUserProfile(@Body UpdateProfile updateProfile);

    @Multipart
    @PUT("user/audioDescription")
    Call<ResponseBody> uploadAudioDes(@Part MultipartBody.Part Audio,
                                      @Part("phone") RequestBody PhoneNo);


    @Multipart
    @PUT("user/favMusic")
    Call<ResponseBody> uploadFavMusic(@Part MultipartBody.Part favMusic,
                                      @Part("phone") RequestBody PhoneNo);

    @FormUrlEncoded
    @POST("user/getfavMusic")
    Call<FavMusicResponse> GetFavMusic(@Field("phone") String phone);

    @FormUrlEncoded
    // @DELETE("user/deleteFavMusic")
    @HTTP(method = "DELETE", path = "user/deleteFavMusic", hasBody = true)
    Call<ResponseBody> DeleteFavMusic(@Field("favMusic") String favMusic,
                                      @Field("phone") String phone);


    @Multipart
    @PUT("user/addProfile")
    Call<ResponseBody> uploadProfileImage(@Part MultipartBody.Part profileImage,
                                          @Part("phone") RequestBody PhoneNo);

    @Multipart
    @PUT("user/specialFeature")
    Call<ResponseBody> uploadSpecialFeatureImage(@Part MultipartBody.Part profileImage,
                                                 @Part("phone") RequestBody PhoneNo,
                                                 @Part("position") RequestBody position);


    @POST("user/login")
    Call<UserResponse> UserLogin(@Body VerifyOTP_Body body);

    @FormUrlEncoded
    @POST("user/social")
    Call<UserResponse> SocialLogin(@Field("email") String email,
                                   @Field("firstName") String name);


    @GET("user")
    Call<UserProfileResponse> GetUserProfile();

    @POST("user/filterProfile")
    Call<HomeResponse> getHomeProfile(@Body FilterBody body);

    @GET("user/homeUsers")
    Call<HomeResponse> getAllHomeProfile();

    @POST("user/addScrollProfile")
    Call<ResponseBody> addSeenSwipeProfile(@Body SpeedChatDeleteBody deleteUserSeen);

    @GET("favourites")
    Call<List<FavouriteResponse>> getFavouriteUser();


    @GET("favourites/recent")
    Call<List<FavouriteResponse>> getRecentFavouriteUser();

    @POST("favourites")
    Call<ResponseBody> addFavouriteUser(@Query("id") String userId);

    @DELETE("favourites")
    Call<ResponseBody> DeleteFavouriteUser(@Query("id") String userId);

    @FormUrlEncoded
    @POST("request/addRequest")
    Call<ResponseBody> SendRequest(@Field("sender") String senderId,
                                   @Field("receiver") String receiverId);

    @GET("klickedList")
    Call<KlickedResponse> getKlickedUser();

    @GET("user/getIdUser")
    Call<UserProfileResponse> GetUserByIdProfile(@Query("id") String UserId);

    @GET("request/sender/pending")
    Call<PendingResponse> GetPendingUser();

    @PUT("request/sender/rejectRequest/{id}")
    Call<ResponseBody> CancelPending(@Path("id") String docId);

    @GET("user/cardsByUserId")
    Call<CardSubCardResponse> getUserCardsById(@Query("id") String userId);


    @GET("request")
    Call<PendingResponse> getRequestUser();

    @PUT("request/rejectRequest/{id}")
    Call<ResponseBody> CancelRequest(@Path("id") String docId);


    @PUT("request/acceptRequest/{id}")
    Call<ResponseBody> AcceptRequest(@Path("id") String docId);


    @FormUrlEncoded
    @POST("chats/newChatDocument")
    Call<ResponseBody> crateChatDocument(@Field("idReceiver") String ReceiverId,
                                         @Field("firstName") String ReceiverName);

    @POST("chats/insertUser")
    Call<ResponseBody> OnlineUser();

    @POST("chats/ejectUser")
    Call<ResponseBody> OfflineUser();

    @GET("chats/oldChats")
    Call<ChatResponse> getHomeChat();

    @POST("chats/chatToUser")
    Call<SendMessageResponse> SendMessage(@Body SendMessageBody body);

    @GET("chats/getMessages")
    Call<UserChatResponse> UserChat(@Query("chatId") String chatId);

    @GET("plans/all")
    Call<List<PlanResponse>> GetAllPlan();


    @FormUrlEncoded
    @POST("razorpay/order")
    Call<RazorOrderResp> createRazorOrder(@Field("amount") String amount);

    @POST("razorpay/capture")
    Call<CapturePaymentSuccessResp> razorCapturePayment(@Body RazorCaptureBody captureBody);

    @PUT("razorpay/data")
    Call<PaymentDataSaveResp> razorDataSave(@Query("id") String docId, @Body RazorSaveDataBody dataBody);


    @POST("visitedProfile")
    Call<ResponseBody> PutDataViewedProfile(@Query("id") String userId);

    @GET("visitedProfile")
    Call<List<ViewedProfiledResponse>> GetDataViewedProfile();

    @GET("feedbacks")
    Call<FeedbackResponse> GetAllFeedbackData();

    @FormUrlEncoded
    @POST("feedbacks/addfeedbacks")
    Call<ResponseBody> AddFeedbacks(@Field("title") String title,
                                    @Field("description") String description);

    @GET("FAQ/getFAQ")
    Call<List<FAQResponse>> GetFAQ();

    @FormUrlEncoded
    @POST("FAQ/postFAQ")
    Call<ResponseBody> AddFAQ(@Field("question") String Question);

    @Multipart
    @POST("user/kyc")
    Call<ResponseBody> uploadBoostProfile(@Part MultipartBody.Part docImage,
                                          @Part("number") RequestBody DocNo,
                                          @Part("type") RequestBody DocType);

    @GET("boost/getFAQ")
    Call<List<FAQResponse>> GetOtherFAQ(@Query("key") String type);

    @POST("user/speedDating")
    Call<UserResponse> GetSeedDatingUser();

    // Speed dating chatings api

    @FormUrlEncoded
    @POST("speedChat/newSpeedDocument")
    Call<ResponseBody> crateSpeedDocument(@Field("idReceiver") String ReceiverId,
                                          @Field("firstName") String ReceiverName);


    @GET("speedChat/oldSpeedChats")
    Call<ChatResponse> getOldSpeedChat();

    @HTTP(method = "DELETE", path = "speedChat/chat", hasBody = true)
    Call<ResponseBody> DeleteOldSpeedChat(@Body SpeedChatDeleteBody speedChatDeleteBody);

    @POST("speedChat/speedChatToUser")
    Call<SendMessageResponse> SpeedSendMessage(@Body SendMessageBody body);

    @GET("speedChat/getSpeedMessages")
    Call<UserChatResponse> SpeedUserChat(@Query("chatId") String chatId);

    @GET("hints/questions")
    Call<List<FAQResponse>> GetHints();

    @GET("hints")
    Call<List<SubHintsResponse>> GetSubHintsResponse(@Query("quesid") String quesid);


    @GET("reward/getrewardbyid")
    Call<RewardHistoryResponse> rewardHistory();

    @GET("gift/getgift")
    Call<List<GiftResponse>> GetALLGift();

    @GET("gift/getgift")
    Call<GiftResponse> GetGiftByID(@Query("id") String id);

    @FormUrlEncoded
    @POST("reward/redeem_gift")
    Call<ResponseBody> RedeemGift(@Field("giftId") String giftId,
                                  @Field("name") String name,
                                  @Field("phone") String phone,
                                  @Field("Address") String Address);


    @GET("reward/redeemGiftuser")
    Call<RedeemHistoryResponse> RedeemHistory();

    @FormUrlEncoded
    @POST("reward/addreward")
    Call<ResponseBody> ShareUserProfile(@Field("userId") String loginUserId,
                                        @Field("shareId") String shareId);


    @FormUrlEncoded
    @POST("chats/block")
    Call<ResponseBody> BlockUser(@Field("idReceiver") String idReceiver);

    @POST("chats/unBlock")
    Call<ResponseBody> UnBlockUser(@Query("id") String ReceiverId);

    @FormUrlEncoded
    @POST("report/sendReport")
    Call<ResponseBody> ReportUser(@Field("report_id") String ReceiverId,
                                  @Field("reason") String reason);

    @FormUrlEncoded
    @PUT("user/deactivateAccount")
    Call<ResponseBody> DeactivateAccount(@Field("reason") String reason);

    @GET("chats/BlockUsers")
    Call<BlockUserListResponse> BLOCK_USER_LIST();

    @FormUrlEncoded
    @PUT("user/deleteSpecialFeature")
    Call<ResponseBody> DeleteSpecialFeature(@Field("Id") String id,
                                            @Field("phone") String phone);


    @GET("chats/klickedmeter")
    Call<ResponseBody> GetKlickedMeter(@Query("reciverid") String RecevierId);

    @GET("hotel")
    Call<List<HotelResponse>> getAllHotel();

    @FormUrlEncoded
    @PUT("user")
    Call<ResponseBody> updateSpeedDatingTime(@Field("remaingSpeedDatingTime") String time,
                                             @Field("phone") String phone);

    @DELETE("klickedList")
    Call<ResponseBody>UserDelete(@Query("id") String id);
}

