package com.eiadatech.eiada.Fragments.BottomSheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.PatientModel;
import com.eiadatech.eiada.Retrofit.Models.TokenModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.eiadatech.eiada.Session;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogoutConfirmationBottomSheet extends BottomSheetDialogFragment {

    private int progressValue = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.logout_confirmation, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        Button btnConfirm = view.findViewById(R.id.button20);
        Button btnCancel = view.findViewById(R.id.button5);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expireSession();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }


    private void expireSession() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressValue < 100) {

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressValue += 1;
                }

                deleteDeviceToken(Session.getUser_ID(getActivity()), Session.getDevice_Token(getActivity()));

            }
        }).start();


    }


    private void deleteDeviceToken(String userId, String token) {
        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<TokenModel> apiCall = retrofitInterface.deleteToken(new TokenModel(userId, token));
        apiCall.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (response.isSuccessful()) {
                    Session.setUser(getActivity(), new PatientModel("", "", "", "", ""));
                    Session.saveRememberPassword(getActivity(), "false");
                    Session.saveToken(getActivity(), "");
                    Session.savePrimaryAddress(getActivity(), "");

                    dismiss();

                    final GoodByeBottomSheet bottomSheetDialog = new GoodByeBottomSheet();
                    bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "goodByeBottomSheet");
                }
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                Session.setUser(getActivity(), new PatientModel("", "", "", "", ""));
                Session.saveRememberPassword(getActivity(), "false");
                Session.saveToken(getActivity(), "");
                Session.savePrimaryAddress(getActivity(), "");
            }
        });

    }


}
