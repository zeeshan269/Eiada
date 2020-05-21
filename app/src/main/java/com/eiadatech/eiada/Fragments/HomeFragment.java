package com.eiadatech.eiada.Fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.eiadatech.eiada.Fragments.MakeAppointment.SelectPatientFragment;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.AppointmentModel;
import com.eiadatech.eiada.Retrofit.Models.ProfessionalModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.eiadatech.eiada.Session;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Activities.HomeActivity.navigationView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private TextView name;
    private ConstraintLayout doctors, nursingCare, therapists, animalCare, laboratory;
    public static String type = "";
    public static AppointmentModel appointment;

    private TextView doctorsLabel, totalDoctors, doctorsDescription, nursesLabel, totalNurses, nursesDescription, labsLabel, totalLabs, labsDesripton, veterinarianLabel, totalVeterinarian, veternarianDescription, therapistsLabel, totalTherapists, therapistsDescription;

    public TextView textViewNext;

    private CircleImageView patientImage;
    private TextView textViewName, activeDoctors, activeNurses, activeTherapists, activeAnimalCare, activeLaboratory;

    private ImageView messages,notifications;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        type = "home";
        navigationView.setVisibility(View.VISIBLE);
        appointment = new AppointmentModel();
        name = view.findViewById(R.id.textView5);
        name.setText(Session.getUser_Name(getActivity()));
        doctors = view.findViewById(R.id.cardView2);
        nursingCare = view.findViewById(R.id.cardView);
        therapists = view.findViewById(R.id.cardView4);
        animalCare = view.findViewById(R.id.cardView3);
        laboratory = view.findViewById(R.id.cardView6);
        doctorsLabel = view.findViewById(R.id.textView4);
        totalDoctors = view.findViewById(R.id.textView51);
        doctorsDescription = view.findViewById(R.id.textView108);
        nursesLabel = view.findViewById(R.id.textView6);
        totalNurses = view.findViewById(R.id.textView7);
        nursesDescription = view.findViewById(R.id.textView109);
        labsLabel = view.findViewById(R.id.textView12);
        totalLabs = view.findViewById(R.id.textView13);
        labsDesripton = view.findViewById(R.id.textView143);
        veterinarianLabel = view.findViewById(R.id.textView10);
        totalVeterinarian = view.findViewById(R.id.textView11);
        veternarianDescription = view.findViewById(R.id.textView144);
        therapistsLabel = view.findViewById(R.id.textView8);
        totalTherapists = view.findViewById(R.id.textView9);
        therapistsDescription = view.findViewById(R.id.textView142);
        textViewNext = view.findViewById(R.id.textView145);
        activeDoctors = view.findViewById(R.id.textView51);
        activeNurses = view.findViewById(R.id.textView7);
        activeTherapists = view.findViewById(R.id.textView9);
        activeAnimalCare = view.findViewById(R.id.textView11);
        activeLaboratory = view.findViewById(R.id.textView13);
        messages = view.findViewById(R.id.imageView28);
        notifications = view.findViewById(R.id.imageView35);

        patientImage = view.findViewById(R.id.profileCircleImageView);
        textViewName = view.findViewById(R.id.textView5);

        cardViewTouchListeners();
        getActiveProfessionals();
        clickListeners();

        if (!Session.getImage(getActivity()).equalsIgnoreCase("")) {
            Picasso.get().load(Session.getImage(getActivity())).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(patientImage);
        } else {
            patientImage.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.defaultu));
        }

        patientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new PatientProfileFragment()).commit();

            }
        });

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new ChatsFragment()).commit();

            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new SettingsFragment()).commit();

            }
        });

        textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new PatientProfileFragment()).commit();
            }
        });

        textViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appointment.getProfessionId() != null) {
                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new SelectPatientFragment()).commit();
                }
            }
        });


    }


    private void clickListeners() {
        doctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                appointment.setProfessionId("1");
                appointment.setPatientId(Session.getUser_ID(getActivity()));
                textViewNext.setBackgroundResource(R.drawable.theme_circle);

            }
        });


        nursingCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                appointment.setProfessionId("2");
                appointment.setPatientId(Session.getUser_ID(getActivity()));
                textViewNext.setBackgroundResource(R.drawable.theme_circle);

            }
        });


        therapists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                appointment.setProfessionId("3");
                appointment.setPatientId(Session.getUser_ID(getActivity()));
                textViewNext.setBackgroundResource(R.drawable.theme_circle);

            }
        });


        animalCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appointment.setProfessionId("4");
                appointment.setPatientId(Session.getUser_ID(getActivity()));
                textViewNext.setBackgroundResource(R.drawable.theme_circle);

            }
        });

        laboratory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appointment.setProfessionId("5");
                appointment.setPatientId(Session.getUser_ID(getActivity()));
                textViewNext.setBackgroundResource(R.drawable.theme_circle);
            }
        });

        textViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewNext.setBackground(getActivity().getResources().getDrawable(R.drawable.theme_circle));
            }
        });

    }

    private void cardViewTouchListeners() {
        doctors.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                doctors.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_selected_background));
                nursingCare.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));
                therapists.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));
                animalCare.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));
                laboratory.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));


                doctorsLabel.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
                totalDoctors.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
                doctorsDescription.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));

                nursesLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalNurses.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                nursesDescription.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));

                therapistsLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalTherapists.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                therapistsDescription.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));


                veterinarianLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalVeterinarian.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                veternarianDescription.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));

                labsLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalLabs.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                labsDesripton.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));


                return false;
            }
        });

        nursingCare.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                doctors.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));
                nursingCare.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_selected_background));
                therapists.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));
                animalCare.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));
                laboratory.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));

                doctorsLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalDoctors.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                doctorsDescription.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));

                nursesLabel.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
                totalNurses.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
                nursesDescription.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));

                therapistsLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalTherapists.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                therapistsDescription.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));

                veterinarianLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalVeterinarian.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                veternarianDescription.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));

                labsLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalLabs.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                labsDesripton.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));

                return false;
            }
        });

        therapists.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                doctors.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));
                nursingCare.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));
                therapists.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_selected_background));
                animalCare.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));
                laboratory.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));

                doctorsLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalDoctors.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                doctorsDescription.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));

                nursesLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalNurses.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                nursesDescription.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));

                therapistsLabel.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
                totalTherapists.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
                therapistsDescription.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));

                veterinarianLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalVeterinarian.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                veternarianDescription.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));

                labsLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalLabs.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                labsDesripton.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));


                return false;
            }
        });


        animalCare.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                doctors.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));
                nursingCare.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));
                therapists.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));
                animalCare.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_selected_background));
                laboratory.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));

                doctorsLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalDoctors.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                doctorsDescription.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));

                nursesLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalNurses.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                nursesDescription.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));

                therapistsLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalTherapists.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                therapistsDescription.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));

                veterinarianLabel.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
                totalVeterinarian.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
                veternarianDescription.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));

                labsLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalLabs.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                labsDesripton.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));

                return false;
            }
        });

        laboratory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                doctors.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));
                nursingCare.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));
                therapists.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));
                animalCare.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_background_style));
                laboratory.setBackground(getActivity().getResources().getDrawable(R.drawable.home_box_selected_background));

                doctorsLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalDoctors.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                doctorsDescription.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));

                nursesLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalNurses.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                nursesDescription.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));

                therapistsLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalTherapists.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                therapistsDescription.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));


                veterinarianLabel.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                totalVeterinarian.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
                veternarianDescription.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));

                labsLabel.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
                totalLabs.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
                labsDesripton.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));


                return false;
            }
        });


    }


    private void selectPatient() {
        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.home_options_dialog);


        Button btnMySelf = dialog.findViewById(R.id.button10);
        final Button btnFamily = dialog.findViewById(R.id.button11);


        btnMySelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "mySelf";
                dialog.dismiss();

            }
        });

        btnFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "family";
                dialog.dismiss();

            }
        });

        dialog.show();


    }

    private void getActiveProfessionals() {

        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<List<ProfessionalModel>> api = retrofitInterface.getActiveProfessionals();

        api.enqueue(new Callback<List<ProfessionalModel>>() {
            @Override
            public void onResponse(Call<List<ProfessionalModel>> call, Response<List<ProfessionalModel>> response) {
                if (response.isSuccessful()) {

                    for (int i = 0; i < response.body().size(); i++) {
                        if (response.body().get(i).getType().equalsIgnoreCase("Doctor")) {
                            activeDoctors.setText(response.body().get(i).getTotal());
                        } else if (response.body().get(i).getType().equalsIgnoreCase("Nursing Care")) {
                            activeNurses.setText(response.body().get(i).getTotal());
                        } else if (response.body().get(i).getType().equalsIgnoreCase("Therapist")) {
                            activeTherapists.setText(response.body().get(i).getTotal());
                        } else if (response.body().get(i).getType().equalsIgnoreCase("Animal Care")) {
                            activeAnimalCare.setText(response.body().get(i).getTotal());
                        } else if (response.body().get(i).getType().equalsIgnoreCase("Laboratory")) {
                            activeLaboratory.setText(response.body().get(i).getTotal());
                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<List<ProfessionalModel>> call, Throwable t) {

            }
        });
    }


}
