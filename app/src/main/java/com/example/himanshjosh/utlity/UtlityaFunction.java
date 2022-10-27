package com.example.himanshjosh.utlity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import androidx.fragment.app.FragmentActivity;

import com.google.android.material.snackbar.Snackbar;

public class UtlityaFunction {

    private static ProgressDialog progressDialog = null;
    private static  Snackbar snackbar=null;


    public static ProgressDialog ShowProgressDialog(FragmentActivity context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMax(100);
            progressDialog.setMessage("Loading....");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);

        }

        return progressDialog;
    }


    public  static  void showSnackBar(View view, String lable)
    {
        Snackbar.make(view, lable, Snackbar.LENGTH_SHORT).show();
    }


    public  static  void dismiss()
    {
        progressDialog.dismiss();
    }






    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }






}
