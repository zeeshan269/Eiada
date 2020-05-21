package com.eiadatech.eiada.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.Models.SlotModel;

import java.util.List;

import static com.eiadatech.eiada.Fragments.BookingDateFragment.btnBook;
import static com.eiadatech.eiada.Fragments.BookingDateFragment.date;

public class SlotsAdapter extends RecyclerView.Adapter<SlotsAdapter.ViewHolder> {

    private List<SlotModel> slots;
    private Context context;
    public static SlotModel selectedSlot;

    public SlotsAdapter(List<SlotModel> slots, Context context) {
        this.slots = slots;
        this.context = context;
    }

    @NonNull
    @Override
    public SlotsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.slot_row, parent, false);
        return new SlotsAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull SlotsAdapter.ViewHolder holder, int position) {
        SlotModel slotModel = slots.get(position);
        holder.name.setText(slotModel.getName());

        String startTime = slotModel.getStartTime().substring(0, Math.min(slotModel.getStartTime().length(), 5));
        String endTime = slotModel.getEndTime().substring(0, Math.min(slotModel.getEndTime().length(), 5));

        holder.timings.setText(startTime + " - " + endTime);
        holder.status.setText(slotModel.getStatus());

    }

    @Override
    public int getItemCount() {
        return slots.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView status;
        public TextView timings;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView28);
            status = itemView.findViewById(R.id.textView29);
            timings = itemView.findViewById(R.id.textView31);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedSlot = slots.get(getLayoutPosition());
                    if (selectedSlot.getStatus().equalsIgnoreCase("reserved")) {
                        Toast.makeText(context, "Sorry! slot is already reserved", Toast.LENGTH_SHORT).show();
                    } else {

                        btnBook.setText(date);
                    }
                }
            });


        }


    }


}

