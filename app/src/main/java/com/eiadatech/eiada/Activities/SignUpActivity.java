package com.eiadatech.eiada.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.Models.PatientModel;
import com.eiadatech.eiada.Retrofit.Models.TokenServerResponse;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.google.android.material.snackbar.Snackbar;
import com.twilio.verification.TwilioVerification;
import com.twilio.verification.external.Via;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private Button btnSIgnUp;
    private EditText editTextEmail, editTextName, editTextPassword, editTextRepeatPassword, editTextMobile;
    private TextView textDateOfBirth;
    private Spinner spinnerGender;
    private String gender = "", dateOfBirth = "";
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private TextView textViewSignIn;
    private ConstraintLayout relativeLayout;
    private CheckedTextView showPassword, showRepeatPassword;

    private ProgressDialog progressDialog;
    public static final String ACCOUNT_SID = "AC46a46ef25a90e9ec633586aa28dd3f9c";
    public static final String AUTH_TOKEN = "";

    public static TwilioVerification twilioVerification;
    public static PatientModel registerPatient;

    public static String signuP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initializeViews();
    }

    private void initializeViews() {

        btnSIgnUp = findViewById(R.id.button);
        editTextEmail = findViewById(R.id.editText2);
        editTextName = findViewById(R.id.editText);
        editTextMobile = findViewById(R.id.editText45);
        editTextPassword = findViewById(R.id.editText3);
        editTextRepeatPassword = findViewById(R.id.editText4);
        textDateOfBirth = findViewById(R.id.spinner4);
        spinnerGender = findViewById(R.id.spinner2);
        textViewSignIn = findViewById(R.id.textView6);
        relativeLayout = findViewById(R.id.constraintLayout);
        showPassword = findViewById(R.id.imageView22);
        showRepeatPassword = findViewById(R.id.imageView23);
        progressDialog = new ProgressDialog(SignUpActivity.this);
        getDate();


        twilioVerification = new TwilioVerification(this);


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


        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        textViewSignIn.setOnClickListener(view -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
            finish();
        });
        btnSIgnUp.setOnClickListener(view -> {


            String name = editTextName.getText().toString();
            String email = editTextEmail.getText().toString().toLowerCase();
            String password = editTextPassword.getText().toString();
            String retypePassword = editTextRepeatPassword.getText().toString();

            if (name.isEmpty()) {
                Snackbar.make(relativeLayout, "Please provide name", Snackbar.LENGTH_SHORT).show();
            } else if (email.isEmpty() || !(email.matches(emailPattern))) {
                Snackbar.make(relativeLayout, "Please provide valid email", Snackbar.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Snackbar.make(relativeLayout, "Please provide Password", Snackbar.LENGTH_SHORT).show();
            } else if (retypePassword.isEmpty()) {
                Snackbar.make(relativeLayout, "Please Re-type Password", Snackbar.LENGTH_SHORT).show();
            } else if (!password.equalsIgnoreCase(retypePassword)) {
                Snackbar.make(relativeLayout, "Passwords do not match", Snackbar.LENGTH_SHORT).show();
            } else if (dateOfBirth.equalsIgnoreCase("")) {
                Snackbar.make(relativeLayout, "Please Select Date Of Birth", Snackbar.LENGTH_SHORT).show();
            } else if (editTextMobile.getText().toString().isEmpty()) {
                Snackbar.make(relativeLayout, "Please provide valid phone number", Snackbar.LENGTH_SHORT).show();
            } else {


                PatientModel patientModel = new PatientModel();
                patientModel.setName(name);
                patientModel.setEmail(email);
                patientModel.setPassword(password);
                patientModel.setGender(gender);
                patientModel.setDateOfBirth(dateOfBirth);
                patientModel.setRoleId("1");
                patientModel.setMobile("+" + editTextMobile.getText().toString());
                registerPatient = patientModel;
                initTokenServerApi("+" + editTextMobile.getText().toString());
                startActivity(new Intent(SignUpActivity.this, MobileVerificationActivity.class));
                signuP = "yes";
            }


        });

    }

    private void getDate() {

        final Calendar calendar = Calendar.getInstance();
        final int mYear = calendar.get(Calendar.YEAR);
        final int mMonth = calendar.get(Calendar.MONTH);
        final int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        textDateOfBirth.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this,
                    (view1, year, monthOfYear, dayOfMonth) -> {

                        int month = monthOfYear + 1;
                        String day = String.valueOf(dayOfMonth);

                        String stringMonth = String.valueOf(month);

                        if (stringMonth.length() == 1) {
                            stringMonth = "0" + month;
                        }

                        if (day.length() == 1) {
                            day = "0" + day;
                        }

                        dateOfBirth = day + "-" + (stringMonth) + "-" + year;
                        textDateOfBirth.setText(dateOfBirth);

                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

    }


    private void initTokenServerApi(String mobile) {

        String TOKEN_SERVER_URL = "https://eiada.herokuapp.com";

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(TOKEN_SERVER_URL)
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        retrofitInterface.getToken("+" + mobile).enqueue(new Callback<TokenServerResponse>() {
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
