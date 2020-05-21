package com.eiadatech.eiada.Fragments.Appointments;


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

import com.eiadatech.eiada.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import static com.eiadatech.eiada.Activities.HomeActivity.navigationView;
import static com.eiadatech.eiada.Adapters.AppointmentAdapter.getMonthName;
import static com.eiadatech.eiada.Adapters.AppointmentAdapter.selectedAppointment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImplementingAppointmentDetailsFragment extends Fragment {


    private TextView name, specialist, notes, rating, location, address, amount, patientName, date, paymentMethod;
    private FrameLayout frameLayout;
    private Button btnAwaitingImplementation;
    private ImageView btnBack;


    public ImplementingAppointmentDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_implementing_appointment_details, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {

        navigationView.setVisibility(View.GONE);

        name = view.findViewById(R.id.textView216);
        specialist = view.findViewById(R.id.textView42);
        rating = view.findViewById(R.id.textView48);
        address = view.findViewById(R.id.textView47);
        amount = view.findViewById(R.id.textView234);
        patientName = view.findViewById(R.id.textView224);
        date = view.findViewById(R.id.textView219);
        paymentMethod = view.findViewById(R.id.textView229);
        location = view.findViewById(R.id.textView226);
        notes = view.findViewById(R.id.textView231);
        frameLayout = view.findViewById(R.id.frameLayout2);
        btnAwaitingImplementation = view.findViewById(R.id.button26);
        btnBack = view.findViewById(R.id.imageView95);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        try {
            if (selectedAppointment.getRating().length() != 1) {
                String ratingV = selectedAppointment.getRating().substring(0, Math.min(selectedAppointment.getRating().length(), 3));


                rating.setText(ratingV);
            }else{
                rating.setText(selectedAppointment.getRating());
            }
        } catch (NumberFormatException e) {
            rating.setText(selectedAppointment.getRating());
        }


        int professionalRating = (int) Math.floor(Double.parseDouble(selectedAppointment.getRating()));
        setRatingStars(view, professionalRating);

        name.setText(selectedAppointment.getProfessionalName());

        specialist.setText(selectedAppointment.getSpecialistName());
        patientName.setText(selectedAppointment.getPatientName());
        address.setText(selectedAppointment.getBookingAddress());
        location.setText(selectedAppointment.getLocationName());
        amount.setText(selectedAppointment.getFees());

        notes.setText(selectedAppointment.getReason());

        String bookingDate = selectedAppointment.getBookingDate().substring(Math.max(selectedAppointment.getBookingDate().length() - 5, 0));
        String month = bookingDate.substring(0, Math.min(bookingDate.length(), 2));
        String day = bookingDate.substring(Math.max(bookingDate.length() - 2, 0));

        date.setText(getMonthName(month) + ", " + day);


        if (selectedAppointment.getProfessionalImage() != null) {
            Picasso.get().load(selectedAppointment.getProfessionalImage()).into(new Target() {

                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    frameLayout.setBackground(new BitmapDrawable(getActivity().getResources(), bitmap));
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


    }

    private void setRatingStars(View view, int rating) {

        ImageView unselectedStar1 = view.findViewById(R.id.imageView101);
        ImageView unselectedStar2 = view.findViewById(R.id.imageView99);
        ImageView unselectedStar3 = view.findViewById(R.id.imageView102);
        ImageView unselectedStar4 = view.findViewById(R.id.imageView100);
        ImageView unselectedStar5 = view.findViewById(R.id.imageView104);

        if (rating == 1) {

            unselectedStar1.setVisibility(View.INVISIBLE);
        } else if (rating == 2) {
            unselectedStar1.setVisibility(View.INVISIBLE);
            unselectedStar2.setVisibility(View.INVISIBLE);
        } else if (rating == 3) {
            unselectedStar1.setVisibility(View.INVISIBLE);
            unselectedStar2.setVisibility(View.INVISIBLE);
            unselectedStar3.setVisibility(View.INVISIBLE);
        } else if (rating == 4) {
            unselectedStar1.setVisibility(View.INVISIBLE);
            unselectedStar2.setVisibility(View.INVISIBLE);
            unselectedStar3.setVisibility(View.INVISIBLE);
            unselectedStar4.setVisibility(View.INVISIBLE);
        } else if (rating == 5) {
            unselectedStar1.setVisibility(View.INVISIBLE);
            unselectedStar2.setVisibility(View.INVISIBLE);
            unselectedStar3.setVisibility(View.INVISIBLE);
            unselectedStar4.setVisibility(View.INVISIBLE);
            unselectedStar5.setVisibility(View.INVISIBLE);
        }

    }

}
