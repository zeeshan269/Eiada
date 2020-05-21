package com.eiadatech.eiada.Fragments.MakeAppointment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.eiadatech.eiada.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import static com.eiadatech.eiada.Fragments.HomeFragment.appointment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SymptonDescriptionFragment extends BottomSheetDialogFragment {


    private EditText editTextDescription;
    private Button btnContinue;

    public SymptonDescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sympton_description, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {

        editTextDescription = view.findViewById(R.id.editText7);
        btnContinue = view.findViewById(R.id.button12);
        ImageView dismiss = view.findViewById(R.id.imageView148);

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = editTextDescription.getText().toString();
                dismiss();
                if (description.isEmpty()) {
                    Toast.makeText(getActivity(), "Please provide description", Toast.LENGTH_SHORT).show();
                } else {
                    appointment.setReason(description);
                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new SelectAddressFragment()).commit();

                }
            }
        });


    }

}
