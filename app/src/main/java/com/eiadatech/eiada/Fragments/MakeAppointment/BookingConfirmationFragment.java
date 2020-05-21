package com.eiadatech.eiada.Fragments.MakeAppointment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.eiadatech.eiada.Fragments.BottomSheets.AppointmentConfirmationSheet;
import com.eiadatech.eiada.Fragments.BottomSheets.CancelAppointmentFragment;
import com.eiadatech.eiada.R;
import com.paypal.android.sdk.payments.PayPalService;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import static com.eiadatech.eiada.Adapters.AppointmentAdapter.getMonthName;
import static com.eiadatech.eiada.Adapters.AvailableProfessionalsAdapter.selectedProfessional;
import static com.eiadatech.eiada.Fragments.HomeFragment.appointment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingConfirmationFragment extends Fragment {


    private TextView textViewPatient, textViewAddress, textViewDescription, textViewSpecialist, textViewDate, textViewTimings, textViewFees, textViewRating, professionalAddress;
    private Button btnAddAppointment;
    private TextView professionalName;
    //    private ConstraintLayout constraintLayout;

    private ImageView ratingStar1, ratingStar2, ratingStar3, ratingStar4, ratingStar5;
    private Button btnCancel;
    private FrameLayout professionalImage;


    public static Context context;

    public BookingConfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_confirmation, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {

        textViewAddress = view.findViewById(R.id.textView222);
        professionalName = view.findViewById(R.id.textView216);
        textViewPatient = view.findViewById(R.id.textView219);
        professionalImage = view.findViewById(R.id.frameLayout2);
        textViewDescription = view.findViewById(R.id.textView229);
        textViewSpecialist = view.findViewById(R.id.textView217);
        textViewDate = view.findViewById(R.id.textView224);
        textViewTimings = view.findViewById(R.id.textView226);
        btnAddAppointment = view.findViewById(R.id.button26);
        textViewFees = view.findViewById(R.id.textView231);
        textViewRating = view.findViewById(R.id.textView90);
        professionalAddress = view.findViewById(R.id.textView68);
        btnCancel = view.findViewById(R.id.button25);
        //    constraintLayout = view.findViewById(R.id.constraintLayout);

        context = getActivity();

        String date = appointment.getBookingDate().substring(Math.max(appointment.getBookingDate().length() - 5, 0));
        String month = date.substring(0, Math.min(date.length(), 2));
        String day = date.substring(Math.max(date.length() - 2, 0));
        textViewDate.setText(getMonthName(month) + " " + day);

        textViewAddress.setText(appointment.getBookingAddress());
        textViewPatient.setText(appointment.getRelation());
        textViewSpecialist.setText(appointment.getSpecialistName());
        professionalName.setText(appointment.getProfessionalName());
        // textViewDisease.setText(appointment.getSympton());
        textViewDescription.setText(appointment.getReason());
        textViewTimings.setText(appointment.getBookingSlotTimings());
        textViewFees.setText(appointment.getFees());

        if (selectedProfessional.getAddress() != null) {
            if (selectedProfessional.getAddress().equalsIgnoreCase("")) {
                professionalAddress.setText("Not Available");
            } else {
                professionalAddress.setText(selectedProfessional.getAddress());
            }
        }else{
            professionalAddress.setText("Not Available");
        }
        ratingStar1 = view.findViewById(R.id.imageView101);
        ratingStar2 = view.findViewById(R.id.imageView99);
        ratingStar3 = view.findViewById(R.id.imageView102);
        ratingStar4 = view.findViewById(R.id.imageView100);
        ratingStar5 = view.findViewById(R.id.imageView104);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelAppointmentFragment bottomSheetDialog = new CancelAppointmentFragment();
                bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "cancelBottomSheet");
            }
        });


        String ratingFirstDigit = appointment.getRating().substring(0, Math.min(appointment.getRating().length(), 1));

        if (ratingFirstDigit.equalsIgnoreCase("1")) {
            ratingStar2.setVisibility(View.VISIBLE);
            ratingStar3.setVisibility(View.VISIBLE);
            ratingStar4.setVisibility(View.VISIBLE);
            ratingStar5.setVisibility(View.VISIBLE);
        } else if (ratingFirstDigit.equalsIgnoreCase("2")) {
            ratingStar3.setVisibility(View.VISIBLE);
            ratingStar4.setVisibility(View.VISIBLE);
            ratingStar5.setVisibility(View.VISIBLE);
        } else if (ratingFirstDigit.equalsIgnoreCase("3")) {

            ratingStar4.setVisibility(View.VISIBLE);
            ratingStar5.setVisibility(View.VISIBLE);
        } else if (ratingFirstDigit.equalsIgnoreCase("4")) {

            ratingStar5.setVisibility(View.VISIBLE);
        } else if (ratingFirstDigit.equalsIgnoreCase("5")) {

        }


        if (appointment.getProfessionalImage() != null) {
            Picasso.get().load(appointment.getProfessionalImage()).into(new Target() {

                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    professionalImage.setBackground(new BitmapDrawable(getActivity().getResources(), bitmap));
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }


                @Override
                public void onPrepareLoad(final Drawable placeHolderDrawable) {
                    Log.d("TAG", "Prepare Load");
                }
            });
        }

        textViewRating.setText(appointment.getRating());


        btnAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppointmentConfirmationSheet bottomSheetDialog = new AppointmentConfirmationSheet();
                bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "confirmationBottomSheet");

            }
        });


    }

    @Override
    public void onDestroy() {
        getActivity().stopService(new Intent(getActivity(), PayPalService.class));
        super.onDestroy();
    }


}
