package com.example.himanshjosh.produts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.himanshjosh.cart.CartActivity;
import com.example.himanshjosh.databinding.ActivityProductBinding;
import com.example.himanshjosh.network.NetworkListener;
import com.example.himanshjosh.network.RetrofitService;
import com.example.himanshjosh.produts.adpter.AdapterProduts;
import com.example.himanshjosh.ui.home.listner.OnClickListner;
import com.example.himanshjosh.ui.home.model.get.ModelGetProductByCate;
import com.example.himanshjosh.utlity.UtlityaFunction;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import cc.cloudist.acplibrary.ACProgressPie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity implements OnClickListner {

    private ActivityProductBinding binding;
    private NetworkListener networkListener;
    private ACProgressFlower progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        networkListener = RetrofitService.getClient().create(NetworkListener.class);
        showProgressDialog();

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

        binding.imageCartitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductActivity.this, CartActivity.class));

            }
        });

        if (getIntent().getStringExtra("cateID") != null && !(getIntent().getStringExtra("cateID").isEmpty())) {
            Call<ModelGetProductByCate> event = networkListener.getProductByCate(getIntent().getStringExtra("cateID"));
            event.enqueue(new Callback<ModelGetProductByCate>() {
                @Override
                public void onResponse(Call<ModelGetProductByCate> call, Response<ModelGetProductByCate> response) {
                    if (response.body().getStatus()) {
                        getProductListByCate(response.body());
                        progressDialog.dismiss();
                        binding.recyviewNewgrid.setVisibility(View.VISIBLE);
                        binding.noDataFound.setVisibility(View.GONE);

                    } else {
                        progressDialog.dismiss();
                        binding.recyviewNewgrid.setVisibility(View.GONE);
                        binding.noDataFound.setVisibility(View.VISIBLE);


                    }


                }

                @Override
                public void onFailure(Call<ModelGetProductByCate> call, Throwable t) {

                    progressDialog.dismiss();
                    binding.recyviewNewgrid.setVisibility(View.GONE);
                    binding.noDataFound.setVisibility(View.VISIBLE);


                }
            });


        } else {
            progressDialog.dismiss();
            binding.recyviewNewgrid.setVisibility(View.GONE);
            binding.noDataFound.setVisibility(View.VISIBLE);
        }


    }

    private void showProgressDialog() {
        progressDialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Loading...")
                .fadeColor(Color.DKGRAY).build();
        progressDialog.show();
    }


    private void getProductListByCate(ModelGetProductByCate body) {
        AdapterProduts adpterGrid = new AdapterProduts(this, body, this);
        binding.recyviewNewgrid.setAdapter(adpterGrid);
        binding.recyviewNewgrid.setHasFixedSize(true);
        binding.recyviewNewgrid.setLayoutManager(new GridLayoutManager(this, 2));



    }

    @Override
    public void onEvent(String id) {
        startActivity(new Intent(this, ProductDetailsActivity.class).putExtra("pid", id));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onStop() {
        super.onStop();
        progressDialog = null;
    }
}


