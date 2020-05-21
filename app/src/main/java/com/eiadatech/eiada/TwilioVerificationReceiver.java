package com.eiadatech.eiada;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.twilio.verification.TwilioVerification;
import com.twilio.verification.external.VerificationStatus;

public class TwilioVerificationReceiver extends BroadcastReceiver {

    public static String verificationCode;

    @Override
    public void onReceive(Context context, Intent intent) {
        VerificationStatus verificationStatus = TwilioVerification.getVerificationStatus(intent);

        // NOT_STARTED, STARTED, AWAITING_VERIFICATION, SUCCESS, ERROR
        verificationCode = String.valueOf(verificationStatus.getState());



    }

   

}
