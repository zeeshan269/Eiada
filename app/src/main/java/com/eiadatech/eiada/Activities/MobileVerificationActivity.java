package com.eiadatech.eiada.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.chaos.view.PinView;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.PatientModel;
import com.eiadatech.eiada.Retrofit.Models.TokenServerResponse;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.google.android.material.snackbar.Snackbar;
import com.twilio.verification.external.Via;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.eiadatech.eiada.Activities.SignUpActivity.registerPatient;
import static com.eiadatech.eiada.Activities.SignUpActivity.twilioVerification;
import static com.eiadatech.eiada.TwilioVerificationReceiver.verificationCode;

public class MobileVerificationActivity extends AppCompatActivity {

    private String pinValue = "";
    private ConstraintLayout constraintLayout;
    private TextView textViewMobile,textViewSendCodeAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);
        initializeViews();
    }


    private void initializeViews() {
        final PinView pinView = findViewById(R.id.first);
        textViewMobile= findViewById(R.id.textView139);
        textViewSendCodeAgain= findViewById(R.id.textView140);
        textViewMobile.setText(registerPatient.getMobile());
        pinView.setTextColor(
                ResourcesCompat.getColor(getResources(), R.color.colorAccent, getTheme()));
        pinView.setTextColor(
                ResourcesCompat.getColorStateList(getResources(), R.color.themeColor, getTheme()));
        pinView.setLineColor(
                ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getTheme()));
        pinView.setLineColor(
                ResourcesCompat.getColorStateList(getResources(), R.color.themeColor, getTheme()));
        pinView.setItemCount(4);
        pinView.setItemHeight(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_size));
        pinView.setItemWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_size));
        pinView.setItemRadius(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_radius));
        pinView.setItemSpacing(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_spacing));
        pinView.setLineWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_item_line_width));
        pinView.setAnimationEnable(true);// start animation when adding text
        pinView.setCursorVisible(false);
        pinView.setCursorColor(
                ResourcesCompat.getColor(getResources(), R.color.colorDarkGray, getTheme()));
        pinView.setCursorWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_cursor_width));

        pinView.setItemBackgroundColor(Color.BLACK);
        pinView.setItemBackground(getResources().getDrawable(R.drawable.edit_text_background));
        pinView.setItemBackgroundResources(R.drawable.edit_text_background);
        pinView.setHideLineWhenFilled(false);

        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                pinValue = editable.toString();
            }
        });

        textViewSendCodeAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTokenServerApi(registerPatient.getMobile());
            }
        });

        Button btnContinue = findViewById(R.id.button17);
        constraintLayout = findViewById(R.id.constraintLayout);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pinValue.isEmpty()) {

                    twilioVerification.checkVerificationPin(pinValue);

                    if (verificationCode.equalsIgnoreCase("SUCCESS")) {

                    } else {
                        Toast.makeText(MobileVerificationActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MobileVerificationActivity.this, "Please provide Pin Number", Toast.LENGTH_SHORT).show();
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
        retrofitInterface.getToken(  mobile).enqueue(new Callback<TokenServerResponse>() {
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


}
