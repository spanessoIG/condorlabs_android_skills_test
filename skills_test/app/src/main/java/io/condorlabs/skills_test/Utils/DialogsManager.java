package io.condorlabs.skills_test.Utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by sebastiangomez on 24/02/16.
 */

public class DialogsManager {

    public static interface DialogMessageResponseHandler {
        public void onAlertPositive(String message);

        public void onAlertNegative();
    }
    public static interface DialogResponseHandler {
        public void onAlertPositive();

        public void onAlertNegative();
    }

    public interface SimpleDialogResponseHandler {
        public void onAccept();
    }

    public static void showSimpleDialog(String title, String message, Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setTitle(title);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public static void showPutMessageDialog(String title, String message, String positiveButtonTitle,
                                            String negativeButtonTitle, Context context, final DialogMessageResponseHandler handler) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setTitle(title);
        builder.setCancelable(false);

        final EditText activityObservation = new EditText(context);
        builder.setView(activityObservation);

        builder.setPositiveButton(positiveButtonTitle, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                handler.onAlertPositive(activityObservation.getText().toString());
            }
        });

        builder.setNegativeButton(negativeButtonTitle, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                handler.onAlertNegative();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }




    public static void showSimpleDialogListener(String title, String message, Context context, final SimpleDialogResponseHandler handler) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();
                handler.onAccept();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showDesicionDialog(String title, String message, String positiveButtonTitle,
                                          String negativeButtonTitle, Context context, final DialogResponseHandler handler) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        builder.setMessage(message).setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton(positiveButtonTitle, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                handler.onAlertPositive();

            }
        });
        builder.setNegativeButton(negativeButtonTitle, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                handler.onAlertNegative();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static ProgressDialog createSimpleProgressDialog(String title, String message, Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);

        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        return progressDialog;
    }
}
