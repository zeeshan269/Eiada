package com.eiadatech.eiada.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.Adapters.ChatAdapter;
import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.APIClient;
import com.eiadatech.eiada.Retrofit.Models.ChatModel;
import com.eiadatech.eiada.Retrofit.Models.PatientModel;
import com.eiadatech.eiada.Retrofit.RetrofitInterface;
import com.eiadatech.eiada.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.eiadatech.eiada.Activities.HomeActivity.navigationView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView btnSend;
    private EditText message;
    List<ChatModel> chats = new ArrayList<>();

    Handler handler = new Handler();
    int apiDelayed = 10 * 1000; //1 second=1000 milisecond, 5*1000=5seconds
    Runnable runnable;

    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        navigationView.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.chatsList);
        message = view.findViewById(R.id.editText23);
        btnSend = view.findViewById(R.id.textView257);
        getChats(Session.getUser_ID(getActivity()));

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



        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                if (inputMethodManager.isAcceptingText()) {
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                }
                if (!message.getText().toString().isEmpty()) {
                    sendMessage(Session.getUser_ID(getActivity()), Session.getUser_ID(getActivity()), "1", Session.getUser_Name(getActivity()), message.getText().toString().trim());
                    message.setText("");
                }
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                //do your function;

                getChats(Session.getUser_ID(getActivity()));

                handler.postDelayed(runnable, apiDelayed);
            }
        }, apiDelayed);


    }

    private void getChats(String userId) {

        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<List<ChatModel>> apiCall = retrofitInterface.getChats(new PatientModel(userId));

        apiCall.enqueue(new Callback<List<ChatModel>>() {
            @Override
            public void onResponse(Call<List<ChatModel>> call, Response<List<ChatModel>> response) {
                if (response.isSuccessful()) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(new ChatAdapter(response.body(), getActivity()));
                    if (response.body().size() != 0) {
                        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ChatModel>> call, Throwable t) {
                Toast.makeText(getActivity(), "Internet Problem please try later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible
    }

    private void sendMessage(String userId, String senderId, final String receiverId, String name, String message) {
        RetrofitInterface retrofitInterface = APIClient.getClient().create(RetrofitInterface.class);
        Call<List<ChatModel>> apiCall = retrofitInterface.sendMessage(new ChatModel(userId, senderId, receiverId, name, message));

        apiCall.enqueue(new Callback<List<ChatModel>>() {
            @Override
            public void onResponse(Call<List<ChatModel>> call, Response<List<ChatModel>> response) {
                if (response.isSuccessful()) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(new ChatAdapter(response.body(), getActivity()));
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                }
            }

            @Override
            public void onFailure(Call<List<ChatModel>> call, Throwable t) {

            }
        });

    }

}
