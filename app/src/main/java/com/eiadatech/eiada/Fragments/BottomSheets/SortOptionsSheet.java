package com.eiadatech.eiada.Fragments.BottomSheets;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Adapters.AvailableProfessionalsAdapter;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.Models.ProfessionalModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import static com.eiadatech.eiada.Fragments.AvailableProfessionalsFragment.closeFilter;
import static com.eiadatech.eiada.Fragments.AvailableProfessionalsFragment.professionals;
import static com.eiadatech.eiada.Fragments.AvailableProfessionalsFragment.recyclerView;
import static com.eiadatech.eiada.Fragments.AvailableProfessionalsFragment.selectedPrice;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortOptionsSheet extends BottomSheetDialogFragment {


    public static String selectedFilter = "";
    public static String selectedFilterPrice = "0";


    public SortOptionsSheet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sort_options_sheet, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        Button confirm = view.findViewById(R.id.button20);
        CheckedTextView topRated = view.findViewById(R.id.textView200);
        TextView textViewSelectedPrice = view.findViewById(R.id.textView205);
        SeekBar price = view.findViewById(R.id.seekBar2);
        ImageView close = view.findViewById(R.id.imageView175);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFilter = "";
                selectedFilterPrice = "";
                dismiss();
            }
        });


        price.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                selectedFilterPrice = String.valueOf(progress);
                textViewSelectedPrice.setText(selectedFilterPrice);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });


        topRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topRated.isChecked()) {
                    selectedFilter = "";
                    topRated.setChecked(false);
                    topRated.setBackgroundResource(R.drawable.border_box_style_background);
                    topRated.setPadding(8, 12, 12, 12);
                    topRated.setTextColor(getActivity().getResources().getColor(R.color.colorGray));
                } else {
                    topRated.setPadding(8, 12, 12, 12);
                    topRated.setChecked(true);
                    topRated.setBackgroundResource(R.drawable.selected_border_box);
                    topRated.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
                    selectedFilter = "topRated";
                }

            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();


                List<ProfessionalModel> searchedProfessionals = new ArrayList<>();
                for (int i = 0; i < professionals.size(); i++) {
                    String fees = professionals.get(i).getFees().substring(0, professionals.get(i).getFees().indexOf(" "));
                    Double price = Double.valueOf(fees);
                    if (price <= Double.valueOf(selectedFilterPrice)) {
                        searchedProfessionals.add(professionals.get(i));
                    }
                }

                selectedPrice.setVisibility(View.VISIBLE);
                closeFilter.setVisibility(View.VISIBLE);
                selectedPrice.setText("0-" + textViewSelectedPrice.getText().toString() + " AED");

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(new AvailableProfessionalsAdapter(searchedProfessionals, getActivity()));

            }
        });


    }


}
