package com.example.himanshjosh.produts;

import static com.example.himanshjosh.utlity.ConstantField.IMAGE_BASE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.himanshjosh.R;
import com.example.himanshjosh.cart.CartActivity;
import com.example.himanshjosh.databinding.ActivityMainBinding;
import com.example.himanshjosh.databinding.ActivityProductDetailsBinding;
import com.example.himanshjosh.network.NetworkListener;
import com.example.himanshjosh.network.RetrofitService;
import com.example.himanshjosh.produts.adpter.ImageAdapter;
import com.example.himanshjosh.produts.model.get.ModelAddItamToCart;
import com.example.himanshjosh.produts.model.get.ProductDetails;
import com.example.himanshjosh.utlity.SharedPrefrence;
import com.example.himanshjosh.utlity.UtlityaFunction;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import cc.cloudist.acplibrary.ACProgressPie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity {

    private ActivityProductDetailsBinding binding;
    private NetworkListener networkListener;
    private int count = 1;
    private int pid;
    private int uid;
    private int price;
    private int totalPrice;
    private ACProgressFlower progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        networkListener = RetrofitService.getClient().create(NetworkListener.class);
        uid = Integer.parseInt(SharedPrefrence.getStr(this, "customerId"));
        binding.imageCartitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetailsActivity.this, CartActivity.class));

            }
        });

        binding.buttonAddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                Call<ModelAddItamToCart> call = networkListener.addItemToCart(String.valueOf(uid), String.valueOf(pid), String.valueOf(count), String.valueOf(totalPrice));
                call.enqueue(new Callback<ModelAddItamToCart>() {
                    @Override
                    public void onResponse(Call<ModelAddItamToCart> call, Response<ModelAddItamToCart> response) {
                        if (response.body().getStatus()) {
                            UtlityaFunction.showSnackBar(binding.getRoot(), "Item add successfully !");
                            progressDialog.dismiss();

                        } else {
                            UtlityaFunction.showSnackBar(binding.getRoot(), "Item not add!");
                            progressDialog.dismiss();


                        }
                    }

                    @Override
                    public void onFailure(Call<ModelAddItamToCart> call, Throwable t) {
                        UtlityaFunction.showSnackBar(binding.getRoot(), "" + t.getMessage());
                        progressDialog.dismiss();

                    }
                });

            }
        });

        binding.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                binding.display.setText(String.valueOf(count));
                priceCalcluation();
            }
        });

        binding.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 1) {
                    count--;
                    binding.display.setText(String.valueOf(count));
                    priceCalcluation();
                }


            }
        });


        String id = getIntent().getStringExtra("pid");
        if (!(id.isEmpty())) {
            showProgressDialog();
            Call<ProductDetails> call = networkListener.getProductDetails(id);
            call.enqueue(new Callback<ProductDetails>() {
                @Override
                public void onResponse(Call<ProductDetails> call, Response<ProductDetails> response) {
                    if (response.body().getStatus()) {
                        if (response.body().getProduct().getImage() != null || !(response.body().getProduct().getImage().isEmpty())
                                || response.body().getProduct().getSubcat() != null || !(response.body().getProduct().getSubcat().isEmpty())
                                || response.body().getProduct().getPname() != null || !(response.body().getProduct().getPname().isEmpty())
                                || response.body().getProduct().getRprice() != null || !(response.body().getProduct().getRprice().isEmpty())
                                || response.body().getProduct().getSprice() != null || !(response.body().getProduct().getSprice().isEmpty())
                                || response.body().getProduct().getDiscount()!=null || !(response.body().getProduct().getDiscount().isEmpty())
                                || response.body().getProduct().getDescription()!=null || !(response.body().getProduct().getDescription().isEmpty())
                                || response.body().getProduct().getSku()!=null ||!(response.body().getProduct().getSku().isEmpty())) {

                           /* Glide.with(getApplicationContext())
                                    .load(IMAGE_BASE_URL + response.body().getProduct().getImage())
                                    .placeholder(R.drawable.image_search)
                                    .error(R.drawable.image_not_found)
                                    .into(binding.imageView);*/

                            ArrayList<String> photolist=new ArrayList<>();
                            photolist.add(response.body().getProduct().getImage());
                            photolist.add(response.body().getProduct().getImage2());
                            photolist.add(response.body().getProduct().getImage3());
                            photolist.add(response.body().getProduct().getImage4());
                            imageSlider(photolist);
                            binding.textView7.setText(response.body().getProduct().getSubcat());
                            binding.textProductName.setText(response.body().getProduct().getPname());
                            binding.textView3.setText("₹" + response.body().getProduct().getRprice());
                            binding.textView4.setText("₹" + response.body().getProduct().getSprice());
                            binding.textView11.setText(" " +response.body().getProduct().getDiscount()+"*");
                            binding.textView6.setText(" " +response.body().getProduct().getLdescription());
                            binding.textView123.setText("HSBN NO : "+response.body().getProduct().getSku());
                            try {
                                long value = Integer.parseInt(response.body().getProduct().getRprice()) - Integer.parseInt(response.body().getProduct().getSprice());
                                long value2=Integer.parseInt(response.body().getProduct().getRprice());
                                double value1 =(double)( (double)value/(double) value2);
                                double pertange = (double) value1 * 100;
                                binding.textView5.setText(String.valueOf((int)pertange)+ "% off");

                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            pid = Integer.parseInt(response.body().getProduct().getPid().toString());
                            price = Integer.parseInt(response.body().getProduct().getSprice());
                            priceCalcluation();
                            progressDialog.dismiss();

                        }
                    } else {
                        UtlityaFunction.showSnackBar(binding.getRoot(), "Data not found");
                        progressDialog.dismiss();


                    }
                }

                @Override
                public void onFailure(Call<ProductDetails> call, Throwable t) {
                    progressDialog.dismiss();

                    UtlityaFunction.showSnackBar(binding.getRoot(), "" + t.getMessage());
                }
            });

        }


        binding.backButtonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        binding.buttonByenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddItem();
            }
        });




    }




    private void showProgressDialog() {
        progressDialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Loading...")
                .fadeColor(Color.DKGRAY).build();
        progressDialog.show();
    }

    private void priceCalcluation() {
        totalPrice = count * price;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void onAddItem()
    {
        showProgressDialog();
        Call<ModelAddItamToCart> call = networkListener.addItemToCart(String.valueOf(uid), String.valueOf(pid), String.valueOf(count), String.valueOf(totalPrice));
        call.enqueue(new Callback<ModelAddItamToCart>() {
            @Override
            public void onResponse(Call<ModelAddItamToCart> call, Response<ModelAddItamToCart> response) {
                if (response.body().getStatus()) {
                    progressDialog.dismiss();
                    startActivity(new Intent(ProductDetailsActivity.this, CartActivity.class));


                } else {
                    UtlityaFunction.showSnackBar(binding.getRoot(), "Something went to wrong !");
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<ModelAddItamToCart> call, Throwable t) {
                UtlityaFunction.showSnackBar(binding.getRoot(), "Something went to wrong !");
                progressDialog.dismiss();

            }
        });

    }

    public void imageSlider(ArrayList<String> photolist)
    {
        ViewPager mViewPager = (ViewPager) findViewById(R.id.imageView);
        ImageAdapter adapterView = new ImageAdapter(this,photolist);
        mViewPager.setAdapter(adapterView);
    }

}