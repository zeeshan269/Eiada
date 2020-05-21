package com.eiadatech.eiada.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.Models.ChatModel;
import com.eiadatech.eiada.Session;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.eiadatech.eiada.Adapters.AppointmentAdapter.get12FormatTime;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<ChatModel> chats;
    private Context context;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    public ChatAdapter(List<ChatModel> chats, Context context) {
        this.chats = chats;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        ChatModel chatModel = chats.get(position);
        if (chatModel.getSenderId().equalsIgnoreCase(Session.getUser_ID(context))) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sender_message_row, parent, false);

        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.received_message_row, parent, false);

        }
        return new ChatAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        ChatModel chat = chats.get(position);
        holder.message.setText(chat.getMessage());
        String time;
        if (chat.getCreatedAt()!=null) {
            String date = chat.getCreatedAt().substring(Math.max(chat.getCreatedAt().length() - 8, 0));
            time = date.substring(0, Math.min(date.length(), 5));
            holder.time.setText(get12FormatTime(time));
        } else {
            DateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
            Calendar cal = Calendar.getInstance();
            time =  simpleDateFormat.format(cal.getTime());
            holder.time.setText(time  );
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView message;
        public TextView name;
        public TextView time;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.textView59);

            time = itemView.findViewById(R.id.textView258);

        }
    }
}
