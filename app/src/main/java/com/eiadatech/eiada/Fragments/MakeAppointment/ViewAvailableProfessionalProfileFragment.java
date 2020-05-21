package com.eiadatech.eiada.Fragments.MakeAppointment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Adapters.ReviewAdapter;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.ReviewModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Adapters.AvailableProfessionalsAdapter.selectedProfessional;
import static com.eiadatech.eiada.Fragments.HomeFragment.appointment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAvailableProfessionalProfileFragment extends Fragment {
    private CircleImageView professionalImage;
    private TextView professionalName, professionalLocation, professionalRating, professionalHospitals, professionalSpecialization, professionalFees,
            professionalExperience, professionalUniversity, professionalLicense, professionalShortSummary, textViewAddress;

    private ScrollView scrollView;
    private RecyclerView recyclerViewReviews;
    private Button bookApppointment;
    private TextView btnReviews, btnDetails;

    private ImageView ratingStar1, ratingStar2, ratingStar3, ratingStar4, ratingStar5, backImage;

    public ViewAvailableProfessionalProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_available_professional_profile, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {

        professionalImage = view.findViewById(R.id.profileCircleImageView);
        professionalName = view.findViewById(R.id.textView14);
        professionalLocation = view.findViewById(R.id.textView15);
        professionalRating = view.findViewById(R.id.textView48);
        professionalHospitals = view.findViewById(R.id.textView184);
        professionalSpecialization = view.findViewById(R.id.textView186);
        professionalExperience = view.findViewById(R.id.textView193);
        professionalUniversity = view.findViewById(R.id.textView195);
        textViewAddress = view.findViewById(R.id.textView15);
        professionalLicense = view.findViewById(R.id.textView197);
        professionalShortSummary = view.findViewById(R.id.textView199);
        professionalFees = view.findViewById(R.id.textView189);
        recyclerViewReviews = view.findViewById(R.id.reviewsList);
        scrollView = view.findViewById(R.id.scrollview);
        btnDetails = view.findViewById(R.id.button8);
        btnReviews = view.findViewById(R.id.button19);
        bookApppointment = view.findViewById(R.id.button21);
        ratingStar1 = view.findViewById(R.id.imageView101);
        ratingStar2 = view.findViewById(R.id.imageView99);
        ratingStar3 = view.findViewById(R.id.imageView102);
        ratingStar4 = view.findViewById(R.id.imageView100);
        ratingStar5 = view.findViewById(R.id.imageView104);
        backImage = view.findViewById(R.id.imageView161);


        professionalName.setText(selectedProfessional.getName());
        professionalFees.setText(selectedProfessional.getFees());
        professionalRating.setText(selectedProfessional.getRating());
        professionalUniversity.setText(selectedProfessional.getUniversity());
        professionalShortSummary.setText(selectedProfessional.getAbout());
        professionalExperience.setText(selectedProfessional.getExperience());
        professionalHospitals.setText(selectedProfessional.getHospitals());
        professionalSpecialization.setText(selectedProfessional.getSpecializations());
        professionalLicense.setText(selectedProfessional.getLicenseNumber());
        if (selectedProfessional.getAddress() != null) {
            if (selectedProfessional.getAddress().equalsIgnoreCase("")) {
                textViewAddress.setText("Not Available");
            } else {
                textViewAddress.setText(selectedProfessional.getAddress());
            }
        } else {
            textViewAddress.setText("Not Available");
        }

        if (selectedProfessional.getSpecializations() != null) {
            if (selectedProfessional.getSpecializations().equalsIgnoreCase("")) {
                professionalSpecialization.setText("Not available");
            }

        } else {
            professionalSpecialization.setText("Not available");
        }

        if (selectedProfessional.getFees() != null) {
            if (selectedProfessional.getFees().equalsIgnoreCase("")) {
                professionalFees.setText("Not available");
            }
        } else {
            professionalFees.setText("Not available");
        }

        if (selectedProfessional.getUniversity() != null) {
            if (selectedProfessional.getUniversity().equalsIgnoreCase("")) {
                professionalUniversity.setText("Not available");
            }
        } else {
            professionalUniversity.setText("Not available");
        }

        if (selectedProfessional.getExperience() != null) {
            if (selectedProfessional.getExperience().equalsIgnoreCase("")) {
                professionalExperience.setText("Not available");
            }
        } else {
            professionalExperience.setText("Not available");
        }


        if (selectedProfessional.getAbout() != null) {
            if (selectedProfessional.getAbout().equalsIgnoreCase("")) {
                professionalShortSummary.setText("Not available");
            }

        } else {
            professionalShortSummary.setText("Not available");
        }

        if (selectedProfessional.getHospitals() != null) {
            if (selectedProfessional.getHospitals().equalsIgnoreCase("")) {
                professionalHospitals.setText("Not available");
            }
        } else {
            professionalHospitals.setText("Not available");
        }


        if (selectedProfessional.getProfileImage() != null) {
            Picasso.get().load(selectedProfessional.getProfileImage()).into(professionalImage);
        }


        String ratingFirstDigit = selectedProfessional.getRating().substring(0, Math.min(selectedProfessional.getRating().length(), 1));

        if (ratingFirstDigit.equalsIgnoreCase("0")) {
            ratingStar1.setVisibility(View.VISIBLE);
            ratingStar2.setVisibility(View.VISIBLE);
            ratingStar3.setVisibility(View.VISIBLE);
            ratingStar4.setVisibility(View.VISIBLE);
            ratingStar5.setVisibility(View.VISIBLE);
        } else if (ratingFirstDigit.equalsIgnoreCase("1")) {
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

        bookApppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                appointment.setProfessionalId(selectedProfessional.getProfessionalId());
                appointment.setSpecialistName(selectedProfessional.getSpecialistName());
                appointment.setProfessionalName(selectedProfessional.getName());
                appointment.setProfessionalImage(selectedProfessional.getProfileImage());
                appointment.setFees(selectedProfessional.getFees());
                appointment.setProfessionalMobile(selectedProfessional.getMobile());
                appointment.setRating(selectedProfessional.getRating());

                SelectAppointmentDateTimeFragment bottomSheetDialog = new SelectAppointmentDateTimeFragment();
                bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "timingsBottomSheet");
            }
        });
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnDetails.setBackground(getActivity().getResources().getDrawable(R.drawable.selected_button_background));
                btnDetails.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));

                btnReviews.setBackground(getActivity().getResources().getDrawable(R.drawable.unselected_button_background));
                btnReviews.setTextColor(getActivity().getResources().getColor(R.color.themeColor));

                scrollView.setVisibility(View.VISIBLE);
                recyclerViewReviews.setVisibility(View.GONE);
            }
        });

        btnReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnReviews.setBackground(getActivity().getResources().getDrawable(R.drawable.selected_button_background));
                btnReviews.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));

                btnDetails.setBackground(getActivity().getResources().getDrawable(R.drawable.unselected_button_background));
                btnDetails.setTextColor(getActivity().getResources().getColor(R.color.themeColor));


                scrollView.setVisibility(View.GONE);
                recyclerViewReviews.setVisibility(View.VISIBLE);
                getProfessionalReviews(selectedProfessional.getProfessionalId());
            }
        });


    }

    private void getProfessionalReviews(String profesionalId) {

        ReviewModel reviewModel = new ReviewModel();
        reviewModel.setProfessionalId(profesionalId);

        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<List<ReviewModel>> apiCall = retrofitInterface.getProfessionalReviews(reviewModel);

        apiCall.enqueue(new Callback<List<ReviewModel>>() {
            @Override
            public void onResponse(Call<List<ReviewModel>> call, Response<List<ReviewModel>> response) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerViewReviews.setLayoutManager(layoutManager);
                recyclerViewReviews.setItemAnimator(new DefaultItemAnimator());
                recyclerViewReviews.setAdapter(new ReviewAdapter(response.body(), getActivity()));

            }

            @Override
            public void onFailure(Call<List<ReviewModel>> call, Throwable t) {
            }
        });
    }


}
