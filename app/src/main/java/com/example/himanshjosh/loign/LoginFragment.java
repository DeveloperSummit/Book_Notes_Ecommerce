package com.example.himanshjosh.loign;

import static com.example.himanshjosh.utlity.ConstantField.regex;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.himanshjosh.MainActivity;
import com.example.himanshjosh.R;
import com.example.himanshjosh.databinding.FragmentLoginBinding;
import com.example.himanshjosh.loign.responce.getrequest.ModelLoginResponce;
import com.example.himanshjosh.network.NetworkListener;
import com.example.himanshjosh.network.RetrofitService;
import com.example.himanshjosh.utlity.SharedPrefrence;
import com.example.himanshjosh.utlity.UtlityaFunction;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import cc.cloudist.acplibrary.ACProgressPie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment implements PaymentResultListener {

    private FragmentLoginBinding binding;
    private NetworkListener networkListener;
    private ACProgressFlower progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater());



        networkListener = RetrofitService.getClient().create(NetworkListener.class);

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment);

            }
        });



        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtlityaFunction.hideKeyboard(requireActivity());
                if (binding.email.getText().toString().trim().isEmpty() || binding.password.getText().toString().toString().isEmpty()) {
                    UtlityaFunction.showSnackBar(binding.getRoot(), "Please Enter the Valid Email & Password !");

                } else if (!(emailValidation(binding.email.getText().toString().trim()))) {
                    UtlityaFunction.showSnackBar(binding.getRoot(), "Please Enter the Valid Email & Password !");

                } else {
                    userLogin(binding.email.getText().toString().trim(), binding.password.getText().toString().trim());
                }

            }
        });


        binding.tectviewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
            }
        });

        return binding.getRoot();
    }

    private void userLogin(String email, String password) {
        showProgressbar();
        Call<ModelLoginResponce> call = networkListener.loginUser(email, password);
        call.enqueue(new Callback<ModelLoginResponce>() {
            @Override
            public void onResponse(Call<ModelLoginResponce> call, Response<ModelLoginResponce> response) {
                if (response.body().getStatus()) {

                    SharedPrefrence.setStr(requireActivity(), "LOGIN", "true");
                    SharedPrefrence.setStr(requireActivity(), "UserName", response.body().getName());
                    SharedPrefrence.setStr(requireActivity(), "UserEmail", response.body().getEmail());
                    SharedPrefrence.setStr(requireActivity(), "UserMob", response.body().getMobile());
                    SharedPrefrence.setStr(requireActivity(), "customerId", response.body().getCustomerId());
                    SharedPrefrence.setStr(requireActivity(), "Address", response.body().getAddress());
                    Intent intent = new Intent(requireActivity(), MainActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                    progressDialog.dismiss();

                } else {
                    UtlityaFunction.showSnackBar(binding.getRoot(), "Please Enter the Valid Email & Password !");
                    progressDialog.dismiss();

                }


            }

            @Override
            public void onFailure(Call<ModelLoginResponce> call, Throwable t) {
                UtlityaFunction.showSnackBar(binding.getRoot(), "" + t.getMessage());
                progressDialog.dismiss();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        progressDialog=null;
    }

    public boolean emailValidation(String email) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    private void showProgressbar() {
        progressDialog = new ACProgressFlower.Builder(requireActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Loading...")
                .fadeColor(Color.DKGRAY).build();
        progressDialog.show();
    }



    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(requireActivity(), ""+s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(requireActivity(), ""+s, Toast.LENGTH_SHORT).show();

    }
}

