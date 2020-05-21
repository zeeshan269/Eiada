package com.eiadatech.eiada.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.PatientModel;
import com.eiadatech.eiada.Retrofit.Models.TokenModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.eiadatech.eiada.Session;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button btnSignIn;
    private TextView register;
    private EditText editTextEmail, editTextPassword;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ConstraintLayout relativeLayout;
    private ProgressDialog progressDialog;
    private String token = "";
    private CheckedTextView showPassword;
    private TextView forgetPassword;
    private ConstraintLayout btnFacebook = findViewById(R.id.constraintLayout18);
    private ConstraintLayout btnGmail = findViewById(R.id.constraintLayout19);
    private final static int GOOGLE_SIGN_IN = 123;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    GoogleSignInClient mgoogleSignInClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mgoogleSignInClient = GoogleSignIn.getClient(this, gso);
        btnGmail.setOnClickListener(v -> SignInGoogle());

        if(mAuth.getCurrentUser() != null){
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        }
    }

    private void SignInGoogle() {
        Intent signInIntent = mgoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    private void firebaseAuthWithGoogle (GoogleSignInAccount acct){
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
                mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        Log.d("TAG", "signInWithCredential:sucess");

                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    }else {
                        Log.d("TAG","signInWithCredential:failure",task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            String name = user.getDisplayName();
            String email = user.getEmail();
            String photo = String.valueOf(user.getPhotoUrl());
        }
    }


    private void initializeViews() {


        btnSignIn = findViewById(R.id.button);
        register = findViewById(R.id.textView6);
        editTextEmail = findViewById(R.id.editText);
        editTextPassword = findViewById(R.id.editText2);
        relativeLayout = findViewById(R.id.relative);
        showPassword = findViewById(R.id.imageView27);
        forgetPassword = findViewById(R.id.textView4);
        btnGmail = findViewById(R.id.constraintLayout19);

        FirebaseMessaging.getInstance().subscribeToTopic("Eiada");
        token = FirebaseInstanceId.getInstance().getToken();
        FacebookSdk.sdkInitialize(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        LoginManager.getInstance().logOut();
        progressDialog = new ProgressDialog(LoginActivity.this);

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

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loginwith_FB();
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (getCurrentFocus().getWindowToken() != null) {
                    if (inputMethodManager.isAcceptingText()) {
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }
                }
                String email = editTextEmail.getText().toString().trim().toLowerCase();
                String password = editTextPassword.getText().toString().trim();

                if (email.isEmpty() || !(email.matches(emailPattern))) {
                    Snackbar.make(relativeLayout, "Invalid Email", Snackbar.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Snackbar.make(relativeLayout, "Invalid Password", Snackbar.LENGTH_SHORT).show();
                } else {
                    PatientModel patient = new PatientModel(email, password);
                    authenticatePatient(patient);
                }

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                finish();
            }
        });

    }



    private void authenticatePatient(PatientModel patientModel) {
        progressDialog.setMessage("Signing In");
        progressDialog.show();
        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<PatientModel> apiCall = retrofitInterface.authenticatePatient(patientModel);
        apiCall.enqueue(new Callback<PatientModel>() {
            @Override
            public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().getName().equalsIgnoreCase("")) {
                        Snackbar.make(relativeLayout, "Invalid email or password", Snackbar.LENGTH_SHORT).show();
                    } else if (response.body().getName().equalsIgnoreCase("Failed")) {
                        Snackbar.make(relativeLayout, "Server Problem! Please try later", Snackbar.LENGTH_SHORT).show();
                    } else {

                        Session.setUser(LoginActivity.this, new PatientModel(response.body().getName(), response.body().getEmail(), response.body().getPassword(), response.body().getGender(), response.body().getDateOfBirth()));
                        Session.saveRememberPassword(LoginActivity.this, "true");
                        Session.saveToken(LoginActivity.this, token);
                        Session.setUser_ID(LoginActivity.this, response.body().getPatientId());
                        Session.saveMobileNumber(LoginActivity.this, response.body().getMobile());
                        Session.saveDateOfBirth(LoginActivity.this, response.body().getDateOfBirth());
                        Session.savePrimaryAddress(LoginActivity.this, response.body().getAddress());
                        String path = response.body().getImage();
                        if (path == null) {
                            path = "";
                        }
                        Session.saveImage(LoginActivity.this, path);
                        registerDeviceToken(response.body().getPatientId(), token);
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<PatientModel> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(relativeLayout, "Server Problem! Please try later", Snackbar.LENGTH_SHORT).show();
            }
        });

    }


    private void registerDeviceToken(String userId, String token) {
        userId = userId + "u";

        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<TokenModel> apiCall = retrofitInterface.registerToken(new TokenModel(userId, token));

        apiCall.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (response.isSuccessful()) {
                }
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
            }
        });

    }

    // Bottom two function are related to Fb implimentation
    private CallbackManager mCallbackManager;

    public void Loginwith_FB() {

        LoginManager.getInstance()
                .logInWithReadPermissions(LoginActivity.this,
                        Arrays.asList("public_profile", "email"));

        // initialze the facebook sdk and request to facebook for login
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
                Log.d("resp_token", loginResult.getAccessToken() + "");
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("resp", "" + error.toString());
                Toast.makeText(LoginActivity.this, "Login Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }

        });


    }

    private void handleFacebookAccessToken(final AccessToken token) {
        // if user is login then this method will call and
        // facebook will return us a token which will user for get the info of user
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        Log.d("resp_token", token.getToken() + "");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //  iosDialog.show();
                            final String id = Profile.getCurrentProfile().getId();
                            GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject user, GraphResponse graphResponse) {

                                    Log.d("resp", user.toString());
                                    //after get the info of user we will pass to function which will store the info in our server

                                    String fname = "" + user.optString("first_name");
                                    String lname = "" + user.optString("last_name");


                                    if (fname.equals("") || fname.equals("null"))
                                        fname = getResources().getString(R.string.app_name);

                                    if (lname.equals("") || lname.equals("null"))
                                        lname = "";

//                                    Call_Api_For_Signup(""+id,fname
//                                            ,lname,
//                                            "https://graph.facebook.com/"+id+"/picture?width=500&width=500",
//                                            "facebook");

                                }
                            });

                            // here is the request to facebook sdk for which type of info we have required
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "last_name,first_name,email");
                            request.setParameters(parameters);
                            request.executeAsync();
                        } else {

                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            //  handleSignInResult(task);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("TAG", "Google sign in failed", e);
            }
        } else if (mCallbackManager != null)
            mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }

}