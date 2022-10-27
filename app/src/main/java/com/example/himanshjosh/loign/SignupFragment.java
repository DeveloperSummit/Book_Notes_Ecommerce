package com.example.himanshjosh.loign;

import static com.example.himanshjosh.utlity.ConstantField.regex;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.himanshjosh.MainActivity;
import com.example.himanshjosh.R;
import com.example.himanshjosh.databinding.FragmentLoginBinding;
import com.example.himanshjosh.databinding.FragmentSignupBinding;
import com.example.himanshjosh.loign.responce.getrequest.ModelLoginResponce;
import com.example.himanshjosh.loign.responce.getrequest.signup.ModelLoginResponceSignup;
import com.example.himanshjosh.network.NetworkConfig;
import com.example.himanshjosh.network.NetworkListener;
import com.example.himanshjosh.network.RetrofitService;
import com.example.himanshjosh.utlity.SharedPrefrence;
import com.example.himanshjosh.utlity.UtlityaFunction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import cc.cloudist.acplibrary.ACProgressPie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupFragment extends Fragment {

    private FragmentSignupBinding binding;
    private NetworkListener networkListener;
    private ACProgressFlower progressDialog ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(getLayoutInflater());
        networkListener = RetrofitService.getClient().create(NetworkListener.class);

        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_loginFragment);

            }
        });

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.name.getText().toString().trim().isEmpty() ||
                        binding.lastname.getText().toString().trim().isEmpty() ||
                        binding.email2.getText().toString().trim().isEmpty() ||
                        binding.MobileNumber.getText().toString().trim().isEmpty() ||
                        binding.password2.getText().toString().trim().isEmpty() ||
                        binding.reenterPassword.getText().toString().trim().isEmpty() ||
                        binding.addresh.getText().toString().trim().isEmpty()
                ) {
                    UtlityaFunction.showSnackBar(binding.getRoot(), "Please Enter the Valid Details & fill all fields Properly !");
                } else if (!(emailValidation(binding.email2.getText().toString().trim()))) {
                    UtlityaFunction.showSnackBar(binding.getRoot(), "Please Enter the Valid Email !");
                } else if (!(binding.MobileNumber.getText().toString().trim().length() == 10)) {
                    UtlityaFunction.showSnackBar(binding.getRoot(), "Please Enter the Valid Mobile Number !");

                } else if (!(binding.password2.getText().toString().trim().equals(binding.reenterPassword.getText().toString().trim()))) {
                    UtlityaFunction.showSnackBar(binding.getRoot(), "Please Enter the Same Password !");

                } else {
                    userSignup();
                }


            }
        });


        return binding.getRoot();
    }

    private void userSignup() {

        showProgressBar();
        Call<ModelLoginResponceSignup> call = networkListener.registerUser(binding.name.getText().toString(), binding.lastname.getText().toString(), binding.email2.getText().toString(), binding.MobileNumber.getText().toString(), binding.password2.getText().toString(), binding.addresh.getText().toString());
        call.enqueue(new Callback<ModelLoginResponceSignup>() {
            @Override
            public void onResponse(Call<ModelLoginResponceSignup> call, Response<ModelLoginResponceSignup> response) {
                if (response.body().getStatus()) {
                    SharedPrefrence.setStr(requireActivity(), "LOGIN", "true");
                    SharedPrefrence.setStr(requireActivity(), "UserName", response.body().getCustomer().getFname()+" "+response.body().getCustomer().getLname());
                    SharedPrefrence.setStr(requireActivity(), "UserEmail", response.body().getCustomer().getEmail());
                    SharedPrefrence.setStr(requireActivity(), "UserMob", response.body().getCustomer().getMobile());
                    SharedPrefrence.setStr(requireActivity(), "customerId", response.body().getCustomer().getCustomerid());
                    SharedPrefrence.setStr(requireActivity(), "Address", response.body().getCustomer().getAddress());
                    progressDialog.dismiss();
                    Intent intent = new Intent(requireActivity(), MainActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                } else {
                    UtlityaFunction.showSnackBar(binding.getRoot(),""+response.body().getMessage());
                    progressDialog.dismiss();



                }

            }

            @Override
            public void onFailure(Call<ModelLoginResponceSignup> call, Throwable t) {
                UtlityaFunction.showSnackBar(binding.getRoot(),"" + t.getMessage());
                progressDialog.dismiss();


            }
        });
    }

    private void showProgressBar() {
        progressDialog = new ACProgressFlower.Builder(requireActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Loading...")
                .fadeColor(Color.DKGRAY).build();
        progressDialog.show();
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

}