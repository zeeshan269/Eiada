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
import com.eiadatech.eiada.Retrofit.Models.FamilyMemberModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Adapters.FamilyMemberAdapter.selectedFamily;

public class DeleteFamilyMemberConfirmationSheet extends BottomSheetDialogFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.delete_address_confirmation_sheet, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        Button btnConfirm = view.findViewById(R.id.button20);
        Button btnCancel = view.findViewById(R.id.button5);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                deleteFamilyMember(selectedFamily.getFamilyMemberId());
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }


    private void deleteFamilyMember(String familyId) {

        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<FamilyMemberModel> api = retrofitInterface.deleteFamilyMember(new FamilyMemberModel(familyId));
        api.enqueue(new Callback<FamilyMemberModel>() {
            @Override
            public void onResponse(Call<FamilyMemberModel> call, Response<FamilyMemberModel> response) {
                if (response.isSuccessful()) {
                }
            }

            @Override
            public void onFailure(Call<FamilyMemberModel> call, Throwable t) {

            }
        });
    }


}
