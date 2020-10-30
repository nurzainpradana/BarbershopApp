package com.firmansyah.barbershop.util;

import android.content.Context;

public class AppUtilits {

    public static void viewMessage(Context mContext, String message) {

        com.firmansyah.barbershop.util.MessageDialog messageDialog = null;
        if (messageDialog == null)
            messageDialog = new com.firmansyah.barbershop.util.MessageDialog(mContext, message);
        messageDialog.viewMessageShow();
    }


}
