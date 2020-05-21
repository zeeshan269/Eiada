package com.eiadatech.eiada.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.PatientModel;
import com.eiadatech.eiada.Retrofit.Models.TokenServerResponse;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.twilio.verification.TwilioVerification;
import com.twilio.verification.external.Via;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private Button btnReset;
    public static final String ACCOUNT_SID = "AC46a46ef25a90e9ec633586aa28dd3f9c";
    public static final String AUTH_TOKEN = "";
    public static String mobile;
    public static TwilioVerification twilioVerification;
    public static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        twilioVerification = new TwilioVerification(this);
        initializeVierws();
    }

    private void initializeVierws() {

        editTextPhone = findViewById(R.id.editText19);
        btnReset = findViewById(R.id.button);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = editTextPhone.getText().toString();


                if (phone.isEmpty()) {
                    Toast.makeText(ForgetPasswordActivity.this, "Please enter valid mobile number", Toast.LENGTH_LONG).show();
                } else if (phone.length() != 12) {
                    Toast.makeText(ForgetPasswordActivity.this, "Please enter mobile number with country code e.g 971", Toast.LENGTH_LONG).show();
                } else {
                    phone = "+" + phone;
                    checkMobileVerification(phone);
                }
            }
        });


    }


    private void initTokenServerApi(String mobile) {

        String TOKEN_SERVER_URL = "https://eiada.herokuapp.com";

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(TOKEN_SERVER_URL)
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        retrofitInterface.getToken(mobile).enqueue(new Callback<TokenServerResponse>() {
            @Override
            public void onResponse(Call<TokenServerResponse> call, Response<TokenServerResponse> response) {
                if (response.isSuccessful()) {

                }
                String jwtToken = response.body().getJwtToken();
                twilioVerification.startVerification(jwtToken, Via.SMS);

            }

            @Override
            public void onFailure(Call<TokenServerResponse> call, Throwable t) {

            }
        });
    }

    private void checkMobileVerification(String phone) {
        PatientModel patientModel = new PatientModel();
        patientModel.setMobile(phone);
        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<PatientModel> api = retrofitInterface.checkMobileVerification(patientModel);
        api.enqueue(new Callback<PatientModel>() {
            @Override
            public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {
                if (response.isSuccessful()) {

                    if (response.body().getEmail().isEmpty()) {
                        Toast.makeText(ForgetPasswordActivity.this, "Mobile Number does not exist", Toast.LENGTH_LONG).show();

                    } else if (response.body().getEmail().equalsIgnoreCase("Failed")) {
                        Toast.makeText(ForgetPasswordActivity.this, "Server Problem please try later", Toast.LENGTH_LONG).show();
                    } else {
                        userId = response.body().getPatientId();
                        initTokenServerApi(phone);
                        mobile = phone;
                        startActivity(new Intent(ForgetPasswordActivity.this, PasswordResetVerificationActivity.class));
                    }

                }
            }

            @Override
            public void onFailure(Call<PatientModel> call, Throwable t) {

            }
        });
    }
}
