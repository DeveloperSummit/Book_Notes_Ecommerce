package com.example.himanshjosh.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.himanshjosh.MainActivity;
import com.example.himanshjosh.databinding.FragmentGalleryBinding;
import com.example.himanshjosh.loign.LoignBaseActivity;
import com.example.himanshjosh.utlity.SharedPrefrence;

public class GalleryFragment extends Fragment {


    private FragmentGalleryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(getLayoutInflater());

        binding.textView.setText("Name : " + SharedPrefrence.getStr(requireActivity(), "UserName"));
        binding.textView8.setText("Email : " + SharedPrefrence.getStr(requireActivity(), "UserEmail"));
        binding.textView9.setText("Mobile No : " + SharedPrefrence.getStr(requireActivity(), "UserMob"));
        binding.textView10.setText("Address : " + SharedPrefrence.getStr(requireActivity(), "Address"));


        binding.logoutnutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefrence.sharedPrefenceClear(requireActivity());
                Toast.makeText(requireActivity(), "Logout Successfully !", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(requireActivity(), LoignBaseActivity.class);
                startActivity(intent);
                requireActivity().finish();

                // Navigation.findNavController(binding.getRoot()).navigate(R.id.action_logoutFragment_to_loginFragment2);


            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
