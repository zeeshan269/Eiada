package com.eiadatech.eiada.Fragments.BottomSheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eiadatech.eiada.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ConfirmationBottomSheet extends BottomSheetDialogFragment {

    private int progressValue = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.confirmation_sheet, container, false);
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
                final EvaluationBottomSheet bottomSheetDialog = new EvaluationBottomSheet();
                bottomSheetDialog.show(getActivity().getSupportFragmentManager(),"evaluationBottomSheet");


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }






}
