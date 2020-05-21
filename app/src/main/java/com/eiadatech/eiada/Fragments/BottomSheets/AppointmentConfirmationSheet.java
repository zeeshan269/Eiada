package com.eiadatech.eiada.Fragments.BottomSheets;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.eiadatech.eiada.Fragments.HistoryFragment;
import com.eiadatech.eiada.Fragments.MakeAppointment.BookingConfirmationFragment;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.AppointmentModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.eiadatech.eiada.Adapters.AvailableProfessionalsAdapter.selectedProfessional;
import static com.eiadatech.eiada.Adapters.SlotsAdapter.selectedSlot;
import static com.eiadatech.eiada.Fragments.HomeFragment.appointment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentConfirmationSheet extends BottomSheetDialogFragment {

    private PayPalConfiguration payPalConfiguration;
    private String client = "AdXk402gviz0Ly4bWWSiuLJbR2VEIk8GyHLejYcWQSRpS7bCZrCX7tj7e2inQYu_Wa-f0EFu4J8VXvdL";

    public AppointmentConfirmationSheet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointment_confirmation_sheet, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        Button btnYes = view.findViewById(R.id.button20);
        Button btnBack = view.findViewById(R.id.button5);
        TextView textView = view.findViewById(R.id.textView110);
        TextView textView1 = view.findViewById(R.id.textView91);
        textView.setText("To confirm request you are going to pay " + appointment.getFees());
        textView1.setText("PayPal account");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        payPalConfiguration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION).clientId(client);
        Intent i = new Intent(getActivity(), PayPalService.class);
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
        getActivity().startService(i);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String price = appointment.getFees().substring(0, appointment.getFees().indexOf(" "));
                Double priceIndirham = Double.valueOf(price);
                Double priceInDollar = priceIndirham * 0.27224;

                PayPalPayment payPalPayment = new PayPalPayment(BigDecimal.valueOf(priceInDollar), "USD", "Appointment Fees", PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
                startActivityForResult(intent, 1122);


            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1122) {
            if (resultCode == Activity.RESULT_OK) {
                addAppointment(appointment);
            }
        }
    }

    private void addAppointment(AppointmentModel appointmentModel) {

        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<AppointmentModel> apiCall = retrofitInterface.addAppointment(appointmentModel);

        apiCall.enqueue(new Callback<AppointmentModel>() {
            @Override
            public void onResponse(Call<AppointmentModel> call, Response<AppointmentModel> response) {
                if (response.isSuccessful()) {
                   dismiss();
                    if (selectedProfessional.getMessageNotifications().equalsIgnoreCase("yes")) {
                        sendSms(appointmentModel.getBookingSlotTimings(), appointment.getProfessionalMobile());
                    }
                    appointment = new AppointmentModel();
                    selectedSlot = null;
                    //     Snackbar.make(constraintLayout, "Appointment added successfully", Snackbar.LENGTH_SHORT).show();

                    FragmentManager fm = ((FragmentActivity) BookingConfirmationFragment.context).getSupportFragmentManager();
                    for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                        fm.popBackStack();
                    }

                   // ((FragmentActivity) BookingConfirmationFragment.context).getSupportFragmentManager().popBackStack();
                    ((FragmentActivity) BookingConfirmationFragment.context).getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HistoryFragment()).commit();


                }
            }

            @Override
            public void onFailure(Call<AppointmentModel> call, Throwable t) {
                //        Snackbar.make(constraintLayout, "Internet problem please try later", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void sendSms(String message, String Number) {

        String body = "You have new Booking " + message;
        String from = "+17146768687";
        String to = Number;

        String base64EncodedCredentials = "Basic " + Base64.encodeToString(
                ("AC46a46ef25a90e9ec633586aa28dd3f9c" + ":" + "6097d04e3c05351565c74abc04e3329a").getBytes(), Base64.NO_WRAP
        );

        Map<String, String> data = new HashMap<>();
        data.put("From", from);
        data.put("To", to);
        data.put("Body", body);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twilio.com/2010-04-01/")
                .build();
        RetrofitInterface api = retrofit.create(RetrofitInterface.class);

        api.sendMessage("AC46a46ef25a90e9ec633586aa28dd3f9c", base64EncodedCredentials, data).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) Log.d("TAG", "onResponse->success");
                else Log.d("TAG", "onResponse->failure");

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TAG", "onFailure");
            }
        });

    }


}
