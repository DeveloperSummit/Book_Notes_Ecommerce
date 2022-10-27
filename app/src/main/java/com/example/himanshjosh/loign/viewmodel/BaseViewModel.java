package com.example.himanshjosh.loign.viewmodel;

import android.net.Network;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.himanshjosh.loign.responce.getrequest.ModelLoginResponce;
import com.example.himanshjosh.network.NetworkConfig;
import com.example.himanshjosh.repository.MyRepository;

import retrofit2.Response;

public class BaseViewModel extends ViewModel {

    private MyRepository myRepository;
    private NetworkConfig mNetworkConfig;
    public OnCLicklistner onClicklistner;


    public BaseViewModel(MyRepository myRepository, NetworkConfig mNetworkConfig,OnCLicklistner onClicklistner) {
        this.myRepository = myRepository;
        this.mNetworkConfig = mNetworkConfig;
        this.onClicklistner=onClicklistner;
    }



    MutableLiveData<String> _email= new MutableLiveData<String>();
    LiveData<String> email=_email;

    public  void loginButton()
    {

       /* Response<ModelLoginResponceSignup> data=myRepository.loginUser("admin@gmail.com","Admin123");
        if (data.isSuccessful()){
            onClicklistner.success("success");

        }else
        {
            onClicklistner.failed("failed");

        }*/


    }

}



