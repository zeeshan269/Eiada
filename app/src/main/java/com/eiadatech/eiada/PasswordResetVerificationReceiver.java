package com.eiadatech.eiada;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.eiadatech.eiada.Activities.LoginActivity;
import com.eiadatech.eiada.Activities.NewPasswordActivity;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.PatientModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.twilio.verification.TwilioVerification;
import com.twilio.verification.external.VerificationStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Activities.SignUpActivity.registerPatient;
import static com.eiadatech.eiada.Activities.SignUpActivity.signuP;


public class PasswordResetVerificationReceiver extends BroadcastReceiver {
    public static String verificationCode;

    @Override
    public void onReceive(Context context, Intent intent) {
        VerificationStatus verificationStatus = TwilioVerification.getVerificationStatus(intent);

        //   NOT_STARTED, STARTED, AWAITING_VERIFICATION, SUCCESS, ERROR
        verificationCode = String.valueOf(verificationStatus.getState());

        if (signuP == null) {

            if (verificationCode.equalsIgnoreCase("SUCCESS")) {
                Intent intent1 = new Intent(context, NewPasswordActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent1);

            } else {
                Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();
            }

        } else if (signuP.isEmpty()) {
            if (verificationCode.equalsIgnoreCase("SUCCESS")) {
                Intent intent1 = new Intent(context, NewPasswordActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent1);

            } else {
                Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();
            }

        } else {
            registerPatient(context, registerPatient);
        }

    }

    private void registerPatient(Context context, PatientModel patientModel) {

        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<PatientModel> apiCall = retrofitInterface.registerPatient(patientModel);

        apiCall.enqueue(new Callback<PatientModel>() {
            @Override
            public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {
                if (response.isSuccessful()) {

                    if (response.body().getName().equalsIgnoreCase("Failed")) {
                        Toast.makeText(context, "Account already Exists! Please try another one", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Patient registered Successfully!", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<PatientModel> call, Throwable t) {

                Toast.makeText(context, "Internet Problem please try later!", Toast.LENGTH_SHORT).show();

            }
        });

    }


}
