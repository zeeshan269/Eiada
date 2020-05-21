package com.eiadatech.eiada.Fragments.Documents;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.DocumentModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.eiadatech.eiada.Session;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.eiadatech.eiada.Activities.HomeActivity.navigationView;
import static com.eiadatech.eiada.Fragments.Documents.UploadDocumentsFragment.otherDocuments;
import static com.eiadatech.eiada.Fragments.Documents.UploadPassportFragment.scaleDown;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadOtherDocsFragment extends Fragment {

    private Button btnSubmit;
    private EditText documnetName;
    private Button uploadImage;
    private ImageView uploadedImage;
    private String encodedImage = "";
    private RetrofitInterface retrofitInterface;
    private ConstraintLayout constraintLayout;

    public UploadOtherDocsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_other_docs, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        navigationView.setVisibility(View.GONE);
        btnSubmit = view.findViewById(R.id.button7);
        documnetName = view.findViewById(R.id.textView54);
        uploadImage = view.findViewById(R.id.imageView34);
        uploadedImage = view.findViewById(R.id.imageView33);
        constraintLayout = view.findViewById(R.id.constraintLayout);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        TextView btnBack = view.findViewById(R.id.textView151);
        ImageView imageBack = view.findViewById(R.id.imageView73);

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

        if (otherDocuments != null) {
            if (!otherDocuments.getDocumentNumber().isEmpty()) {
                documnetName.setText(otherDocuments.getDocumentNumber());
                Picasso.get().load(otherDocuments.getImage()).networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE).into(uploadedImage);
            }
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                if (inputMethodManager.isAcceptingText()) {
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                }

                if (documnetName.getText().toString().trim().isEmpty()) {
                    Snackbar.make(constraintLayout, "Please provide document name", Snackbar.LENGTH_SHORT).show();
                } else {

                    if (otherDocuments != null) {
                        if (!otherDocuments.getDocumentNumber().isEmpty()) {
                            updateOtherDocument(Session.getUser_ID(getActivity()), documnetName.getText().toString(), encodedImage);
                        } else {
                            if (encodedImage.isEmpty()) {
                                Snackbar.make(constraintLayout, "Please attach document images", Snackbar.LENGTH_SHORT).show();
                            } else {
                                saveOtherDocument(Session.getUser_ID(getActivity()), documnetName.getText().toString(), encodedImage);
                            }
                        }
                    } else {

                        if (encodedImage.isEmpty()) {
                            Snackbar.make(constraintLayout, "Please attach document images", Snackbar.LENGTH_SHORT).show();
                        } else {
                            saveOtherDocument(Session.getUser_ID(getActivity()), documnetName.getText().toString(), encodedImage);
                        }
                    }


                }
            }
        });

    }


    public void uploadImage() {


        if (!checkIfAlreadyhavePermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            getActivity().startActivityForResult(Intent.createChooser(intent, "Select File"), 304);

        }

    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == 304) {

                Uri productImageURI = data.getData();

                Bitmap bitmap1 = null;
                try {
                    bitmap1 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), productImageURI);
                    Bitmap scale = scaleDown(bitmap1, 400, true);
                    uploadedImage.setImageBitmap(scale);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    scale.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveOtherDocument(String userId, String documentName, String image) {

        retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<DocumentModel> apiCall = retrofitInterface.submitDocuments(new DocumentModel(userId, documentName, image, "", "otherDocuments"));

        apiCall.enqueue(new Callback<DocumentModel>() {
            @Override
            public void onResponse(Call<DocumentModel> call, Response<DocumentModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getType().equalsIgnoreCase("Failed")) {
                        Snackbar.make(constraintLayout, "Server problem!", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(constraintLayout, "Document submitted successfully!", Snackbar.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }
                }
            }

            @Override
            public void onFailure(Call<DocumentModel> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Snackbar.make(constraintLayout, "Internet Problem please try later", Snackbar.LENGTH_SHORT).show();

            }
        });
    }


    private void updateOtherDocument(String userId, String documentName, String image) {

        DocumentModel documentModel = new DocumentModel(userId, documentName, image, "", "otherDocuments");
        documentModel.setDocumentId(otherDocuments.getDocumentId());

        retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<DocumentModel> apiCall = retrofitInterface.updateDocuments(documentModel);

        apiCall.enqueue(new Callback<DocumentModel>() {
            @Override
            public void onResponse(Call<DocumentModel> call, Response<DocumentModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getType().equalsIgnoreCase("Failed")) {
                        Snackbar.make(constraintLayout, "Server problem!", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(constraintLayout, "Document updated successfully!", Snackbar.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }
                }
            }

            @Override
            public void onFailure(Call<DocumentModel> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Snackbar.make(constraintLayout, "Internet Problem please try later", Snackbar.LENGTH_SHORT).show();

            }
        });
    }


}
