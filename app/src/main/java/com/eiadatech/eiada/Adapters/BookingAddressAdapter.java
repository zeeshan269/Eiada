package com.eiadatech.eiada.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Fragments.AvailableProfessionalsFragment;
import com.eiadatech.eiada.Fragments.BottomSheets.DeleteAddressConfirmationSheet;
import com.eiadatech.eiada.Fragments.UpdateBookingAddressFragment;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.Models.BookingAddressModel;

import java.util.List;

import static com.eiadatech.eiada.Fragments.HomeFragment.appointment;

public class BookingAddressAdapter extends RecyclerView.Adapter<BookingAddressAdapter.ViewHolder> {


    private Context context;
    private List<BookingAddressModel> bookingAddressModelList;
    public static BookingAddressModel selectedBookingAddress;
    public String selectType;

    int index = -1;

    public BookingAddressAdapter(Context context, List<BookingAddressModel> bookingAddressModelList, String type) {
        this.context = context;
        this.bookingAddressModelList = bookingAddressModelList;
        this.selectType = type;
    }

    @NonNull
    @Override
    public BookingAddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_address_row, parent, false);
        return new BookingAddressAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingAddressAdapter.ViewHolder holder, int position) {
        BookingAddressModel bookingAddressModel = bookingAddressModelList.get(position);
        holder.name.setText(bookingAddressModel.getLocationName());
        holder.address.setText(bookingAddressModel.getAddress());
        holder.type.setText(bookingAddressModel.getType());

        String latEiffelTower = bookingAddressModel.getLatitude();
        String lngEiffelTower = bookingAddressModel.getLongitude();
        //   String url = "http://maps.google.com/maps/api/staticmap?center=" + latEiffelTower + "," + lngEiffelTower + "&zoom=15&size=200x200&sensor=false&key=AIzaSyAE32GAF8b-wSsXvGmuzUTbJAG10wUhUQo";

        //    Picasso.get().load(url).into(holder.location);

        if (selectType.equalsIgnoreCase("select")) {
            holder.delete.setVisibility(View.INVISIBLE);
        }

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectType.equalsIgnoreCase("select")) {
                    index = position;
                    notifyDataSetChanged();
                } else {
                    selectedBookingAddress = bookingAddressModelList.get(position);
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new UpdateBookingAddressFragment()).commit();
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedBookingAddress = bookingAddressModelList.get(position);
                final DeleteAddressConfirmationSheet bottomSheetDialog = new DeleteAddressConfirmationSheet();
                bottomSheetDialog.show(((FragmentActivity) context).getSupportFragmentManager(),"evaluationBottomSheet");
            }
        });

        if (selectType.equalsIgnoreCase("select")) {
            if (index == position) {
                appointment.setBookingAddressId(bookingAddressModelList.get(position).getBookingAddressId());
                appointment.setBookingAddress(bookingAddressModelList.get(position).getAddress());

                holder.constraintLayout.setBackground(context.getResources().getDrawable(R.drawable.home_box_selected_background));
                holder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                holder.address.setTextColor(context.getResources().getColor(R.color.colorWhite));
                holder.type.setTextColor(context.getResources().getColor(R.color.colorWhite));

                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new AvailableProfessionalsFragment()).commit();

            } else {
                holder.constraintLayout.setBackground(context.getResources().getDrawable(R.drawable.home_box_background_style));
                holder.name.setTextColor(context.getResources().getColor(R.color.colorGray));
                holder.address.setTextColor(context.getResources().getColor(R.color.colorGray));
                holder.type.setTextColor(context.getResources().getColor(R.color.colorGray));
            }
        }

    }

    @Override
    public int getItemCount() {
        return bookingAddressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView address;
        public TextView type;
        public ImageView delete;
        public ConstraintLayout constraintLayout;
        //  public ImageView location;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView51);
            address = itemView.findViewById(R.id.textView58);
            type = itemView.findViewById(R.id.textView33);
            delete = itemView.findViewById(R.id.imageView68);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            //  location = itemView.findViewById(R.id.mapView);


        }
    }

}
