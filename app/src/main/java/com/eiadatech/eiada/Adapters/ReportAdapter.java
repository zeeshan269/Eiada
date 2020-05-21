package com.eiadatech.eiada.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Fragments.ViewReportFragment;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.Models.ReportModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private List<ReportModel> reports;
    private Context context;
    public static ReportModel selectedReport;


    public ReportAdapter(List<ReportModel> reports, Context context) {
        this.reports = reports;
        this.context = context;
        selectedReport = null;
    }

    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_row, parent, false);
        return new ReportAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ViewHolder holder, int position) {
        ReportModel report = reports.get(position);
        holder.name.setText(report.getProfessionalName());
        holder.specialistType.setText(report.getSpecialist());


        String month = report.getDate().substring(0, Math.min(report.getDate().length(), 3));
        String day = report.getDate().substring(report.getDate().indexOf(",") + 2, report.getDate().length() - 5);


        holder.date.setText("Created " + report.getDate()+", "+report.getTime());
        if (reports.get(position).getProfessionalImage() != null) {
            Picasso.get().load(reports.get(position).getProfessionalImage()).into(holder.doctorImage);
        }else {
            holder.doctorImage.setImageDrawable(context.getResources().getDrawable(R.drawable.defaultu));
        }

    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView specialistType;
        public TextView date;
        public TextView name;
        public ImageView doctorImage;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView8);
            specialistType = itemView.findViewById(R.id.textView136);
            date = itemView.findViewById(R.id.textView105);
            doctorImage = itemView.findViewById(R.id.profileCircleImageView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedReport = reports.get(getAdapterPosition());
                    ((FragmentActivity) itemView.getContext()).getSupportFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.frame, new ViewReportFragment())
                            .commit();
                }
            });
        }
    }

}
