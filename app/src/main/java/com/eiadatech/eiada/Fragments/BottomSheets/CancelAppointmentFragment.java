package com.eiadatech.eiada.Fragments.BottomSheets;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.eiadatech.eiada.Fragments.HomeFragment;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.Models.AppointmentModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import static com.eiadatech.eiada.Fragments.HomeFragment.appointment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CancelAppointmentFragment extends BottomSheetDialogFragment {


    public CancelAppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cancel_appointment, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        Button btnYes = view.findViewById(R.id.button20);
        Button btnNo = view.findViewById(R.id.button5);


        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                appointment = null;
                appointment = new AppointmentModel();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();

            }
        });


    }


}
