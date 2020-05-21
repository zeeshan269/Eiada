package com.eiadatech.eiada.Activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.eiadatech.eiada.Fragments.HistoryFragment;
import com.eiadatech.eiada.Fragments.HomeFragment;
import com.eiadatech.eiada.Fragments.ReportsFragment;
import com.eiadatech.eiada.Fragments.SettingsFragment;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.Models.PayPalToken;
import com.eiadatech.eiada.Retrofit.Models.TwilioModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;

import org.apache.http.HttpResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.eiadatech.eiada.Activities.SignUpActivity.ACCOUNT_SID;
import static com.eiadatech.eiada.Activities.SignUpActivity.AUTH_TOKEN;

public class HomeActivity extends AppCompatActivity {

    public static BottomNavigationView navigationView;
    public static String currentFragment = "Home";

    boolean doubleBackToExitPressedOnce = false;


    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.home:

                    currentFragment = "Home";
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
                    return true;
                case R.id.reservation:

                    currentFragment = "";
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HistoryFragment()).commit();
                    return true;
                case R.id.reports:

                    currentFragment = "";
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new ReportsFragment()).commit();
                    return true;
                case R.id.settings:
                    currentFragment = "";
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new SettingsFragment()).commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
        getToken();
        // sendVerifiation();
        sendCode();

        printKeyHash();
//getSer();


//        Service service = Service.creator("My First Verify Service").create();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
        fragment.onActivityResult(requestCode, resultCode, data);

    }

    private void sendVerifiation() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);


        Verification verification = Verification.fetcher(
                "VAb363d184007a32218388baec227cde1c",
                "AC46a46ef25a90e9ec633586aa28dd3f9c")
                .fetch();

        //   Service service = Service.creator("Eiada Health care").create();


//        Verification verification = Verification.creator(
//               service.getSid(),
//                "+971563302017",
//                "sms")
//                .create();


    }

    private void getToken() {

        String base64EncodedCredentials = "Basic " + Base64.encodeToString(
                ("AdBJ83lRUAvV7dRYm1VFWgqrF9Ma_H09F_3gpgEVwMcmQjbXj8JzQl2HWhsicCsJGtfc9RLrlaA0lsEe" + ":" + "EEXHiQ9H7NSjRBqpDotDHFv6H6pJy0tL3yPpK8vcEI75e1elUzg88hROYPj54v0Tcx4zskLVexYKGjWb").getBytes(), Base64.NO_WRAP
        );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.sandbox.paypal.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitInterface api = retrofit.create(RetrofitInterface.class);

        api.getToken(base64EncodedCredentials, "client_credentials").enqueue(new Callback<PayPalToken>() {
            @Override
            public void onResponse(Call<PayPalToken> call, Response<PayPalToken> response) {
                if (response.isSuccessful()) Log.d("MessageFailure", response.body().getToken());
                else Log.d("MessageFailure", "onResponse->failure");
            }

            @Override
            public void onFailure(Call<PayPalToken> call, Throwable t) {
                Log.d("MessageFailure", t.getMessage());
            }
        });


    }


    private void sendCode() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://verify.twilio.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitInterface api = retrofit.create(RetrofitInterface.class);

        api.sendCode(ACCOUNT_SID + ":" + AUTH_TOKEN, "VA6e4ffebfa7fd39651b9e6eddeaaf0c80", "+971563302017", "sms", "code").enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccessful()) Log.d("CodeFailure", "success");
                else Log.d("CodeFailure", "onResponse->failure");
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                Log.d("CodeFailure", t.getMessage());
            }
        });


    }


    @Override
    public void onBackPressed() {

        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() >= 1) {
            navigationView.setVisibility(View.GONE);
            fm.popBackStack();
            return;

        } else {

            if (doubleBackToExitPressedOnce) {
                navigationView.setVisibility(View.VISIBLE);
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Snackbar.make(this.getWindow().getDecorView().findViewById(android.R.id.content), "Please click back again to exit", Snackbar.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }


    private void getSer() {

        String base64EncodedCredentials = Base64.encodeToString(
                (ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(), Base64.NO_WRAP
        );
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://verify.twilio.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitInterface api = retrofit.create(RetrofitInterface.class);

        api.getSer((ACCOUNT_SID + ":" + AUTH_TOKEN)).enqueue(new Callback<TwilioModel>() {
            @Override
            public void onResponse(Call<TwilioModel> call, Response<TwilioModel> response) {
                if (response.isSuccessful()) {
                    Log.d("HelloBuddy", response.body().getAccount_side());
                }
            }

            @Override
            public void onFailure(Call<TwilioModel> call, Throwable t) {
                Log.d("HelloBuddy", t.getMessage());
            }
        });


    }


    public void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("keyhash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }


}
