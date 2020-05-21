package com.eiadatech.eiada.Retrofit;

import com.eiadatech.eiada.Retrofit.Models.AppointmentModel;
import com.eiadatech.eiada.Retrofit.Models.BookingAddressModel;
import com.eiadatech.eiada.Retrofit.Models.ChatModel;
import com.eiadatech.eiada.Retrofit.Models.DocumentModel;
import com.eiadatech.eiada.Retrofit.Models.FamilyMemberModel;
import com.eiadatech.eiada.Retrofit.Models.PatientModel;
import com.eiadatech.eiada.Retrofit.Models.PayPalToken;
import com.eiadatech.eiada.Retrofit.Models.ProfessionalModel;
import com.eiadatech.eiada.Retrofit.Models.ReportModel;
import com.eiadatech.eiada.Retrofit.Models.ReviewModel;
import com.eiadatech.eiada.Retrofit.Models.SlotModel;
import com.eiadatech.eiada.Retrofit.Models.SymptonsModel;
import com.eiadatech.eiada.Retrofit.Models.TokenModel;
import com.eiadatech.eiada.Retrofit.Models.TokenServerResponse;
import com.eiadatech.eiada.Retrofit.Models.TwilioModel;

import org.apache.http.HttpResponse;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @POST("authenticatePatient.php")
    Call<PatientModel> authenticatePatient(@Body PatientModel patientModel);

    @POST("registerPatient.php")
    Call<PatientModel> registerPatient(@Body PatientModel patientModel);

    @POST("updatePatientPassword.php")
    Call<PatientModel> updatePatientPassword(@Body PatientModel patientModel);

    @POST("updatePatientProfile.php")
    Call<PatientModel> updatePatientProfile(@Body PatientModel patientModel);

    @POST("getFamilyMembers.php")
    Call<List<FamilyMemberModel>> getFamilyMembers(@Body PatientModel patientModel);

    @POST("addFamilyMember.php")
    Call<FamilyMemberModel> addFamilyMember(@Body FamilyMemberModel familyMemberModel);

    @POST("updateFamilyMember.php")
    Call<FamilyMemberModel> updateFamilyMember(@Body FamilyMemberModel familyMemberModel);

    @POST("deleteFamilyMember.php")
    Call<FamilyMemberModel> deleteFamilyMember(@Body FamilyMemberModel familyMemberModel);

    @POST("submitDocuments.php")
    Call<DocumentModel> submitDocuments(@Body DocumentModel documentModel);

    @POST("updateDocuments.php")
    Call<DocumentModel> updateDocuments(@Body DocumentModel documentModel);

    @POST("getPatientChats.php")
    Call<List<ChatModel>> getChats(@Body PatientModel patientModel);

    @POST("getAvailableProfessionals.php")
    Call<List<ProfessionalModel>> getAvailableProfessionals(@Body SymptonsModel symptonsModel);

    @POST("getAvailableProfessionalsByDate.php")
    Call<List<ProfessionalModel>> getAvailableProfessionalsByDate(@Body SymptonsModel symptonsModel);

    @POST("getReports.php")
    Call<List<ReportModel>> getReports(@Body PatientModel patientModel);


    @POST("giveReview.php")
    Call<ReviewModel> giveReview(@Body ReviewModel reviewModel);

    @POST("getReview.php")
    Call<ReviewModel> getReview(@Body ReviewModel reviewModel);

    @POST("getProfessionalReviews.php")
    Call<List<ReviewModel>> getProfessionalReviews(@Body ReviewModel reviewModel);

    @POST("sendMessage.php")
    Call<List<ChatModel>> sendMessage(@Body ChatModel chatModel);

    @POST("addBookingAddress.php")
    Call<BookingAddressModel> addBookingAddress(@Body BookingAddressModel bookingAddressModel);


    @POST("updateBookingAddress.php")
    Call<BookingAddressModel> updateBookingAddress(@Body BookingAddressModel bookingAddressModel);

    @POST("deleteBookingAddress.php")
    Call<BookingAddressModel> deleteBookingAddress(@Body BookingAddressModel bookingAddressModel);

    @POST("getBookingAddresses.php")
    Call<List<BookingAddressModel>> getBookingAddresses(@Body PatientModel patientModel);

    @POST("getSymptonsByProfession.php")
    Call<List<SymptonsModel>> getSymptonsByProfession(@Body SymptonsModel symptonsModel);

    @POST("getSlotsByDate.php")
    Call<List<SlotModel>> getSlotsByDate(@Body SlotModel slotModel);

    @POST("addBooking.php")
    Call<AppointmentModel> addAppointment(@Body AppointmentModel appointmentModel);

    @POST("getAppointments.php")
    Call<List<AppointmentModel>> getAppointments(@Body PatientModel patientModel);

    @POST("registerToken.php")
    Call<TokenModel> registerToken(@Body TokenModel tokenModel);

    @POST("deleteToken.php")
    Call<TokenModel> deleteToken(@Body TokenModel tokenModel);

    @POST("changeProfileImage.php")
    Call<PatientModel> changeProfileImage(@Body PatientModel patientModel);

    @GET("getActiveProfessionals.php")
    Call<List<ProfessionalModel>> getActiveProfessionals();

    @POST("getUploadedDocuments.php")
    Call<List<DocumentModel>> getUploadedDocuments(@Body PatientModel patientModel);

    @POST("checkMobileVerification.php")
    Call<PatientModel> checkMobileVerification(@Body PatientModel patientModel);

     @FormUrlEncoded
    @POST("v1/oauth2/token")
    Call<PayPalToken> getToken(
            @Header("Authorization") String signature,
            @Field("grant_type") String metadata
    );


    @FormUrlEncoded
    @POST("v2/Services/{serviceSid}/Verifications")
    Call<HttpResponse> sendCode(
            @Header("Authorization") String authorixation,
            @Path("serviceSid") String sid,
            @Field("to") String to,
            @Field("channel") String channel,
            @Field("customMessage") String customMessage
    );


    @POST("v2/Services/VA6e4ffebfa7fd39651b9e6eddeaaf0c80")
    Call<TwilioModel> getSer(@Header("Authorization") String signature);


    @POST("/verify/token")
    @FormUrlEncoded
    Call<TokenServerResponse> getToken(@Field("phone_number") String phoneNumber);


    // send SMS through Twilio
    @FormUrlEncoded
    @POST("Accounts/{ACCOUNT_SID}/SMS/Messages")
    Call<ResponseBody> sendMessage(
            @Path("ACCOUNT_SID") String accountSId,
            @Header("Authorization") String signature,
            @FieldMap Map<String, String> metadata
    );


    @POST("updateNotificationSettings.php")
    Call<PatientModel> updateNotificationSettings(@Body PatientModel patientModel);

}
