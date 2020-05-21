package com.eiadatech.eiada.Fragments.BottomSheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.eiadatech.eiada.Fragments.Appointments.CompletedAppointmentDetailsFragment;
import com.eiadatech.eiada.Fragments.HistoryFragment;
import com.eiadatech.eiada.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ConfirmTermination extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.confirm_termination, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(final View view) {
        Button btnConfirm = view.findViewById(R.id.button20);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
                FragmentManager fm = ((FragmentActivity) CompletedAppointmentDetailsFragment.completedAppointContext).getSupportFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }

                // ((FragmentActivity) BookingConfirmationFragment.context).getSupportFragmentManager().popBackStack();
                ((FragmentActivity) CompletedAppointmentDetailsFragment.completedAppointContext).getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HistoryFragment()).commit();

            }
        });
    }

}
