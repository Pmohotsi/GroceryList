package com.example.grocerylist;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

public class ApplicationClass extends Application {

    //SharedPreferences used to save User data
    public static SharedPreferences myPrefs;
    public static String currentUserPassword;
    public static final String MY_SHARED_PREFERENCES_NAME = "com.example.grocerylist";
    public static final String PASSWORD = "Password";

    @Override
    public void onCreate() {
        super.onCreate();

        myPrefs = getSharedPreferences(MY_SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        currentUserPassword = myPrefs.getString("Password", "");


    }

    /**
     * @param views to be hidden on given context
     */
    public static void hideViews(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * @param views to be shown on given context
     */
    public static void showViews(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Switch visibility of Views
     *
     * @param showView to be shown
     * @param hideView to be hidden
     */
    public static void switchViews(View showView, View hideView) {
        showViews(showView);
        hideViews(hideView);
    }


    /**
     * Creates a basis of an AlertDialog
     *
     * @param context in which the dialog will be shown
     * @param title   of the AlertDialog
     * @param message to be displayed in the AlertDialog
     * @return AlertDialog.Builder with specified parameters
     */
    public static AlertDialog.Builder buildAlertDialog(Context context, String title,
                                                       String message) {

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setMessage(message);

        return builder;

    }
}
