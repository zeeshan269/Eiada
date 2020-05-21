package com.eiadatech.eiada;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.eiadatech.eiada.Activities.HomeActivity;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    String type = "";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            type = "json";
            showNotification(remoteMessage.getData().get("message"));
        }

        if (remoteMessage.getNotification() != null) {
            type = "message";
            showNotification(remoteMessage.getNotification().getBody());
        }

    }

    private void showNotification(String messageBody) {

        String id = "", message = "", title = "";

        if (type.equalsIgnoreCase("json")) {
            try {
                JSONObject jsonObject = new JSONObject(messageBody);
                id = jsonObject.getString("id");
                message = jsonObject.getString("message");
                title = jsonObject.getString("title");
//                Order order = new Order();
//                order.setId(Integer.parseInt(title));
//                selectedOrder = order;


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (type.equalsIgnoreCase("message")) {
            message = messageBody;
        }


        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setAutoCancel(true);
        builder.setContentTitle("Eiada Health Care");
        builder.setContentText(message);
        builder.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark);

        Uri soundURi = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        builder.setSound(soundURi);
//        Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
//        vibrator.vibrate(1000);
        builder.setContentIntent(pendingIntent);


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }
}
