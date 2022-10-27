package com.example.himanshjosh.dagger;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class NetworkConnectionInttercepeter implements Interceptor {

    public  Context context;
    private Context applicationCOntext = context.getApplicationContext();


    public NetworkConnectionInttercepeter(Context context){
        context=context;
    }





    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }

   /* private boolean isInternetAvalible() {
         connectivityManager = applicationCOntext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }*/
}
