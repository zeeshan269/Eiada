package com.eiadatech.eiada.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.PatientModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Activities.ForgetPasswordActivity.userId;

public class NewPasswordActivity extends AppCompatActivity {


    private EditText editTextPassword, editTextRepeatPassword;
    private CheckedTextView showPassword, showRepeatPassword;
    private Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        editTextPassword = findViewById(R.id.editText3);
        editTextRepeatPassword = findViewById(R.id.editText4);
        showPassword = findViewById(R.id.imageView22);
        showRepeatPassword = findViewById(R.id.imageView23);
        btnReset = findViewById(R.id.button);

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPassword.isChecked()) {
                    showPassword.setChecked(false);
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    showPassword.setChecked(true);
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
            }
        });

        showRepeatPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showRepeatPassword.isChecked()) {
                    showRepeatPassword.setChecked(false);
                    editTextRepeatPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    showRepeatPassword.setChecked(true);
                    editTextRepeatPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = editTextPassword.getText().toString();
                String retypePassword = editTextRepeatPassword.getText().toString();

                if (password.isEmpty()) {
                    Toast.makeText(NewPasswordActivity.this, "Please provide Password", Toast.LENGTH_SHORT).show();
                } else if (retypePassword.isEmpty()) {
                    Toast.makeText(NewPasswordActivity.this, "Please Re-type Password", Toast.LENGTH_SHORT).show();
                } else if (!password.equalsIgnoreCase(retypePassword)) {
                    Toast.makeText(NewPasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    updatePassword(userId, password);
                }
            }
        });


    }

    private void updatePassword(final String userId, final String password) {


        PatientModel patientModel = new PatientModel();
        patientModel.setPatientId(userId);
        patientModel.setPassword(password);

        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<PatientModel> apiCall = retrofitInterface.updatePatientPassword(patientModel);

        apiCall.enqueue(new Callback<PatientModel>() {
            @Override
            public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(NewPasswordActivity.this, "Password reset successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(NewPasswordActivity.this, LoginActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent1);
                }
            }

            @Override
            public void onFailure(Call<PatientModel> call, Throwable t) {

                Toast.makeText(NewPasswordActivity.this, "Internet problem! password not Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
