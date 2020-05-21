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

public class SaveChangesSuccessfully extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_successfully_saved, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(final View view) {
        Button btnConfirm = view.findViewById(R.id.button20);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
                getActivity().onBackPressed();
            }
        });
    }

}
