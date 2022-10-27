package com.example.himanshjosh.loign;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.himanshjosh.MainActivity;
import com.example.himanshjosh.R;
import com.example.himanshjosh.utlity.SharedPrefrence;
import com.example.himanshjosh.utlity.UtlityaFunction;


public class SplashFragment extends Fragment {

    private  View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_splash, container, false);

        return  view;

    }

    @Override
    public void onStart() {
        super.onStart();

      String loginStatus= SharedPrefrence.getStr(requireActivity(),"LOGIN");
        if (Boolean.valueOf(loginStatus))
        {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(requireActivity(), MainActivity.class);
                    startActivity(intent);
                    requireActivity().finish();

                }
            },1000);
        }else {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment);
                }
            },1000);
        }








    }
}