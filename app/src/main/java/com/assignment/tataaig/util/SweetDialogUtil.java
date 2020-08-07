package com.assignment.tataaig.util;

import android.content.Context;
import android.util.Log;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SweetDialogUtil {

    private static final String TAG = "SweetDialogUtil";
    private Context context;

    public SweetDialogUtil(Context context) {
        this.context = context;
    }

    private void showSweetAlertDialogMessage(String title, String description, int alertType) {
        try {
            new SweetAlertDialog(context, alertType)
                    .setTitleText(title)
                    .setContentText(description)
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    }).show();
        } catch (Exception e) {
            Log.e(TAG, "sweetAlertDialogError..");

        }
    }


    public void showErrorSweetAlertDialog() {
        showSweetAlertDialogMessage("Oops! Something went wrong.", "Please try again later.", SweetAlertDialog.ERROR_TYPE);
    }

    public void showSweetAlertDialogWithMsg(String title, String message) {
        showSweetAlertDialogMessage(title, message, SweetAlertDialog.NORMAL_TYPE);
    }

    public void showNoInternetWarningSweetAlertDialog() {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Internet Connectivity Issue!")
                .setContentText("Please check your internet connectivity & Try again.")
                .show();
    }
}
