package com.example.himanshjosh.cart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.himanshjosh.cart.adapter.CartAdapter;
import com.example.himanshjosh.cart.model.Product;
import com.example.himanshjosh.databinding.CartBinding;
import com.example.himanshjosh.network.NetworkListener;
import com.example.himanshjosh.network.RetrofitService;
import com.example.himanshjosh.order.order_placing;
import com.example.himanshjosh.ui.cartitem.model.CartDatum;
import com.example.himanshjosh.ui.cartitem.model.GetCartListModel;
import com.example.himanshjosh.ui.cartitem.model.get.ModelRemoveCartResponce;
import com.example.himanshjosh.utlity.SharedPrefrence;
import com.example.himanshjosh.utlity.UtlityaFunction;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartActivity extends AppCompatActivity implements CartAdapter.OnClickUpdateValue {
    private CartBinding binding;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CartAdapter cartAdapter;
    private NetworkListener networkListener;
    private ACProgressFlower progressDialog;
    private List<Product> favoriteList;
    private  String TAG="ABCD";
    private TextView mTotalValue,mTotalItem;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView = binding.recyclerview;
        networkListener = RetrofitService.getClient().create(NetworkListener.class);
        mTotalValue=binding.totalprice;
        mTotalItem=binding.totalProduct;

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });


        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CartActivity.this, order_placing.class);
                startActivity(intent);

            }
        });

        getCartList();

        binding.getRoot();
    }

    private void getCartList() {
        showProgressBar();
        Call<GetCartListModel> cartList = networkListener.getCartList(SharedPrefrence.getStr(CartActivity.this, "customerId"));
        cartList.enqueue(new Callback<GetCartListModel>() {
            @Override
            public void onResponse(Call<GetCartListModel> call, Response<GetCartListModel> response) {
                if (response.body().getStatus() && response.body().getCartData().size() > 0) {

                    progressDialog.dismiss();
                    setUpRecyclerView(response.body().getCartData());
                    binding.layourtConst.setVisibility(View.VISIBLE);
                    binding.layoutButton.setVisibility(View.VISIBLE);
                    binding.noDataFound.setVisibility(View.GONE);

                } else {
                    progressDialog.dismiss();
                    setUpRecyclerView(new ArrayList<CartDatum>());
                    binding.layourtConst.setVisibility(View.GONE);
                    binding.layoutButton.setVisibility(View.GONE);
                    binding.noDataFound.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(Call<GetCartListModel> call, Throwable t) {
                progressDialog.dismiss();
                binding.layourtConst.setVisibility(View.GONE);
                binding.layoutButton.setVisibility(View.GONE);
                binding.noDataFound.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showProgressBar() {
        progressDialog = new ACProgressFlower.Builder(CartActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Loading...")
                .fadeColor(Color.DKGRAY).build();
        progressDialog.show();
    }

    private void setUpRecyclerView(List<CartDatum> cartData) {
        if (cartData.size() > 0 || cartData != null) {
            onDetailsUpdate(cartData);
            linearLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            cartAdapter = new CartAdapter(CartActivity.this, cartData, this);
            binding.recyclerview.setAdapter(cartAdapter);
        }
    }

    private void onDetailsUpdate(List<CartDatum> cartData) {
        try {
            Log.d(TAG, "onDetailsUpdate:  "+cartData);
            int total_price = 0;
            mTotalItem.setText("  Total Price (" + cartData.size() + " items)");
            for (CartDatum var : cartData) {
                total_price += Integer.parseInt(var.getPrice()) * Integer.parseInt(var.getQty());
            }
            mTotalValue.setText(" ₹" + total_price);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        binding = null;
        progressDialog = null;
    }


    @Override
    public void onUpdate(String value, int position, List<CartDatum> updatecartList) {
        onDetailsUpdate(updatecartList);
    }

    @Override
    public void onRemoveCart(int postion, String pid) {
        showProgressBar();
        Call<ModelRemoveCartResponce> cartList = networkListener.onRemoveCartProduct(pid);
        cartList.enqueue(new Callback<ModelRemoveCartResponce>() {
            @Override
            public void onResponse(Call<ModelRemoveCartResponce> call, Response<ModelRemoveCartResponce> response) {
                if (response.body().getStatus()) {
                    UtlityaFunction.showSnackBar(binding.getRoot(), "Item Remove Successfully !");
                    progressDialog.dismiss();
                    getCartList();

                } else {
                    UtlityaFunction.showSnackBar(binding.getRoot(), "Item not Remove Successfully !");
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ModelRemoveCartResponce> call, Throwable t) {
                UtlityaFunction.showSnackBar(binding.getRoot(), "" + t.getMessage());
                progressDialog.dismiss();


            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}