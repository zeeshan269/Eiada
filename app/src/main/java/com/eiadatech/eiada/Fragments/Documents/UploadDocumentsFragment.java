package com.eiadatech.eiada.Fragments.Documents;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.DocumentModel;
import com.eiadatech.eiada.Retrofit.Models.PatientModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.eiadatech.eiada.Session;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Activities.HomeActivity.navigationView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadDocumentsFragment extends Fragment {

    private ConstraintLayout passport, emirates, insurance, otherDocs;
    private TextView passportNumber, emiratesNumber, insuranceNumber, otherDocsNumber;

    public static DocumentModel passportDocument, emiratesDocument, insuranceDocument, otherDocuments;

    public UploadDocumentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_documents, container, false);
        initializeViews(view);
        return view;
    }


    private void initializeViews(View view) {
        navigationView.setVisibility(View.GONE);
        passport = view.findViewById(R.id.passport);
        emirates = view.findViewById(R.id.emirates);
        insurance = view.findViewById(R.id.insurance);
        otherDocs = view.findViewById(R.id.otherDocs);
        passportNumber = view.findViewById(R.id.textView155);
        emiratesNumber = view.findViewById(R.id.textView158);
        insuranceNumber = view.findViewById(R.id.textView156);
        otherDocsNumber = view.findViewById(R.id.textView157);

        TextView btnBack = view.findViewById(R.id.textView151);
        ImageView imageBack = view.findViewById(R.id.imageView73);

        getUploadedDocuments(Session.getUser_ID(getActivity()));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        passport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new UploadPassportFragment()).commit();
            }
        });
        emirates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new UploadEmiratesIDFragment()).commit();
            }
        });

        insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new UploadInsuranceIDFragment()).commit();
            }
        });

        otherDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new UploadOtherDocsFragment()).commit();

            }
        });

    }

    private void getUploadedDocuments(String patientId) {

        PatientModel patientModel = new PatientModel();
        patientModel.setPatientId(patientId);
        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<List<DocumentModel>> api = retrofitInterface.getUploadedDocuments(patientModel);

        api.enqueue(new Callback<List<DocumentModel>>() {
            @Override
            public void onResponse(Call<List<DocumentModel >> call, Response<List<DocumentModel>> response) {
                if (response.isSuccessful()) {

                    for (int i = 0; i < response.body().size(); i++) {
                        if (response.body().get(i).getType().equalsIgnoreCase("passport")) {
                            passportDocument = response.body().get(i);
                            if (passportDocument.getDocumentNumber() != null) {
                                if(!passportDocument.getDocumentNumber().isEmpty()){
                                    passportNumber.setText(passportDocument.getDocumentNumber());
                                }else{
                                    passportNumber.setText("No documents added");
                                }
                            }else{
                                passportNumber.setText("No documents added");
                            }
                        } else if (response.body().get(i).getType().equalsIgnoreCase("emirates")) {
                            emiratesDocument = response.body().get(i);
                            if (emiratesDocument.getDocumentNumber() != null) {
                                if(!emiratesDocument.getDocumentNumber().isEmpty()){
                                    emiratesNumber.setText(emiratesDocument.getDocumentNumber());
                                }else{
                                    emiratesNumber.setText("No documents added");
                                }
                            }else{
                                emiratesNumber.setText("No documents added");
                            }
                        } else if (response.body().get(i).getType().equalsIgnoreCase("insurance")) {
                            insuranceDocument = response.body().get(i);
                            if (insuranceDocument.getDocumentNumber() != null) {
                                if(!insuranceDocument.getDocumentNumber().isEmpty()){
                                    insuranceNumber.setText(insuranceDocument.getDocumentNumber());
                                }else{
                                    insuranceNumber.setText("No documents added");
                                }
                            }else{
                                insuranceNumber.setText("No documents added");
                            }
                        } else if (response.body().get(i).getType().equalsIgnoreCase("otherDocuments")) {
                            otherDocuments = response.body().get(i);
                            if (otherDocuments.getDocumentNumber() != null) {
                                if(!otherDocuments.getDocumentNumber().isEmpty()){
                                    otherDocsNumber.setText(otherDocuments.getDocumentNumber());
                                }else{
                                    otherDocsNumber.setText("No documents added");
                                }
                            }else{
                                otherDocsNumber.setText("No documents added");
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DocumentModel>> call, Throwable t) {

            }
        });
    }


}
