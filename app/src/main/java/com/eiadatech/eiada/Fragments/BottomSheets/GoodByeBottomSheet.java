package com.eiadatech.eiada.Fragments.BottomSheets;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eiadatech.eiada.Activities.LoginActivity;
import com.eiadatech.eiada.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class GoodByeBottomSheet extends BottomSheetDialogFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.good_bye_bottom_sheet, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        Button btnConfirm = view.findViewById(R.id.button20);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
    }


}
