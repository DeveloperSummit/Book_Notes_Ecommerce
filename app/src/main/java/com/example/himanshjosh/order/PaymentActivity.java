package com.example.himanshjosh.order;

import static com.example.himanshjosh.utlity.ConstantField.PAYMENT_KEY;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.himanshjosh.R;
import com.example.himanshjosh.databinding.ActivityPaymentBinding;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity  {

    private static final String TAG = "PaymentActivity";

    private ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Checkout.preload(getApplicationContext());

        binding.seekbar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.icon_color_700, getTheme()), PorterDuff.Mode.SRC_ATOP);
        binding.seekbar.getThumb().setColorFilter(getResources().getColor(R.color.icon_color_700, getTheme()), PorterDuff.Mode.SRC_ATOP);
        binding.seekbar.setProgress(100);


    }


}