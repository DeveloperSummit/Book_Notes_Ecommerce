package com.example.himanshjosh.order;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.himanshjosh.R;
import com.example.himanshjosh.cart.CartActivity;
import com.example.himanshjosh.databinding.OrderPlacingBinding;
import com.example.himanshjosh.network.NetworkListener;
import com.example.himanshjosh.network.RetrofitService;
import com.example.himanshjosh.ui.cartitem.model.get.ModelRemoveCartResponce;
import com.example.himanshjosh.utlity.SharedPrefrence;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class order_placing extends AppCompatActivity {
    SeekBar seekBar;
    Button deliver_here, continue_button;
    LinearLayout address, order_summary, linearLayout2;
    ConstraintLayout payment;
    private OrderPlacingBinding binding;
    private ACProgressFlower progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OrderPlacingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        seekBar = binding.seekbar;
        deliver_here = binding.deliverHere;
        address = binding.address;

        seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.icon_color_700, getTheme()), PorterDuff.Mode.SRC_ATOP);
        seekBar.getThumb().setColorFilter(getResources().getColor(R.color.icon_color_700, getTheme()), PorterDuff.Mode.SRC_ATOP);
        seekBar.setProgress(10);




        binding.username.setText(SharedPrefrence.getStr(this, "UserName"));
        binding.userMobile.setText(SharedPrefrence.getStr(this, "UserMob"));
        binding.userAddresh.setText(SharedPrefrence.getStr(this, "Address"));


        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });
        binding.buttonBottomsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });
        deliver_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(order_placing.this, OrderSummeryNewActivity.class));
            }
        });

      /*  continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order_summary.setVisibility(View.GONE);
                payment.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.VISIBLE);
                seekBar.setProgress(88);
            }
        });
*/
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

    }

    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.addreshdesing);
        ImageView imageView = bottomSheetDialog.findViewById(R.id.button_close);
        EditText addresh = bottomSheetDialog.findViewById(R.id.edit_address);
        EditText city = bottomSheetDialog.findViewById(R.id.edit_city);
        EditText state = bottomSheetDialog.findViewById(R.id.edit_state);
        EditText country = bottomSheetDialog.findViewById(R.id.edit_Country);
        Button updateAddresh = bottomSheetDialog.findViewById(R.id.button_updateaddresh);
        String uid = SharedPrefrence.getStr(order_placing.this, "customerId");

        NetworkListener networkListener = RetrofitService.getClient().create(NetworkListener.class);
        updateAddresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (addresh.getText().toString().trim().isEmpty() || city.getText().toString().trim().isEmpty() || state.getText().toString().trim().isEmpty() || country.getText().toString().trim().isEmpty() || uid.isEmpty()) {
                    Toast.makeText(order_placing.this, "please fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    showProgressBar();
                    Call<ModelRemoveCartResponce> call = networkListener.onAddAddress(uid, addresh.getText().toString(), city.getText().toString(), state.getText().toString(), country.getText().toString());
                    call.enqueue(new Callback<ModelRemoveCartResponce>() {
                        @Override
                        public void onResponse(Call<ModelRemoveCartResponce> call, Response<ModelRemoveCartResponce> response) {
                            if (response.body().getStatus()) {
                                progressDialog.dismiss();
                                bottomSheetDialog.dismiss();
                                Toast.makeText(order_placing.this, "Address Create Successfully !", Toast.LENGTH_SHORT).show();
                                SharedPrefrence.setStr(order_placing.this,"Address",response.body().getAddress());
                                binding.userAddresh.setText(SharedPrefrence.getStr(order_placing.this, "Address"));


                            }else {
                                progressDialog.dismiss();
                                bottomSheetDialog.dismiss();
                                Toast.makeText(order_placing.this, "Address Not Create !", Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call<ModelRemoveCartResponce> call, Throwable t) {
                            progressDialog.dismiss();
                            bottomSheetDialog.dismiss();
                            Toast.makeText(order_placing.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();


    }
    private void showProgressBar() {
        progressDialog = new ACProgressFlower.Builder(order_placing.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Loading...")
                .fadeColor(Color.DKGRAY).build();
        progressDialog.show();
    }
}