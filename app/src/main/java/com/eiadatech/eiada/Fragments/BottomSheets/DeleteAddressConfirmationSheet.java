package com.eiadatech.eiada.Fragments.BottomSheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.BookingAddressModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eiadatech.eiada.Adapters.BookingAddressAdapter.selectedBookingAddress;

public class DeleteAddressConfirmationSheet extends BottomSheetDialogFragment {

    private int progressValue = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.delete_address_confirmation_sheet, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        Button btnConfirm = view.findViewById(R.id.button20);
        Button btnCancel = view.findViewById(R.id.button5);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                deleteAddress(selectedBookingAddress.getBookingAddressId());
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }


    private void deleteAddress(String bookingId) {

        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<BookingAddressModel> api = retrofitInterface.deleteBookingAddress(new BookingAddressModel(bookingId));
        api.enqueue(new Callback<BookingAddressModel>() {
            @Override
            public void onResponse(Call<BookingAddressModel> call, Response<BookingAddressModel> response) {
                if (response.isSuccessful()) {
                }
            }

            @Override
            public void onFailure(Call<BookingAddressModel> call, Throwable t) {

            }
        });
    }


}
